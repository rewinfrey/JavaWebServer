package server;

import server.HttpRequestRouter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {

    public Socket request;
    public String directory;
    public HttpRequestRouter httpRequestRouter;


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
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( request.getInputStream()));

        this.httpRequestRouter = new HttpRequestRouter(bufferedReader, outDataStream, outputStreamWriter, directory);
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
