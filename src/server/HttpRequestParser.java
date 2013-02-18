package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/28/13
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class HttpRequestParser
{
  public BufferedReader inStream;
  public String requestLine;
  public String[] requestLineArray;
  public StringBuilder headers;

  public HttpRequestParser(BufferedReader inStream) throws Exception
  {
    this.inStream = inStream;
    requestLine = this.inStream.readLine();
    requestLineArray = parseRequest();
  }

  public String[] parseRequest()
  {
    return requestLine.split("[ ]+");
  }

  public String httpRequestType()
  {
    return requestLineArray[0];
  }

  public String httpRequestResource()
  {
    return requestLineArray[1];
  }

  public void setHttpHeaders() throws IOException
  {
    headers = new StringBuilder();
    String lines = inStream.readLine();

    while((lines != null) && (!lines.equals("")))
    {
      headers.append(lines + "\r\n");
      lines = inStream.readLine();
    }
  }

  public int httpRequestContentLength()
  {
    String[] headerArray = headers.toString().split("\r\n");
    int contentLength = 0;

    for(String header : headerArray)
    {
      if(header.startsWith("Content-Length"))
        contentLength = Integer.parseInt(header.split(": ")[1]);
    }

    return contentLength;
  }

  public String httpPostData() throws IOException
  {
    setHttpHeaders();
    int length = httpRequestContentLength();
    char[] characters = new char[length];

    StringBuilder postContent = new StringBuilder();

    inStream.read(characters, 0, length);
    postContent.append(new String(characters));

    return postContent.toString();
  }

  public String getURI()
  {
    return parseRequest()[1];
  }


  public static Map<String, Object> parse(InputStream input) throws Exception
  {
    Map<String, Object> map = new HashMap<String, Object>();
    HttpRequestParser parser = new HttpRequestParser(new BufferedReader(new InputStreamReader(input)));
    final String[] requestLineBits = parser.parseRequest();

    Map<String, String> paramsMap = parseParams(requestLineBits[1]);

    map.put("method", requestLineBits[0]);
    map.put("uri", requestLineBits[1].split("\\?")[0]);
    map.put("params", paramsMap);
    map.put("headers", Collections.EMPTY_MAP);
    return map;
  }

  private static Map<String, String> parseParams(String requestLineBit)
  {
    String[] uriAndQueryString = requestLineBit.split("\\?");
    Map<String, String> paramsMap = new HashMap<String, String>();
    if(uriAndQueryString.length > 1)
    {
      String[] queryStringParams = uriAndQueryString[1].split("&");
      for(int i = 0; i < queryStringParams.length; i++)
      {
        String paramPair = queryStringParams[i];
        String key = paramPair.split("=")[0];
        String value = paramPair.split("=")[1];
        paramsMap.put(key, value);
      }
    }
    return paramsMap;
  }
}
