package tests;

import org.junit.Before;
import org.junit.Test;
import server.GetWrangler;
import server.HttpRequestParser;
import server.SocketWriter;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/1/13
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetWranglerTest {

    ByteArrayOutputStream dataStream    = new ByteArrayOutputStream();
    DataOutputStream outData            = new DataOutputStream( dataStream );
    OutputStreamWriter outWriter        = new OutputStreamWriter( dataStream );

    String testDir                      = System.getProperty("user.dir").toString() + "/testfiles";
    SocketWriter socketWriter;
    HttpRequestParser httpRequestParser;

    String getRequest  = "GET / HTTP/1.1";
    String getOtherRequest = "Get /test.txt HTTP/1.1";
    String getTimeRequest = "GET /time HTTP/1.1";
    String getFormRequest = "GET /form HTTP/1.1";
    String getHelloRequest = "GET /hello HTTP/1.1";
    String getBogusRequest = "GET /soudaoiusdf HTTP/1.1";
    String getDirectoryRequest = "GET /test HTTP/1.1";
    String getPostUriParamsRequest = "GET /form?name=rick&age=30 HTTP/1.1";

    BufferedReader inputGetStream = new BufferedReader( new StringReader(getRequest));

    private BufferedReader brFactory(String request) {
        return new BufferedReader( new StringReader(request));
    }

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void request() {
        //System.setOut(new PrintStream(outContent));
    }

    GetWrangler getWrangler;
    @Before
    public void setup() throws Exception {
        socketWriter = new SocketWriter(outData, outWriter);
        httpRequestParser = new HttpRequestParser(inputGetStream);
        getWrangler  = new GetWrangler(httpRequestParser, socketWriter, testDir );
    }

    @Test
    public void getWrangler() throws IOException {
        GetWrangler testWrangler = new GetWrangler(httpRequestParser, socketWriter, testDir);
        assertNotNull(testWrangler.preDefinedRoutes);
        assertNotNull(testWrangler.resourceFetcher);
        assertNotNull(testWrangler.httpRequestParser);
        assertNotNull(testWrangler.socketWriter);
        assertNotNull(testWrangler.directory);
        assertNotNull(testWrangler.mimeTypeMatcher);
        assertNotNull(testWrangler.httpGenerator);
    }

    @Test
    public void preDefinedProcess() throws Exception {
        assertEquals("/", getWrangler.httpRequestParser.httpRequestResource());
        assertTrue(getWrangler.preDefinedRoute());

        HttpRequestParser testParser = new HttpRequestParser(brFactory(getOtherRequest));
        GetWrangler testWrangler = new GetWrangler(testParser, socketWriter, testDir);
        assertEquals("/test.txt", testWrangler.httpRequestParser.httpRequestResource());
        assertFalse(testWrangler.preDefinedRoute());
    }

    @Test
    public void getRootProcess() throws IOException, InterruptedException {
        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ getWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ getWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 617\r\n\r\n"
                );
        resultString.append(getWrangler.httpGenerator.generateIndex(testDir, testDir));
        getWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getDirProcess() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getDirectoryRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 793\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generateIndex(testDir+"/test", testDir));
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getFormProcess() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getFormRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 729\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generateForm());
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getHelloProcess() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getHelloRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 8314\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generateHello());
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getTimeProcess() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getTimeRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        Calendar futureDate = Calendar.getInstance();
        futureDate.setTime(new Date());
        futureDate.add(Calendar.SECOND, 1);
        String date = testWrangler.dateFormat.format(futureDate.getTime());
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ date + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ date + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 281\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generateTime(date));
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getOtherProcess() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getOtherRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        testWrangler.httpRequestParser.setHttpHeaders();
        String lastModified = new File( testDir + "/test.txt" ).lastModified()+"";
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ lastModified + "\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: 19\r\n\r\n" +
                "This is a test file"
                );
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getBogusFile() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getBogusRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);
        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 404 Not Found\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 710\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generate404());
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void getPostUriParams() throws Exception {
        HttpRequestParser httpRequestParser1 = new HttpRequestParser(brFactory(getPostUriParamsRequest));
        GetWrangler testWrangler = new GetWrangler(httpRequestParser1, socketWriter, testDir);

        String temp = httpRequestParser1.httpRequestResource();
        String params = temp.replace("/form?", "");

        StringBuilder resultString = new StringBuilder();
        resultString.append(
                "HTTP/1.1 200 OK\r\n" +
                "Connection: keep-alive\r\n" +
                "Date: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Server: BoomTown\r\n" +
                "Last-Modified: "+ testWrangler.dateFormat.format(new Date()) + "\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: 293\r\n\r\n"
                );
        resultString.append(testWrangler.httpGenerator.generateFormParams(params));
        testWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

    @Test
    public void writeLogToTerminal() {
        ByteArrayOutputStream bin = new ByteArrayOutputStream();
        System.setOut( new PrintStream(bin));
        getWrangler.writeLogToTerminal("200 OK");
        assertEquals("\nGET / HTTP/1.1 200 OK\n" + getWrangler.dateFormat.format(new Date()) + "\n", bin.toString());
    }
}
