package tests;

import org.junit.Before;
import org.junit.Test;
import server.SocketWriter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
    DataOutputStream outData            = new DataOutputStream(   new ByteArrayOutputStream());
    OutputStreamWriter outWriter        = new OutputStreamWriter( new ByteArrayOutputStream());

    SocketWriter socketWriter;

    @Before
    public void setup() throws IOException {
        socketWriter = new SocketWriter(outData, outWriter);
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
        socketWriter.setResponseHeaders("text/html", "300", "17:38:46 04/02/2013", "200 OK");
        assertEquals("text/html", socketWriter.contentType);
        assertEquals("300", socketWriter.contentLength);
        assertEquals("17:38:46 04/02/2013", socketWriter.lastModified);
        assertEquals("200 OK", socketWriter.httpStatus);
    }
}
