package com.rick_http_server;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/28/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponse {
    private String HTTPVERSION   = "HTTP1/1 ";
    private String LASTMODIFIED  = "Last-Modified: ";
    private String CONTENTTYPE   = "Content-Type: ";
    private String CONTENTLENGTH = "Content-Length: ";
    private String CONNECTION    = "Connection: close";
    private String SERVER        = "Server: BoomTown";
    private String CRLF          = "\r\n";

    private PrintWriter outStream;
    private String httpStatus;
    private String lastModified;
    private String contentType;
    private String contentLength;

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy" );

    public HttpResponse(String contentType, String fileLength, String lastModified, String httpStatus, PrintWriter outStream) {
        this.contentType   = contentType;
        this.contentLength = fileLength;
        this.lastModified  = lastModified;
        this.httpStatus    = httpStatus;
        this.outStream     = outStream;
    }

    public void writeResponseHeaders() {
        outStream.write(HTTPVERSION + httpStatus + CRLF);
        outStream.write(CONNECTION + CRLF);
        outStream.write(dateFormat.format(new Date()) + CRLF);
        outStream.write(SERVER + CRLF);
        outStream.write(LASTMODIFIED + lastModified + CRLF);
        outStream.write(CONTENTTYPE + contentType + CRLF);
        outStream.write(CONTENTLENGTH + contentLength + CRLF);
        outStream.write(CRLF);
    }
}
