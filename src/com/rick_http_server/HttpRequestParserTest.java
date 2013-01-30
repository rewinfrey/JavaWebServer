package com.rick_http_server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestParserTest extends TestCase {

    HttpRequestParser httpRequestParser;
    String getRequest  = "GET / HTTP/1.1";

    String postRequest = "POST /form HTTP/1.1\n" +
            "11:07:39 30/01/2013\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 27\n" +
            "Cache-Control: max-age=0\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
            "Origin: http://localhost:3005\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Referer: http://localhost:3005/form\n" +
            "Accept-Encoding: gzip,deflate,sdch\n" +
            "Accept-Language: en-US,en;q=0.8\n" +
            "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\n" +
            "Cookie: _scrap_session=BAh\n" +
            "\n";
            //"\n" +
            //"Post-Data: first_name=Rick&last_name=Winfrey\n";

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

    public void testHttpRequestParser() throws IOException, InterruptedException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, getRequest);
        BufferedReader in     = getInputStream(requestSocket);
        httpRequestParser     = new HttpRequestParser(in, getRequest);
        assertEquals(getRequest, httpRequestParser.requestLine);
        httpServer.stop();
    }

    public void testRequestTypes() throws IOException, InterruptedException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, getRequest);
        BufferedReader in     = getInputStream(requestSocket);
        httpRequestParser     = new HttpRequestParser(in, getRequest);
        assertEquals("GET", httpRequestParser.httpRequestType());
        httpServer.stop();
    }

    public void testRequestResource() throws IOException, InterruptedException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, getRequest);
        BufferedReader in     = getInputStream(requestSocket);
        httpRequestParser     = new HttpRequestParser(in, getRequest);
        assertEquals("/", httpRequestParser.httpRequestResource());
        httpServer.stop();
    }
/*
    public void testSetHttpHeaders() throws IOException, InterruptedException {
        HttpServer httpServer = configureServer(5007);
        Socket requestSocket  = configureSocket(5007, postRequest);
        BufferedReader in     = getInputStream(requestSocket);
        httpRequestParser     = new HttpRequestParser(in, postRequest);
        assertEquals("/", httpRequestParser.setHttpHeaders());
        httpServer.stop();
    }
*/
}
