package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import server.GetWrangler;
import server.HttpRequestRouter;
import server.HttpServer;

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
/*
    static HttpServer httpGetServer;
    static HttpServer httpPostServer;
    static int getPort = 3010;
    static int postPort = 3007;
    static Socket testGetSocket;
    static Socket testPostSocket;

    @BeforeClass
    public static void startServers() throws IOException {
        startGetServer();
//        startPostServer();
    }

    @AfterClass
    public static void tearDownServers() throws IOException, InterruptedException {
        httpGetServer.stop();
//        httpPostServer.stop();
    }

    public static void testGetSocket() throws IOException {
        testGetSocket = new Socket(InetAddress.getLocalHost(), getPort);
        PrintWriter out = new PrintWriter( testGetSocket.getOutputStream(), true);
        out.write("GET /hello HTTP/1.1\r\nHost: localhost:"+getPort+"\r\n");
        out.write("\r\n");
        out.flush();
    }

    public static Socket testMockSocket() throws IOException {
        Socket tempSocket = new Socket();
        PrintWriter out = new PrintWriter( tempSocket.getOutputStream(), true);
        out.write("GET /hello HTTP/1.1\r\nHost: localhost:"+getPort+"\r\n");
        out.write("\r\n");
        out.flush();
        return tempSocket;
    }

    public static void testPostSocket() throws IOException {
        testPostSocket = new Socket(InetAddress.getLocalHost(), postPort);
        PrintWriter out = new PrintWriter( testPostSocket.getOutputStream(), true);
        out.write("POST /form HTTP/1.1\r\nHost: localhost:"+postPort+"\r\n");
        out.write("Content-Type: application/x-www-form-urlencoded\r\n");
        out.write("Content-Length: 35\r\n");
        out.write("\r\n");
        out.write("one=BigBird&two=Oscar&three=Grover\r\n");
        out.write("\r\n");
        out.flush();
    }

    public static void startGetServer() throws IOException {
        httpGetServer = new HttpServer(getPort);
        httpGetServer.bindServerSocket();
        httpGetServer.serverThreadStart();
        testGetSocket();
    }

    public static void startPostServer() throws IOException {
        httpPostServer = new HttpServer(postPort);
        httpPostServer.bindServerSocket();
        httpPostServer.serverThreadStart();
        testPostSocket();
    }

    @Test
    public void httpGetRequestRouter() throws IOException, InterruptedException {
 //       testGetSocket();
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(new BufferedReader( new InputStreamReader(testGetSocket.getInputStream())), new DataOutputStream(testGetSocket.getOutputStream()), "testServedDir");
        assertNotNull(httpRequestRouter.request);
        assertNotNull(httpRequestRouter.directory);
        assertNotNull(httpRequestRouter.inStream);
        assertNotNull(httpRequestRouter.requestLine);
        assertNotNull(httpRequestRouter.httpRequestParser);
    }

    @Test
    public void httpGetWranglerCreated() throws IOException {
        MockGetSocket tempSocket = new MockGetSocket(InetAddress.getLocalHost(), 3008);
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(new BufferedReader( new InputStreamReader(tempSocket.getInputStream())), new DataOutputStream(tempSocket.getOutputStream()), "testServedDir");
        assertNull(httpRequestRouter.wrangler);
        assertEquals("GET", httpRequestRouter.httpRequestParser.httpRequestType());
        assertNotNull(httpRequestRouter.httpRequestParser);
        httpRequestRouter.routeRequest();
        //httpRequestRouter.wrangler = new GetWrangler(httpRequestRouter.httpRequestParser, tempSocket);
        assertNotNull(httpRequestRouter.wrangler);
    }
/*
    @Test
    public void httpPostRequestRouter() throws IOException {
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(testPostSocket, "testServedDir");
        assertNotNull(httpRequestRouter.request);
        assertNotNull(httpRequestRouter.directory);
        assertNotNull(httpRequestRouter.inStream);
        assertNotNull(httpRequestRouter.requestLine);
        assertNotNull(httpRequestRouter.httpRequestParser);
    }

    @Test
    public void httpPostWranglerCreated() throws IOException {
        testPostSocket();
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(testPostSocket, "testServedDir");
        httpRequestRouter.routeRequest();
        assertEquals("class server.PostWrangler", httpRequestRouter.wrangler.getClass().toString());
    }
    */
}
