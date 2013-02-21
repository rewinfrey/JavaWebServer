package tests;

import org.junit.Test;
import server.HttpServer;
import server.Responder;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class HttpServerTest
{
  HttpServer httpServer;

  public void httpServerFactory(int port) throws IOException
  {
      httpServer = new HttpServer(port);
      new MockRegister(httpServer);
      httpServer.start();
  }

  public void stop() throws IOException
  {
      httpServer.stop();
  }

  private Process execInRunTime(String cmd) throws IOException {
      Runtime r = Runtime.getRuntime();
      return r.exec(cmd);
  }

  private Process curlTest(int port, String route) throws IOException {
      String cmd = "curl http://localhost:"+port+route;
      return execInRunTime(cmd);
  }

  private Process curlPostTest(int port, String postData, String route) throws IOException {
      String cmd = "curl --data \""+postData+"\" http://localhost:"+port+route;
      return execInRunTime(cmd);
  }

  private BufferedReader processToBufferedReader(Process p)
  {
      return new BufferedReader( new InputStreamReader( p.getInputStream() ) );
  }

  @Test
  public void start() throws IOException, InterruptedException
  {
      int port = 8989;
      httpServerFactory(port);
      assertTrue(httpServer.isBound());
      stop();
  }

  @Test
  public void oneAckResponse() throws IOException {
      int port = 9990;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlTest(port, "/"));
      assertEquals("ACK", response.readLine());
      stop();
  }

  @Test
  public void twoAckResponses() throws IOException {
      int port = 9991;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlTest(port, "/"));
      BufferedReader response2 = processToBufferedReader(curlTest(port, "/"));
      assertEquals("ACK", response.readLine());
      assertEquals("ACK", response2.readLine());
      stop();
  }

  @Test
  public void multipleAckResponses() throws IOException, InterruptedException {
      int port = 9992;
      httpServerFactory(port);
      ArrayList<BufferedReader> responseArray = new ArrayList<BufferedReader>();

      for ( int i = 0; i < 10; i ++ ) {
          responseArray.add(processToBufferedReader(curlTest(port, "/")));
      }

      for ( int j = 0; j < responseArray.size(); j++ ) {
          assertEquals("ACK", responseArray.get(j).readLine());
      }

      stop();
  }

  @Test
  public void onePostAck() throws IOException {
      int port = 9993;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form"));
      assertEquals("\"param1 = value1 param2 = value2\"", response.readLine());
      stop();
  }


  @Test
  public void twoPostAcks() throws IOException {
      int port = 9994;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form"));
      BufferedReader response2 = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form"));
      assertEquals("\"param1 = value1 param2 = value2\"", response.readLine());
      assertEquals("\"param1 = value1 param2 = value2\"", response2.readLine());
      stop();
  }

  @Test
  public void multiplePostAcks() throws IOException, InterruptedException {
      int port = 9999;
      httpServerFactory(port);
      ArrayList<BufferedReader> responseArray = new ArrayList<BufferedReader>();

      for ( int i = 0; i < 10; i ++ ) {
          responseArray.add(processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form")));
          Thread.sleep(100);
      }

      for ( int j = 0; j < responseArray.size(); j++ ) {
          assertEquals("\"param1 = value1 param2 = value2\"", responseArray.get(j).readLine());
      }

      stop();
  }

  @Test
  public void onePostAckWithQueryString() throws IOException {
      int port = 9996;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form?name=rick&age=30"));
      assertEquals("\"param1 = value1 param2 = value2\" age = 30 name = rick", response.readLine());
      stop();
  }

  @Test
  public void twoPostAcksWithQueryStrings() throws IOException {
      int port = 9997;
      httpServerFactory(port);
      BufferedReader response = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form?name=rick&age=30"));
      BufferedReader response2 = processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form?name=colin&age=32"));
      assertEquals("\"param1 = value1 param2 = value2\" age = 30 name = rick", response.readLine());
      assertEquals("\"param1 = value1 param2 = value2\" age = 32 name = colin", response2.readLine());
      stop();
  }

  @Test
  public void MultiplePostAcksWithQueryStrings() throws IOException, InterruptedException {
      int port = 9998;
      httpServerFactory(port);
      ArrayList<BufferedReader> responseArray = new ArrayList<BufferedReader>();

      for ( int i = 0; i < 10; i ++ ) {
          responseArray.add(processToBufferedReader(curlPostTest(port, "param1=value1&param2=value2", "/form?name=rick&age=30")));
          Thread.sleep(100);
      }

      for ( int j = 0; j < responseArray.size(); j++ ) {
          assertEquals("\"param1 = value1 param2 = value2\" age = 30 name = rick", responseArray.get(j).readLine());
      }

      stop();
  }

  public MockRegister mockRegister;

  private static class MockRegister
  {
      private HttpServer httpServer;

      public MockRegister(HttpServer httpServer)
      {
        this.httpServer = httpServer;
        registerRoutes();
      }

      public void registerRoutes() {
          httpServer.registerRoute("/", new Responder() {
              @Override
              public Map<String, Object> respond(Map<String, Object> request)
              {
                  Map<String, Object> response = new HashMap<String, Object>();
                  String bodyString = "ACK";
                  response.put("body", bodyString.getBytes(Charset.forName("UTF-8")));
                  putHeadersInResponseMap(response, request, "200 OK", "text/plain", bodyString.length());
                  return response;
              }
          });

          httpServer.registerRoute("/form", new Responder() {
              @Override
              public Map<String, Object> respond(Map<String, Object> request)
              {
                  Map<String, Object> response = new HashMap<String, Object>();
                  StringBuilder bodyString = new StringBuilder();
                  Map<String, String> postParams = (HashMap<String, String>) request.get("params");
                  Iterator it = postParams.entrySet().iterator();
                  while ( it.hasNext() ) {
                      Map.Entry pairs = (Map.Entry)it.next();
                      System.out.println(pairs.getKey() + " " + pairs.getValue());
                      bodyString.append(pairs.getKey() + " = " + pairs.getValue() + " ");
                  }
                  response.put("body", bodyString.toString().trim().getBytes(Charset.forName("UTF-8")));
                  putHeadersInResponseMap(response, request, "200 OK", "text/plain", bodyString.length());
                  return response;
              }
          });
      }

      public static void putHeadersInResponseMap(Map<String, Object> response, Map<String, Object> request, String status, String contentType, int contentLength)
      {
          DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy");

          String CRLF = "\r\n";
          String RESPONSE = "HTTP/1.1 " + status;
          String CONNECTION = "Connection: Close";
          String DATE = "Date: testing";
          String SERVER = "Server: BoomTown";
          String CONTENTTYPE = "Content-Type: " + contentType;
          String CONTENTLENGTH = "Content-Length: " + contentLength;

          response.put("response", (RESPONSE + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("connection", (CONNECTION + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("date", (DATE + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("server", (SERVER + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("content-type", (CONTENTTYPE + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("content-length", (CONTENTLENGTH + CRLF + CRLF).getBytes(Charset.forName("UTF-8")));
          response.put("log", request.get("method") + " " + request.get("uri") + " " + status + "\r\n" + dateFormat.format(new Date()));
          response.put("end", (CRLF).getBytes(Charset.forName("UTF-8")));
      }
  }
}
