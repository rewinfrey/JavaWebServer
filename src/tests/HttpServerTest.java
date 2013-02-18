package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/24/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpServerTest
{
  String testDirectory = System.getProperty("user.dir").toString() + "/testfiles";
  ByteArrayOutputStream out = new ByteArrayOutputStream();

  private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void request()
  {
    System.setOut(new PrintStream(outContent));
  }

  private HttpServer serverFactory(int port) throws Exception
  {
    return new HttpServer(port, testDirectory);
  }

  @Test
  public void throwException() throws IOException, InterruptedException
  {
    String[] args = {"-p", "8919", "-d", testDirectory};
    HttpServer.main(args);
    assertTrue(HttpServer.start);
    assertTrue(HttpServer.server.isBound());
    HttpServer.server.serverThreadStart();
    HttpServer.server.stop();
    HttpServer.server.serverThreadStart();
  }

  @Test
  public void mainServerStartup() throws IOException, InterruptedException
  {
    String[] args = {"-p", "8756", "-d", testDirectory};
    HttpServer.main(args);
    assertNotNull(HttpServer.server);
    assertTrue(HttpServer.server.isBound());
    assertTrue(HttpServer.server.isAlive());
    assertEquals(8756, HttpServer.port);
    assertEquals(testDirectory, HttpServer.directory);
    HttpServer.server.stop();
    assertTrue(HttpServer.server.isClosed());
    assertTrue(HttpServer.server.isInterrupted());
  }

  @Test
  public void serverSocketIsBound() throws Exception
  {
    HttpServer testServer = serverFactory(3000);
    testServer.bindServerSocket();
    assertTrue(testServer.isBound());
    serverSocketIsClosed(testServer);
  }

  @Test
  public void displayHelp1() throws IOException, InterruptedException
  {
    String[] args = {"-h"};
    HttpServer.main(args);
    System.setOut(new PrintStream(out));
    HttpServer.displayHelp();
    assertEquals(false, HttpServer.start);
  }

  @Test
  public void displayHelp2() throws IOException, InterruptedException
  {
    String[] args = {"--help"};
    HttpServer.main(args);
    System.setOut(new PrintStream(out));
    HttpServer.displayHelp();
    assertEquals(false, HttpServer.start);
  }

  @Test
  public void serverThreadIsAlive() throws Exception
  {
    HttpServer testServer2 = serverFactory(6034);
    testServer2.bindServerSocket();
    testServer2.serverThreadStart();
    assertTrue(testServer2.isAlive());
    serverIsInterrupted(testServer2);
  }

  private void serverSocketIsClosed(HttpServer testServer) throws Exception
  {
    testServer.unBindServerSocket();
    assertTrue(testServer.isClosed());
  }

  private void serverIsInterrupted(HttpServer testServer2) throws Exception
  {
    testServer2.stop();
    assertTrue(testServer2.isClosed());
    assertTrue(testServer2.isInterrupted());
  }
}
