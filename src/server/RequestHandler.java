package server;

import server.HttpRequestRouter;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    public Socket request;
    public String directory;
    public HttpRequestRouter httpRequestRouter;
    public int exception = 0;


    public RequestHandler ( Socket newRequest, String directory ) {
        this.request   = newRequest;
        this.directory = directory;
    }

    public void processRequest() throws Exception {
        initializeHttpRequestRouter();
        httpRequestRouter.routeRequest();
        request.close();
    }

    public void initializeHttpRequestRouter() throws Exception {
        DataOutputStream outDataStream        = new DataOutputStream( request.getOutputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter( request.getOutputStream(), "UTF-8");
        BufferedReader bufferedReader         = new BufferedReader( new InputStreamReader( request.getInputStream()));

        this.httpRequestRouter = new HttpRequestRouter(bufferedReader, outDataStream, outputStreamWriter, directory);
    }


    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println("Oops, there was an error. It's probably a bad socket.");
            exception = 1;
        }
    }
}
