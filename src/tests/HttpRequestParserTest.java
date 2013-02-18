package tests;

import org.junit.Before;
import org.junit.Test;
import server.HttpRequestParser;

import java.io.*;
import java.util.Collections;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestParserTest
{

  HttpRequestParser httpGetRequestParser;
  HttpRequestParser httpPostRequestParser;
  String[] getRequestArray;
  String[] getPostArray;

  String getRequest = "GET / HTTP/1.1";
  String postRequest = "POST /form HTTP/1.1\n" +
    "11:07:39 30/01/2013\n" +
    "Connection: keep-alive\n" +
    "Content-Length: 44\n" +
    "Cache-Control: max-age=0\n" +
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
    "Origin: http://localhost:3005\n" +
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17\n" +
    "Content-Type: application/x-www-form-urlencoded\n" +
    "Referer: http://localhost:3005/form\n" +
    "Accept-Encoding: gzip,deflate,sdch\n" +
    "Accept-Language: en-US,en;q=0.8\n" +
    "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\n" +
    "Cookie: _scrap_session=BAh\n\n" +
    "Post-Data: first_name=Rick&last_name=Winfrey\n";

  BufferedReader inputGetStream = new BufferedReader(new StringReader(getRequest));
  BufferedReader inputPostStream = new BufferedReader(new StringReader(postRequest));

  private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setup() throws Exception
  {
    httpGetRequestParser = new HttpRequestParser(inputGetStream);
    httpPostRequestParser = new HttpRequestParser(inputPostStream);
    getRequestArray = getRequest.split("[ ]+");
    getPostArray = postRequest.split("[ ]+");
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void httpRequestParser() throws Exception
  {
    assertEquals(inputGetStream, httpGetRequestParser.inStream);
    assertEquals("GET / HTTP/1.1", httpGetRequestParser.requestLine);
    for(int i = 0; i < httpGetRequestParser.requestLineArray.length; i++)
    {
      assertEquals(getRequestArray[i], httpGetRequestParser.requestLineArray[i]);
    }
  }

  @Test
  public void parseRequest()
  {
    String[] parsedArray = httpGetRequestParser.parseRequest();
    for(int i = 0; i < parsedArray.length; i++)
    {
      assertEquals(getRequestArray[i], parsedArray[i]);
    }
  }

  @Test
  public void httpRequestType()
  {
    String tempRequestType = httpGetRequestParser.httpRequestType();
    assertEquals(getRequestArray[0], tempRequestType);
  }

  @Test
  public void httpRequestResource()
  {
    String tempResource = httpGetRequestParser.httpRequestResource();
    assertEquals(getRequestArray[1], tempResource);
  }

  @Test
  public void setHttpHeaders() throws IOException
  {
    String expectedHeaders = "11:07:39 30/01/2013\r\n" +
      "Connection: keep-alive\r\n" +
      "Content-Length: 44\r\n" +
      "Cache-Control: max-age=0\r\n" +
      "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
      "Origin: http://localhost:3005\r\n" +
      "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17\r\n" +
      "Content-Type: application/x-www-form-urlencoded\r\n" +
      "Referer: http://localhost:3005/form\r\n" +
      "Accept-Encoding: gzip,deflate,sdch\r\n" +
      "Accept-Language: en-US,en;q=0.8\r\n" +
      "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\r\n" +
      "Cookie: _scrap_session=BAh\r\n";
    httpPostRequestParser.setHttpHeaders();
    assertEquals(expectedHeaders, httpPostRequestParser.headers.toString());
  }

  @Test
  public void httpRequestContentLength() throws IOException
  {
    httpPostRequestParser.setHttpHeaders();
    assertEquals(44, httpPostRequestParser.httpRequestContentLength());
  }

  @Test
  public void httpPostData() throws IOException
  {
    assertEquals("Post-Data: first_name=Rick&last_name=Winfrey", httpPostRequestParser.httpPostData());
  }

  @Test
  public void gettingTheURI() throws Exception
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET / HTTP/1.1".getBytes())));
    BufferedReader timeReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /time HTTP/1.1".getBytes())));
    assertEquals("/", new HttpRequestParser(reader).getURI());
    assertEquals("/time", new HttpRequestParser(timeReader).getURI());
  }

  @Test
  public void parsingSimpleGetIntoMap() throws Exception
  {
    InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\r\n".getBytes());
    Map<String, Object> request = HttpRequestParser.parse(input);

    assertEquals("GET", request.get("method"));
    assertEquals("/", request.get("uri"));
    assertEquals(Collections.EMPTY_MAP, request.get("params"));
    assertEquals(Collections.EMPTY_MAP, request.get("headers"));
  }

  @Test
  public void parsingTimeGetMap() throws Exception
  {
    InputStream input = new ByteArrayInputStream("GET /time?a=b&c=d HTTP/1.1\r\n".getBytes());
    Map<String, Object> request = HttpRequestParser.parse(input);

    assertEquals("GET", request.get("method"));
    assertEquals("/time", request.get("uri"));
    assertEquals("b", ((Map<String, Object>)request.get("params")).get("a"));
    assertEquals("d", ((Map<String, Object>)request.get("params")).get("c"));
    assertEquals(Collections.EMPTY_MAP, request.get("headers"));
  }

//  @Test
//  public void parsingRequestWithPostParams() throws Exception
//  {
//    InputStream input = new ByteArrayInputStream("POST /time?a=b&c=d HTTP/1.1\r\nContent-Length: 7\r\n\r\ne=f&g=h".getBytes());
//    Map<String, Object> request = HttpRequestParser.parse(input);
//
//    assertEquals("POST", request.get("method"));
//    assertEquals("/time", request.get("uri"));
//    assertEquals("b", ((Map<String, Object>)request.get("params")).get("a"));
//    assertEquals("d", ((Map<String, Object>)request.get("params")).get("c"));
//    assertEquals("f", ((Map<String, Object>)request.get("params")).get("e"));
//    assertEquals("h", ((Map<String, Object>)request.get("params")).get("g"));
//    assertEquals("7", ((Map<String, Object>)request.get("headers")).get("Content-Length"));
//  }

}



