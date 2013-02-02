package com.rick_http_server;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocketWriter {
    private Socket request;
    private PrintWriter outPrintStream;
    private DataOutputStream outDataStream;
    private Logger logger = new Logger();

    private String HTTPVERSION   = "HTTP1/1 ";
    private String LASTMODIFIED  = "Last-Modified: ";
    private String CONTENTTYPE   = "Content-Type: ";
    private String CONTENTLENGTH = "Content-Length: ";
    private String CONNECTION    = "Connection: close";
    private String SERVER        = "Server: BoomTown";
    private String CRLF          = "\r\n";

    private String httpStatus;
    private String lastModified;
    private String contentType;
    private String contentLength;

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss dd/MM/yyyy" );

    public SocketWriter(Socket request) throws IOException {
        this.request = request;
        setIOStreams();
    }

    public void setResponseHeaders(String contentType, String fileLength, String lastModified, String httpStatus) {
        this.contentType     = contentType;
        this.contentLength   = fileLength;
        this.lastModified    = lastModified;
        this.httpStatus      = httpStatus;
    }

    public void writeResponseHeaders() {
        outPrintStream.write(HTTPVERSION + httpStatus + CRLF);
        outPrintStream.write(CONNECTION + CRLF);
        outPrintStream.write(dateFormat.format(new Date()) + CRLF);
        outPrintStream.write(SERVER + CRLF);
        outPrintStream.write(LASTMODIFIED + lastModified + CRLF);
        outPrintStream.write(CONTENTTYPE + contentType + CRLF);
        outPrintStream.write(CONTENTLENGTH + contentLength + CRLF);
        outPrintStream.write(CRLF);
    }

    public void writeLogToTerminal(String requestLine, String requestStatus) {
        String requestSummary = requestLine + " " + requestStatus;
        logger.request(requestSummary, dateFormat.format(new Date()));
    }

    public void writeOutputToClient(String output) throws IOException {
        outDataStream.writeBytes(output);
        outDataStream.flush();
        closeRequest();
    }

    public void writeFileToClient(String fileName) {
        try {
            FileInputStream inFileStream = new FileInputStream( new File( fileName ) );
            byte[] buf                   = new byte[1024];
            int count = 0;
            while ((count = inFileStream.read(buf)) >= 0) {
                outDataStream.write(buf, 0, count);
            }
            inFileStream.close();
            outDataStream.flush();
            closeRequest();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( NullPointerException f ) {
            f.printStackTrace();
        }
    }

    private void setIOStreams() throws IOException {
        this.outPrintStream = new PrintWriter( request.getOutputStream(), true );
        this.outDataStream  = new DataOutputStream( request.getOutputStream() );
    }

    public void closeRequest() throws IOException {
        outDataStream.close();
        outPrintStream.close();
        request.close();
    }
}
