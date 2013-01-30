package com.rick_http_server;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/28/13
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestParser {
    BufferedReader inStream;
    String requestLine;
    String[] requestLineArray;
    StringBuilder headers;

    public HttpRequestParser(BufferedReader inStream, String requestLine) {
        this.inStream         = inStream;
        this.requestLine      = requestLine;
        this.requestLineArray = parseRequest();
    }

    private String[] parseRequest() {
        return requestLine.split("[ ]+");
    }

    public String httpRequestType() {
        return requestLineArray[0];
    }

    public String httpRequestResource() {
        return requestLineArray[1];
    }

    public String setHttpHeaders() throws IOException {
        headers = new StringBuilder();
        String lines = inStream.readLine();

        while((lines != null) && (!lines.equals(""))) {
            headers.append(lines + "\r\n");
            lines = inStream.readLine();
            System.out.println(lines);
        }

        return headers.toString();
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
