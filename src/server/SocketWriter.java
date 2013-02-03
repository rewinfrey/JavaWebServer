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
    private OutputStreamWriter outStreamWriter;
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
        outStreamWriter.write(HTTPVERSION + httpStatus + CRLF);
        outStreamWriter.write(CONNECTION + CRLF);
        outStreamWriter.write(DATE + dateFormat.format(new Date()) + CRLF);
        outStreamWriter.write(SERVER + CRLF);
        outStreamWriter.write(LASTMODIFIED + lastModified + CRLF);
        outStreamWriter.write(CONTENTTYPE + contentType + CRLF);
        outStreamWriter.write(CONTENTLENGTH + contentLength + CRLF);
        outStreamWriter.write(CRLF);
    }

    public void writeLogToTerminal(String requestLine, String requestStatus) {
        String requestSummary = requestLine + " " + requestStatus;
        logger.request(requestSummary, dateFormat.format(new Date()));
    }

    public void writeOutputToClient(String output) throws IOException {
        System.out.println(output);
        outStreamWriter.write(output);
        outStreamWriter.flush();
        //outDataStream.writeUTF(output);
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
        outStreamWriter.close();
        outDataStream.close();
    }
}
