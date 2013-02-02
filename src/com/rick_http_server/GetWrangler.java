package com.rick_http_server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetWrangler extends Wrangler {
    private Socket request;
    private SocketWriter socketWriter;

    private HttpRequestParser httpRequestParser;

    private ResourceFetcher resourceFetcher;


    private String[] preDefinedRoutes = {"/", "/hello", "/time", "/form"};

    public GetWrangler(HttpRequestParser httpRequestParser, Socket request) throws IOException {
        this.httpRequestParser = httpRequestParser;
        this.request           = request;
        this.socketWriter      = new SocketWriter(request);
        this.resourceFetcher   = new ResourceFetcher();
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
                getRoot();
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
        if ( resourceExists(expandFilePath(httpRequestParser.httpRequestResource()))) {
            validFileStream(expandFilePath(httpRequestParser.httpRequestResource()));
        } else {
           bogusFileStream(httpRequestParser.httpRequestResource());
        }
    }

    private boolean resourceExists(String resource) {
       return resourceFetcher.exists(resource);
    }

    private String expandFilePath(String resource) {
        return resourceFetcher.expandedPath(resource);
    }

    private void getRoot() throws IOException {
        buildIndex();
        validFileStream("files/index.html");
    }

    private void getHello() throws IOException {
        validFileStream("files/hello.html");
    }

    private void getTime() throws InterruptedException, IOException {
        Thread.sleep(1000);
        socketWriter.writeOutputToClient(dateFormat.format(new Date()));
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, "200 OK" );
    }

    private void getForm() throws IOException {
        validFileStream("files/form.html");
    }

    private void buildIndex() throws IOException {
        BufferedWriter fOut = new BufferedWriter ( new FileWriter("files/index.html") );

        fOut.write("<!DOCTYPE HTML><html><head></head><body><h1>Index</h1>\n");
        File directory = new File("files/");
        File[] files = directory.listFiles();
        for ( int index = 0; index < files.length; index++) {
            fOut.write("<a href=\"" + files[index].getName() + "\">" + files[index].getName() + "</a><br />\n");
        }
        fOut.write("</body></html>");
        fOut.flush();
        fOut.close();
    }

    private void buildForm() throws IOException {
        BufferedWriter fOut = new BufferedWriter ( new FileWriter("files/form.html") );

        fOut.write("<!DOCTYPE HTML><html><head></head><body><div style=\"margin: auto; width: 206px;\"><h1 style=\"text-align: center;\">Form!</h1>\n");
        fOut.write("<form action=\"/form\" method=\"post\">");
        fOut.write("<ul style=\"list-style: none;\">");
        fOut.write("<li style=\"margin-top: 20px;\"><label for=\"name1\"></label><input type=\"text\" name=\"name1\" value=\"one\"></li>");
        fOut.write("<li style=\"margin-top: 20px;\"><label for=\"name2\"></label><input type=\"text\" name=\"name2\" value=\"two\"></li>");
        fOut.write("<li style=\"margin-top: 20px;\"><label for=\"name3\"></label><input type=\"text\" name=\"name3\" value=\"three\"></li>");
        fOut.write("<input style=\"width: 80px; margin: 20px 22px;\" type=\"submit\" />");
        fOut.write("</ul></form></div></body></html>");
        fOut.flush();
        fOut.close();
    }

    private void validFileStream(String fileName) throws IOException {
        File requestedFile = new File(fileName);
        writeFileToSocket(fileName, requestedFile);
        writeLogToTerminal("200 OK");
   }

    private void bogusFileStream(String fileName) throws IOException {
        File requestedFile = new File("files/404.html");
        writeFileToSocket("files/404.html", requestedFile);
        writeLogToTerminal("404 NotFound");
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
