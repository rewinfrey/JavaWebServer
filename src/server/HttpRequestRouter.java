package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
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

    public Socket request;
    public BufferedReader inStream;
    public DataOutputStream outStream;
    public String requestLine;
    public String directory;

    public HttpRequestParser httpRequestParser;
    public Wrangler wrangler;

    public HttpRequestRouter(BufferedReader inStream, DataOutputStream outStream, String directory) {
        try {
            this.request           = request;
            this.inStream          = inStream;
            this.outStream         = outStream;
            this.requestLine       = inStream.readLine();
            this.directory         = directory;

            this.httpRequestParser = new HttpRequestParser(inStream, requestLine);

        } catch ( Exception e ) {
        }
    }

    public void routeRequest() {
        try {
            SocketWriter socketWriter = new SocketWriter(outStream);
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
