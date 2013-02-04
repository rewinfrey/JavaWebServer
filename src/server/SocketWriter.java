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
    public OutputStreamWriter outStreamWriter;
    public DataOutputStream outDataStream;
    public Logger logger = new Logger();

    public String HTTPVERSION   = "HTTP/1.1 ";
    public String LASTMODIFIED  = "Last-Modified: ";
    public String CONTENTTYPE   = "Content-Type: ";
    public String CONTENTLENGTH = "Content-Length: ";
    public String CONNECTION    = "Connection: keep-alive";
    public String SERVER        = "Server: BoomTown";
    public String DATE          = "Date: ";
    public String CRLF          = "\r\n";

    public String httpStatus;
    public String lastModified;
    public String contentType;
    public String contentLength;

    public DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );

    public SocketWriter(DataOutputStream outDataStream, OutputStreamWriter outStreamWriter) throws IOException {
        this.outDataStream = outDataStream;
        this.outStreamWriter = outStreamWriter;
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
        outStreamWriter.write(output);
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
            closeRequest();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( NullPointerException f ) {
            f.printStackTrace();
        }
    }

    public void closeRequest() throws IOException {
        outStreamWriter.flush();
        outStreamWriter.close();
        outDataStream.flush();
        outDataStream.close();
    }
}
