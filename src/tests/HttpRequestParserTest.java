package tests;

import org.junit.Test;
import server.HttpRequestParser;

import java.io.*;
import java.util.Collections;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class HttpRequestParserTest
{
//  @Test
//  public void parsingSimpleGetIntoMap() throws Exception
//  {
//    InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
//    Map<String, Object> request = HttpRequestParser.parse(input);
//
//    assertEquals("GET", request.get("method"));
//    assertEquals("/", request.get("uri"));
//    assertEquals(Collections.EMPTY_MAP, request.get("params"));
//    assertEquals(Collections.EMPTY_MAP, request.get("headers"));
//  }
//
//  @Test
//  public void parsingTimeGetMap() throws Exception
//  {
//    InputStream input = new ByteArrayInputStream("GET /time?a=b&c=d HTTP/1.1\r\n\r\n".getBytes());
//    Map<String, Object> request = HttpRequestParser.parse(input);
//
//    assertEquals("GET", request.get("method"));
//    assertEquals("/time", request.get("uri"));
//    assertEquals("b", ((Map<String, Object>)request.get("params")).get("a"));
//    assertEquals("d", ((Map<String, Object>)request.get("params")).get("c"));
//    assertEquals(Collections.EMPTY_MAP, request.get("headers"));
//  }
//
//  @Test
//  public void parsingHeaders() throws Exception
//  {
//    InputStream input = new ByteArrayInputStream("GET /time?a=b&c=d HTTP/1.1\r\nContent-Length: 0\r\nDate: Tue, 19 Feb 2013 21:02:13 GMT\r\n\r\n".getBytes());
//    Map<String, Object> request = HttpRequestParser.parse(input);
//
//    assertEquals("GET", request.get("method"));
//    assertEquals("/time", request.get("uri"));
//    assertEquals("b", ((Map<String, Object>)request.get("params")).get("a"));
//    assertEquals("d", ((Map<String, Object>)request.get("params")).get("c"));
//    assertEquals("0", ((Map<String, Object>)request.get("headers")).get("Content-Length"));
//    assertEquals("Tue, 19 Feb 2013 21:02:13 GMT", ((Map<String, Object>)request.get("headers")).get("Date"));
//  }
//
//  @Test
//  public void parsingRequestWithPostContentAndQueryParams() throws Exception
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



