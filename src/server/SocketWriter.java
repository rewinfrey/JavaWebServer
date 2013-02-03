package server;

import server.Logger;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class SocketWriter {
    private DataOutputStream outDataStream;
    private Logger logger = new Logger();

    private String HTTPVERSION   = "HTTP/1.1 ";
    private String LASTMODIFIED  = "Last-Modified: ";
    private String CONTENTTYPE   = "Content-Type: ";
    private String CONTENTLENGTH = "Content-Length: ";
    private String CONNECTION    = "Connection: close";
    private String SERVER        = "Server: BoomTown";
    private String DATE          = "Date: ";
    private String CRLF          = "\r\n";

    private String httpStatus;
    private String lastModified;
    private String contentType;
    private String contentLength;

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );

    public SocketWriter(DataOutputStream outStream) throws IOException {
        this.outDataStream = outStream;
    }

    public void setResponseHeaders(String contentType, String fileLength, String lastModified, String httpStatus) {
        this.contentType     = contentType;
        this.contentLength   = fileLength;
        this.lastModified    = lastModified;
        this.httpStatus      = httpStatus;
    }

    public void writeResponseHeaders() throws IOException {
        outDataStream.writeBytes(HTTPVERSION + httpStatus + CRLF);
        outDataStream.writeBytes(CONNECTION + CRLF);
        outDataStream.writeBytes(DATE + dateFormat.format(new Date()) + CRLF);
        outDataStream.writeBytes(SERVER + CRLF);
        outDataStream.writeBytes(LASTMODIFIED + lastModified + CRLF);
        outDataStream.writeBytes(CONTENTTYPE + contentType + CRLF);
        outDataStream.writeBytes(CONTENTLENGTH + contentLength + CRLF);
        outDataStream.writeBytes(CRLF);
    }

    public void writeLogToTerminal(String requestLine, String requestStatus) {
        String requestSummary = requestLine + " " + requestStatus;
        logger.request(requestSummary, dateFormat.format(new Date()));
    }

    public void writeOutputToClient(String output) throws IOException {
        outDataStream.writeBytes(output);
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

    public void closeRequest() throws IOException {
        outDataStream.close();
    }
}
