package server;

import server.HttpRequestParser;
import server.MimeTypeMatcher;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Wrangler {

    Socket request;
    HttpRequestParser httpRequestParser;
    DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy" );
    MimeTypeMatcher mimeTypeMatcher = new MimeTypeMatcher();

    public Wrangler() {
    }

    public void process() throws IOException, InterruptedException {
    }
}
