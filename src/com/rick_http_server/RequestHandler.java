package com.rick_http_server;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private Socket request;
    private HttpRequestRouter httpRequestRouter;

    public RequestHandler ( Socket newRequest ) {
        this.request = newRequest;
    }

    public void processRequest() throws Exception {
        initializeHttpRequestRouter();
        httpRequestRouter.routeRequest();
    }

    private void initializeHttpRequestRouter() throws IOException {
        this.httpRequestRouter = new HttpRequestRouter(request);
    }


    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
