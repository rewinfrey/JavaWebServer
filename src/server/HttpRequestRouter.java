package server;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/29/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestRouter {

    public BufferedReader inStream;
    public DataOutputStream outDataStream;
    public OutputStreamWriter outputStreamWriter;
    public String directory;

    public HttpRequestParser httpRequestParser;
    public Wrangler wrangler;

    public HttpRequestRouter(BufferedReader inStream, DataOutputStream outDataStream, OutputStreamWriter outputStreamWriter, String directory) throws Exception {
        this.inStream           = inStream;
        this.outputStreamWriter = outputStreamWriter;
        this.outDataStream      = outDataStream;
        this.directory          = directory;

        this.httpRequestParser  = new HttpRequestParser(inStream);
    }

    public void routeRequest() {
        try {
            SocketWriter socketWriter = generateSocketWriter();
            if (httpRequestParser.httpRequestType().equals("GET")) {
                wrangler = new GetWrangler(httpRequestParser, socketWriter, directory);
            } else {
                wrangler = new PostWrangler(httpRequestParser, socketWriter, directory);
            }

            wrangler.process();
        } catch ( Exception e ) {
        }
    }

    public SocketWriter generateSocketWriter() throws IOException {
        return new SocketWriter(outDataStream, outputStreamWriter);
    }
}
