package server;

import server.HttpRequestParser;
import server.MimeTypeMatcher;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Wrangler {

    public HttpRequestParser httpRequestParser;
    public SocketWriter socketWriter;
    public String directory;
    public DateFormat dateFormat           = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy" );
    public MimeTypeMatcher mimeTypeMatcher = new MimeTypeMatcher();
    public HttpGenerator httpGenerator     = new HttpGenerator();

    public Wrangler(HttpRequestParser httpRequestParser, SocketWriter socketWriter, String directory) {
        this.httpRequestParser = httpRequestParser;
        this.socketWriter = socketWriter;
        this.directory = directory;
    }

    public void process() throws IOException, InterruptedException {
    }

    public void outToSocket(String outString, String outStatus) throws IOException {
        socketWriter.setResponseHeaders("text/html; charset=UTF-8", outString.getBytes().length+"", dateFormat.format(new Date()), outStatus, dateFormat.format(new Date()));
        socketWriter.writeResponseHeaders();
        socketWriter.writeOutputToClient(outString);
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, outStatus, dateFormat.format(new Date()));
    }
}
