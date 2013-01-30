package com.rick_http_server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostWrangler extends Wrangler {
    private Socket request;
    private SocketWriter socketWriter;

    private HttpRequestParser httpRequestParser;

    private ResourceFetcher resourceFetcher;

    private Logger logger;

    public PostWrangler(HttpRequestParser httpRequestParser, Socket request) throws IOException {
        this.httpRequestParser = httpRequestParser;
        this.request           = request;
        this.httpRequestParser = httpRequestParser;
        this.request           = request;
        this.socketWriter      = new SocketWriter(request);
        this.resourceFetcher   = new ResourceFetcher();
        this.logger            = new Logger();
    }

    @Override
    public void process() throws IOException {
        socketWriter.writeOutputToClient(constructParamsList(httpRequestParser.httpPostData()));
    }

    public String constructParamsList(String postContent) {
        String[] parsedPostData = postContent.split("&");
        StringBuilder paramsString = new StringBuilder();
        for(String value: parsedPostData)
           paramsString.append(value.replace("=", " = ") + "\r\n");
        return paramsString.toString();
    }
}
