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

    public String[] preDefinedRoutes = {"/", "/hello", "/time", "/form"};
    public ResourceFetcher resourceFetcher;
    public GetWrangler(HttpRequestParser httpRequestParser, SocketWriter socketWriter, String directory) throws IOException {
        super(httpRequestParser, socketWriter, directory);
        this.resourceFetcher   = new ResourceFetcher(directory);
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


    public boolean preDefinedRoute() {
        for( String route : preDefinedRoutes ) {
            if ( route.equals(httpRequestParser.httpRequestResource()) ) {
                return true;
            }
        }
        return false;
    }

    public void processPredefinedRoute(int route) throws IOException, InterruptedException {
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

    public void processRoute() throws IOException {
        if ( isDirectory(directory + httpRequestParser.httpRequestResource())) {
           getRoot(directory + httpRequestParser.httpRequestResource());
        } else if ( resourceExists(expandFilePath(httpRequestParser.httpRequestResource()))) {
           validFileStream(expandFilePath(httpRequestParser.httpRequestResource()));
        } else if ( httpRequestParser.httpRequestResource().startsWith("/form?")) {
           getPostUriParams();
        } else {
           bogusFileStream(httpRequestParser.httpRequestResource());
        }
    }

    public void getPostUriParams() throws IOException {
        StringBuilder postParams = new StringBuilder();
        String temp = httpRequestParser.httpRequestResource();
        String params = temp.replace("/form?", "");
        String htmlParams = httpGenerator.generateFormParams(params);
        outToSocket(htmlParams, "200 OK");
    }

    public boolean isDirectory(String dir) {
        System.out.println("\nGeWrangler # isDirectory()");
        System.out.println(dir);
        File currDir = new File(dir);
        return currDir.isDirectory();
    }

    public boolean resourceExists(String resource) {
       return resourceFetcher.exists(resource);
    }

    public String expandFilePath(String resource) {
        return resourceFetcher.expandedPath(resource);
    }

    public void getRoot(String dir) throws IOException {
        String htmlString = httpGenerator.generateIndex(dir, directory);
        outToSocket(htmlString, "200 OK");
    }

    public void getHello() throws IOException {
        String htmlString = httpGenerator.generateHello();
        outToSocket(htmlString, "200 OK");
    }

    public void getTime() throws InterruptedException, IOException {
        Thread.sleep(1000);
        String time = dateFormat.format(new Date());
        String htmlString = httpGenerator.generateTime(time);
        outToSocket(htmlString, "200 OK");
    }

    public void getForm() throws IOException {
        String htmlString = httpGenerator.generateForm();
        outToSocket(htmlString, "200 OK");
    }


    public void validFileStream(String fileName) throws IOException {
        File requestedFile = new File(fileName);
        writeFileToSocket(fileName, requestedFile);
        writeLogToTerminal("200 OK");
   }

    public void bogusFileStream(String fileName) throws IOException {
        System.out.println("\nGetWranger # preparing 404 string");
        String notFoundHtml = httpGenerator.generate404();
        System.out.println(notFoundHtml);
        outToSocket(notFoundHtml, "404 Not Found");
    }

    public void writeFileToSocket(String fileName, File requestedFile) throws IOException {
        System.out.println("Requested file content-length: "+requestedFile.length());
        socketWriter.setResponseHeaders(mimeTypeMatcher.getMimeType(fileName), requestedFile.length() + "", requestedFile.lastModified() + "", "200 OK", dateFormat.format(new Date()));
        socketWriter.writeResponseHeaders();
        socketWriter.writeFileToClient(fileName);
    }

    public void writeLogToTerminal(String status) {
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, status, dateFormat.format(new Date()));
    }
}
