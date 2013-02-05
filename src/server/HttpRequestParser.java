package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/28/13
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class HttpRequestParser {
    public BufferedReader inStream;
    public String requestLine;
    public String[] requestLineArray;
    public StringBuilder headers;

    public HttpRequestParser(BufferedReader inStream) throws Exception {
        this.inStream    = inStream;
        requestLine      = this.inStream.readLine();
        requestLineArray = parseRequest();
    }

    public String[] parseRequest() {
        return requestLine.split("[ ]+");
    }

    public String httpRequestType() {
        return requestLineArray[0];
    }

    public String httpRequestResource() {
        return requestLineArray[1];
    }

    public void setHttpHeaders() throws IOException {
        headers = new StringBuilder();
        String lines = inStream.readLine();

        while((lines != null) && (!lines.equals(""))) {
            headers.append(lines + "\r\n");
            lines = inStream.readLine();
        }
    }

    public int httpRequestContentLength() {
        String[] headerArray = headers.toString().split("\r\n");
        int contentLength = 0;

        for (String header: headerArray) {
            if ( header.startsWith("Content-Length"))
                contentLength = Integer.parseInt(header.split(": ")[1]);
        }

        return contentLength;
    }

    public String httpPostData() throws IOException {
        setHttpHeaders();
        int length = httpRequestContentLength();
        char[] characters = new char[length];

        StringBuilder postContent = new StringBuilder();

        inStream.read(characters, 0, length);
        postContent.append(new String(characters));

        return postContent.toString();
    }
}
