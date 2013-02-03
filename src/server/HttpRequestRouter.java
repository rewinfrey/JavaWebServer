package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss:SS dd/MM/yyyy" );

    public BufferedReader inStream;
    public DataOutputStream outDataStream;
    public OutputStreamWriter outputStreamWriter;
    public String requestLine;
    public String directory;

    public HttpRequestParser httpRequestParser;
    public Wrangler wrangler;

    public HttpRequestRouter(BufferedReader inStream, DataOutputStream outDataStream, OutputStreamWriter outputStreamWriter, String directory) {
        try {
            this.inStream          = inStream;
            this.outputStreamWriter = outputStreamWriter;
            this.outDataStream     = outDataStream;
            this.directory         = directory;

            this.httpRequestParser = new HttpRequestParser(inStream);
        } catch ( Exception e ) {
        }
    }

    public void routeRequest() {
        try {
            SocketWriter socketWriter = new SocketWriter(outDataStream, outputStreamWriter);
            if (httpRequestParser.httpRequestType().equals("GET")) {
                wrangler = new GetWrangler(httpRequestParser, socketWriter, directory);
            } else {
                wrangler = new PostWrangler(httpRequestParser, socketWriter);
            }

            wrangler.process();
        } catch ( Exception e ) {
        }
    }
}
