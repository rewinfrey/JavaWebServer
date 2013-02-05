package tests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.GetWrangler;
import server.HttpRequestRouter;
import server.HttpServer;
import server.SocketWriter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestRouterTest {
    String directory                    = "/Users/rickwinfrey/play/files";
    BufferedReader getBr                = new BufferedReader(     new StringReader("GET /time HTTP/1.1\r\n"));
    BufferedReader postBr               = new BufferedReader(     new StringReader("POST /form HTTP/1.1\r\n\r\nname=John&age=23"));
    DataOutputStream outData            = new DataOutputStream(   new ByteArrayOutputStream());
    OutputStreamWriter outWriter        = new OutputStreamWriter( new ByteArrayOutputStream());
    HttpRequestRouter getRequestRouter;
    HttpRequestRouter postRequestRouter;

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() throws Exception {
        getRequestRouter  = new HttpRequestRouter(getBr, outData, outWriter, directory);
        postRequestRouter = new HttpRequestRouter(postBr, outData, outWriter, directory);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void httpRequestRouter() {
        assertEquals(getBr, getRequestRouter.inStream);
        assertEquals(outData, getRequestRouter.outDataStream);
        assertEquals(outWriter, getRequestRouter.outputStreamWriter);
        assertNotNull(getRequestRouter.httpRequestParser);
        assertEquals(directory, getRequestRouter.directory);
    }

    @Test
    public void routeGetRequest() {
        getRequestRouter.routeRequest();
        assertEquals("class server.GetWrangler", getRequestRouter.wrangler.getClass().toString());
    }

    @Test
    public void routePostRequest() {
        postRequestRouter.routeRequest();
        assertEquals("class server.PostWrangler", postRequestRouter.wrangler.getClass().toString());
    }

    @Test
    public void generateSocketWriter() throws IOException {
        SocketWriter socketWriter = getRequestRouter.generateSocketWriter();
        assertNotNull(socketWriter);
        assertEquals(outData, socketWriter.outDataStream);
        assertEquals(outWriter, socketWriter.outStreamWriter);
    }
}
