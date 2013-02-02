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
    static Socket mockSocket;
    RequestHandler requestHandler;

    public static void mockSocket() throws IOException {
        mockSocket = new Socket(InetAddress.getLocalHost(), port);
        PrintWriter out = new PrintWriter( mockSocket.getOutputStream(), true);
        out.write("GET /hello HTTP/1.1\r\nHost: localhost:"+port+"\r\n");
        out.write("\r\n");
        out.flush();
    }

    @BeforeClass
    public static void startServer() throws IOException {
        httpServer = new HttpServer(port);
        httpServer.bindServerSocket();
        httpServer.serverThreadStart();
        mockSocket();
    }

    @Before
    public void initializeRequestHandler() {
        requestHandler = new RequestHandler(mockSocket, "test");
    }

    @AfterClass
    public static void stopServer() throws IOException, InterruptedException {
        httpServer.stop();
    }

    @Test
    public void requestHandlerConstructor() throws IOException {
        assertEquals(mockSocket, requestHandler.request);
        assertEquals("test", requestHandler.directory);
    }

    @Test
    public void processRequest() throws Exception {
        assertTrue(requestHandler.request.isConnected());
        assertNull(requestHandler.httpRequestRouter);
        requestHandler.processRequest();
        assertNotNull(requestHandler.httpRequestRouter.wrangler);
        assertTrue(requestHandler.request.isClosed());
    }

    @Test
    public void initializeHttpRequestRouter() throws Exception {
        assertNull(requestHandler.httpRequestRouter);
        requestHandler.initializeHttpRequestRouter();
        assertNotNull(requestHandler.httpRequestRouter);
    }
/*
    @Test
    public void requestHandler() throws IOException {
        RequestHandler requestHandler = new RequestHandler(mockSocket(), "");
        String testHeaders = "GET / HTTP/1.1\r\nHost: localhost:"+port+"\r\n\r\n\r\n";
        Socket testRequest = requestHandler.request;
        PrintWriter out = new PrintWriter( new DataOutputStream(testRequest.getOutputStream()));

        out.write("GET / HTTP/1.1\r\nHost: localhost:"+port+"\r\n\r\n\r\n");

        StringBuilder testString = new StringBuilder();

        BufferedReader input = new BufferedReader( new InputStreamReader(testRequest.getInputStream()));
        System.out.println(input.readLine());
        ByteArrayInputStream mockRequest = new ByteArrayInputStream(testHeaders.getBytes());
        assertEquals("GET / HTTP/1.1", input.readLine());
        assertNotNull(requestHandler.request);
    }*/
}
