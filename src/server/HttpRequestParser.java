package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser
{
  private BufferedReader inReader;

  public static Map<String, Object> parse(InputStream input) throws Exception
  {
    Map<String, Object> map = new HashMap<String, Object>();

    HttpRequestParser parser = new HttpRequestParser(input);

    final String[] requestLineBits = parser.requestLine().split("[ ]");

    final Map<String, String> headersMap = parser.parseHeaders();
    final Map<String, String> paramsMap = parser.parseParams(requestLineBits[1], headersMap.get("Content-Length"));

    map.put("method", requestLineBits[0]);
    map.put("uri", requestLineBits[1].split("\\?")[0]);
    map.put("params", paramsMap);
    map.put("headers", headersMap);
    return map;
  }

  public HttpRequestParser(InputStream input)
  {
    this.inReader = new BufferedReader(new InputStreamReader(input));
  }

  public String requestLine() throws IOException
  {
    return inReader.readLine();
  }

  private Map<String, String> parseParams(String requestURI, String contentLength) throws Exception
  {
      Map<String, String> paramMap = new HashMap<String, String>();

      paramMap.putAll(parseQueryStringParams(requestURI));
      paramMap.putAll(parsePostContent(contentLength));

      return paramMap;
  }

  private Map<String, String> parsePostContent(String contentLength) throws Exception
  {
    Map<String, String> postContent = new HashMap<String, String>();

    if (contentLength != null && !contentLength.equals("0"))
    {
        String postContentString = buildPostDataString(contentLength);
        postContent.putAll(mapifyQueryAndPostParams(postContentString));
    }

    return postContent;
  }

  private String buildPostDataString(String contentLength) throws IOException
  {
    int length = Integer.parseInt(contentLength);
    char[] characters = new char[length];
    StringBuilder postData = new StringBuilder();

    inReader.read(characters, 0, length);
    postData.append(new String(characters));

    return postData.toString();
  }

  private Map<String, String> parseQueryStringParams(String requestURI)
  {
    String[] uriAndQueryString = requestURI.split("\\?");
    Map<String, String> paramsMap = new HashMap<String, String>();

    if(uriAndQueryString.length > 1)
        paramsMap.putAll(mapifyQueryAndPostParams(uriAndQueryString[1]));

    return paramsMap;
  }

  private Map<String, String> mapifyQueryAndPostParams(String parsable)
  {
    Map<String, String> paramsMap = new HashMap<String, String>();
    String[] parsedString = parsable.split("&");

    for(int i = 0; i < parsedString.length; i++)
    {
        String[] paramPair = parsedString[i].split("=");
        paramsMap.put(paramPair[0], paramPair[1]);
    }

    return paramsMap;
  }

  public Map<String, String> parseHeaders() throws IOException
  {
    Map<String, String> headerMap = new HashMap<String, String>();
    String line;

    line = inReader.readLine();
    while(line != null && !line.equals(""))
    {
        String[] tempLine = line.split(": ");

        if(tempLine.length > 1)
            headerMap.put(tempLine[0], tempLine[1]);

        line = inReader.readLine();
    }

    return headerMap;
  }
}
