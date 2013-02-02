package com.rick_http_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/29/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestRouter {
    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy" );

    private Socket request;
    private BufferedReader inStream;
    private String requestLine;

    private HttpRequestParser httpRequestParser;
    public  Wrangler wrangler;

    public HttpRequestRouter(Socket request) {
        try {
            this.request           = request;
            this.inStream          = new BufferedReader( new InputStreamReader( request.getInputStream() ) );
            this.requestLine       = inStream.readLine();

            this.httpRequestParser = new HttpRequestParser(inStream, requestLine);

        } catch ( Exception e ) {
        }
    }

    public void routeRequest() {
        try {
            if (httpRequestParser.httpRequestType().equals("GET")) {
                this.wrangler = new GetWrangler(httpRequestParser, request);
            } else {
                this.wrangler = new PostWrangler(httpRequestParser, request);
            }

            wrangler.process();
        } catch ( Exception e ) {
        }
    }
}
