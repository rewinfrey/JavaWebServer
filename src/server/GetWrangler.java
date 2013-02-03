package server;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetWrangler extends Wrangler {
    private SocketWriter socketWriter;
    private String directory;
    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );
    private HttpRequestParser httpRequestParser;
    private HttpGenerator httpGenerator = new HttpGenerator();

    private ResourceFetcher resourceFetcher;


    private String[] preDefinedRoutes = {"/", "/hello", "/time", "/form"};

    public GetWrangler(HttpRequestParser httpRequestParser, SocketWriter socketWriter, String directory) throws IOException {
        this.httpRequestParser = httpRequestParser;
        this.socketWriter      = socketWriter;
        this.resourceFetcher   = new ResourceFetcher(directory);
        this.directory         = directory;
    }

    @Override
    public void process() throws IOException, InterruptedException {
        if ( preDefinedRoute() ) {
            int count = 0;
            for ( String route : preDefinedRoutes ) {
                if ( route.equals(httpRequestParser.httpRequestResource())) {
                    break;
                }
                count++;
            }
            processPredefinedRoute(count);
        } else {
            processRoute();
        }
    }


    private boolean preDefinedRoute() {
        for( String route : preDefinedRoutes ) {
            if ( route.equals(httpRequestParser.httpRequestResource()) ) {
                return true;
            }
        }
        return false;
    }

    private void processPredefinedRoute(int route) throws IOException, InterruptedException {
        switch(route) {
            case 0:
                getRoot(directory);
                break;
            case 1:
                getHello();
                break;
            case 2:
                getTime();
                break;
            case 3:
                getForm();
                break;
            default:
                processRoute();
        }
    }

    private void processRoute() throws IOException {
        if ( isDirectory(directory + httpRequestParser.httpRequestResource())) {
            getRoot(directory + httpRequestParser.httpRequestResource());
        } else  if ( resourceExists(expandFilePath(httpRequestParser.httpRequestResource()))) {
            validFileStream(expandFilePath(httpRequestParser.httpRequestResource()));
        } else {
           bogusFileStream(httpRequestParser.httpRequestResource());
        }
    }

    private boolean isDirectory(String dir) {
        System.out.println(dir);
        File currDir = new File(dir);
        return currDir.isDirectory();
    }

    private boolean resourceExists(String resource) {
       return resourceFetcher.exists(resource);
    }

    private String expandFilePath(String resource) {
        return resourceFetcher.expandedPath(resource);
    }

    private void getRoot(String dir) throws IOException {
        String htmlString = httpGenerator.generateIndex(dir, directory);
        outToSocket(htmlString, "200 OK");
    }

    private void getHello() throws IOException {
        validFileStream(directory + "/hello.html");
    }

    private void getTime() throws InterruptedException, IOException {
        Thread.sleep(1000);
        String htmlString = httpGenerator.generateTime();
        outToSocket(htmlString, "200 OK");
    }

    private void outToSocket(String outString, String outStatus) throws IOException {
        socketWriter.setResponseHeaders("text/html; charset=UTF-8", outString.getBytes().length+"", dateFormat.format(new Date()), outStatus);
        socketWriter.writeResponseHeaders();
        socketWriter.writeOutputToClient(outString);
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, outStatus);
    }

    private void getForm() throws IOException {
        String htmlString = httpGenerator.generateForm();
        outToSocket(htmlString, "200 OK");
    }


    private void validFileStream(String fileName) throws IOException {
        File requestedFile = new File(fileName);
        writeFileToSocket(fileName, requestedFile);
        writeLogToTerminal("200 OK");
   }

    private void bogusFileStream(String fileName) throws IOException {
        System.out.println("preparing 404 string");
        String notFoundHtml = httpGenerator.generate404();
        System.out.println(notFoundHtml);
        outToSocket(notFoundHtml, "404 Not Found");
    }

    private void writeFileToSocket(String fileName, File requestedFile) throws IOException {
        socketWriter.setResponseHeaders(mimeTypeMatcher.getMimeType(fileName), requestedFile.length() + "", requestedFile.lastModified() + "", "200 OK");
        socketWriter.writeResponseHeaders();
        socketWriter.writeFileToClient(fileName);
    }

    private void writeLogToTerminal(String status) {
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, status);
    }
}
