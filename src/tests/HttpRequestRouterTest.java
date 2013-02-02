package tests;

import junit.framework.TestCase;
import server.HttpRequestRouter;
import server.HttpServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestRouterTest extends TestCase {

   private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

   String getRequest  = "GET / HTTP/1.1\r\n";
   String postRequest = "POST / HTTP/1.1";

   private HttpServer configureServer(int port) throws IOException {
        HttpServer httpServer = new HttpServer(port);
        httpServer.bindServerSocket();
        httpServer.serverThreadStart();
        return httpServer;
   }

    private Socket configureSocket(int port, String requestContent) throws IOException {
        Socket requestSocket = new Socket(InetAddress.getLocalHost(), port);
        PrintWriter pw = new PrintWriter(requestSocket.getOutputStream());
        pw.write(requestContent);
        return requestSocket;
    }

    private BufferedReader getInputStream(Socket requestSocket) throws IOException {
        return new BufferedReader( new InputStreamReader( requestSocket.getInputStream() ) );

    }

    public void testHttpRequestRouter() throws Exception, IOException, InterruptedException {
        //System.setOut(new PrintStream(outContent));
        //HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, getRequest);
        //HttpRequestRouter httpRequestRouter = new HttpRequestRouter(requestSocket);
        //assertEquals("", outContent.toString());
        //System.setOut(null);
        //httpServer.stop();
    }
/*
    public void testGetRouteRequest() throws IOException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, getRequest);
        BufferedReader in     = getInputStream(requestSocket);
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(requestSocket);
        assertEquals("GetWranger", httpRequestRouter.wrangler.getClass().toString());
    }

    public void testPostRouteRequest() throws IOException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, postRequest);
        BufferedReader in     = getInputStream(requestSocket);
        HttpRequestRouter httpRequestRouter = new HttpRequestRouter(requestSocket);
        assertEquals("PostWranger", httpRequestRouter.wrangler.getClass().toString());
    }
/*
    public void routeRequest() throws IOException, InterruptedException {
        if (httpRequestParser.httpRequestType().equals("GET")) {
            this.wrangler = new GetWrangler(httpRequestParser, request);
        } else {
            this.wrangler = new PostWrangler(httpRequestParser, request);
        }

        wrangler.process();
    }*/
}
