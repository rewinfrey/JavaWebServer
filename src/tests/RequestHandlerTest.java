package tests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.HttpServer;
import server.RequestHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/1/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandlerTest {
    static HttpServer httpServer;
    static int port = 3006;
    static Socket testSocket;

    static String testDirectory = "/Users/rickwinfrey/play/files/";
    RequestHandler requestHandler;

    public static void testSocket() throws IOException {
        testSocket = new Socket(InetAddress.getLocalHost(), port);
        PrintWriter out = new PrintWriter( testSocket.getOutputStream(), true);
        out.write("GET /hello HTTP/1.1\r\nHost: localhost:"+port+"\r\n");
        out.write("\r\n");
        out.flush();
    }

    @BeforeClass
    public static void startServer() throws IOException {
        httpServer = new HttpServer(port, testDirectory);
        httpServer.bindServerSocket();
        httpServer.serverThreadStart();
        testSocket();
    }

    @Before
    public void initializeRequestHandler() {
        requestHandler = new RequestHandler(testSocket, testDirectory);
    }

    @AfterClass
    public static void stopServer() throws IOException, InterruptedException {
        httpServer.stop();
    }

    @Test
    public void requestHandlerConstructor() throws IOException {
        assertEquals(testSocket, requestHandler.request);
        assertEquals(testDirectory, requestHandler.directory);
    }

    @Test
    public void processRequest() throws Exception {
        assertTrue(requestHandler.request.isConnected());
        assertNull(requestHandler.httpRequestRouter);
        requestHandler.processRequest();
        assertNotNull(requestHandler.httpRequestRouter);
        assertTrue(requestHandler.request.isClosed());
    }
}
