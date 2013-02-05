package tests;

import org.junit.Before;
import org.junit.Test;
import server.SocketWriter;

import java.io.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/1/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketWriterTest {
    ByteArrayOutputStream dataStream    = new ByteArrayOutputStream();
    DataOutputStream outData            = new DataOutputStream( dataStream );
    OutputStreamWriter outWriter        = new OutputStreamWriter( dataStream );
    String exception;

    SocketWriter socketWriter;
    String headerString = "HTTP/1.1 200 OK\r\n" +
           "Connection: keep-alive\r\n" +
           "Date: 17:38:46 04/02/2013\r\n" +
           "Server: BoomTown\r\n" +
           "Last-Modified: 17:38:46 04/02/2013\r\n" +
           "Content-Type: text/html\r\n" +
           "Content-Length: 300\r\n\r\n";

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() throws IOException {
        socketWriter = new SocketWriter(outData, outWriter);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void httpHeaderStringConstants() {
        assertEquals("HTTP/1.1 ", socketWriter.HTTPVERSION);
        assertEquals("Last-Modified: ", socketWriter.LASTMODIFIED);
        assertEquals("Content-Type: ", socketWriter.CONTENTTYPE);
        assertEquals("Content-Length: ", socketWriter.CONTENTLENGTH);
        assertEquals("Connection: keep-alive", socketWriter.CONNECTION);
        assertEquals("Server: BoomTown", socketWriter.SERVER);
        assertEquals("Date: ", socketWriter.DATE);
        assertEquals("\r\n", socketWriter.CRLF);
    }

    @Test
    public void constructor() throws IOException {
        SocketWriter newSocketWriter = new SocketWriter(outData, outWriter);
        assertEquals(outData, newSocketWriter.outDataStream);
        assertEquals(outWriter, newSocketWriter.outStreamWriter);
        assertNotNull(newSocketWriter.logger);
        assertNotNull(newSocketWriter.dateFormat);
    }

    @Test
    public void setResponseHeaders() {
        socketWriter.setResponseHeaders("text/html", "300", "17:38:46 04/02/2013", "200 OK", "17:38:46 04/02/2013");
        assertEquals("text/html", socketWriter.contentType);
        assertEquals("300", socketWriter.contentLength);
        assertEquals("17:38:46 04/02/2013", socketWriter.lastModified);
        assertEquals("200 OK", socketWriter.httpStatus);
        assertEquals("17:38:46 04/02/2013", socketWriter.date);
    }

    @Test
    public void writeResponseHeaders() throws IOException {
        socketWriter.setResponseHeaders("text/html", "300", "17:38:46 04/02/2013", "200 OK", "17:38:46 04/02/2013");
        socketWriter.writeResponseHeaders();
        assertEquals(headerString, dataStream.toString());
    }

    @Test
    public void writeOutputToClient() throws IOException {
        String testString = "Testing StreamWriter Output To Client";
        socketWriter.writeOutputToClient(testString);
        assertEquals(testString, dataStream.toString());
    }

    @Test
    public void writeHeadersAndOutputToClient() throws IOException {
        socketWriter.setResponseHeaders("text/html", "300", "17:38:46 04/02/2013", "200 OK", "17:38:46 04/02/2013");
        socketWriter.writeResponseHeaders();
        socketWriter.writeOutputToClient("This is to simulate output to a client over a socket");
        assertEquals(headerString + "This is to simulate output to a client over a socket", dataStream.toString());
    }

    @Test
    public void writeFileToClient() throws IOException {
        FileWriter outFileWriter = new FileWriter( new File( System.getProperty("user.dir") + "/testfiles/test.txt" ) );
        outFileWriter.write("This is a test file");
        outFileWriter.flush();
        outFileWriter.close();
        socketWriter.writeFileToClient(System.getProperty("user.dir") + "/testfiles/test.txt");
        assertEquals("This is a test file", dataStream.toString());
    }

    @Test
    public void closeRequest() throws IOException {
        outData.writeBytes("This shouldn't raise an error");
        outWriter.write("This shouldn't raise an error");
        socketWriter.closeRequest();
        try {
            outData.writeBytes("This should raise an error");
            outWriter.write("This should raise an error");
        } catch ( IOException e ) {
            exception = e.toString();
        }
        assertEquals("java.io.IOException: Stream closed", exception);
    }


    @Test
    public void writeLogToTerminal() {
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdout));
        socketWriter.writeLogToTerminal("GET / HTTP/1.1", "200 OK", "17:38:46 04/02/2013");
        assertEquals("\nGET / HTTP/1.1 200 OK\n17:38:46 04/02/2013\n", stdout.toString());
    }
}
