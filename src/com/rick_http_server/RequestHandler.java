package com.rick_http_server;

import java.net.Socket;

public class RequestHandler implements Runnable {

    private Socket request;
    private HttpRequestRouter httpRequestRouter;
    private boolean routerNotDefined = true;


    public RequestHandler ( Socket newRequest ) {
        this.request = newRequest;
    }

    public void processRequest() throws Exception {
        initializeHttpRequestRouter();
        httpRequestRouter.routeRequest();
        request.close();
    }

    private void initializeHttpRequestRouter() throws Exception {
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
