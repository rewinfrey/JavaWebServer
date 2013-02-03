package tests;

import junit.framework.TestCase;

import server.HttpServer;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/24/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpServerTest extends TestCase {
    String testDirectory = "/Users/rickwinfrey/IdeaProjects/RickHttpServer/files/";

    private HttpServer serverFactory(int port) throws Exception {
        return new HttpServer(port, testDirectory);
    }

    public void testServerSocketIsBound() throws Exception {
        HttpServer testServer = serverFactory(3000);
        testServer.bindServerSocket();
        assertTrue(testServer.isBound());
        serverSocketIsClosed(testServer);
    }

    public void serverSocketIsClosed(HttpServer testServer) throws Exception {
        testServer.unBindServerSocket();
        assertTrue(testServer.isClosed());
    }

    public void testServerThreadIsAlive() throws Exception {
        HttpServer testServer2 = serverFactory(6000);
        testServer2.bindServerSocket();
        testServer2.serverThreadStart();
        assertTrue(testServer2.isAlive());
        serverIsInterrupted(testServer2);
    }

    public void serverIsInterrupted(HttpServer testServer2) throws Exception {
        testServer2.stop();
        assertTrue(testServer2.isClosed());
        assertTrue(testServer2.isInterrupted());
    }
}
/*
    public void testMultipleRequests() throws Exception {
        int port = 3000;
        HttpServer server = new HttpServer(port);
        assertFalse(server.is_running());
        server.run();
        assertTrue(server.is_running());
        ??? request1 = requestOn(port);
        ??? request2 = requestOn(port);

        server.connectionCount.should == 2; // maybe

        request1.write(get_root);
        request2.write(get_root);
        assertEqual(request1.read(), root_response);
        assertEqual(request2.read(), root_response);
        request1.close();
        request2.close();

        server.connectionCount.should == 0; // maybe
        server.stop();
        assertFalse(server.is_running());
    }
*/

