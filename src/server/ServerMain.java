package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServerMain{
    private static boolean startable = true;
    private static int port = 5814;
    private static String directory = System.getProperty("user.dir");
    private static HtmlGenerator htmlGenerator = new HtmlGenerator();
    private static HttpServer httpServer;

    public static void main(String[] args) throws IOException {
        parseCommands(args);
        if ( startable == true ) {
            httpServer = new HttpServer(port);
            registerRoutes();
            httpServer.start();
        } else {
            return;
        }
    }

    public static void registerRoutes() {
        httpServer.registerRoute("/", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request)
            {
                Map<String, Object> response = new HashMap<String, Object>();
                String bodyString = htmlGenerator.generateIndex("", directory);
                response.put("body", convertStringToBytes(bodyString));
                putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                return response;
            }
        });

        httpServer.registerRoute("/hello", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request)
            {
                Map<String, Object> response = new HashMap<String, Object>();
                String bodyString = htmlGenerator.generateHello();
                response.put("body", convertStringToBytes(bodyString));
                putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                return response;
            }
        });

        httpServer.registerRoute("/time", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws InterruptedException {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
                Map<String, Object> response = new HashMap<String, Object>();
                Thread.sleep(1000);
                String bodyString = htmlGenerator.generateTime(dateFormat.format(new Date()));
                response.put("body", convertStringToBytes(bodyString));
                putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                return response;
            }
        });

        httpServer.registerRoute("/form", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request)
            {
                Map<String, Object> response = new HashMap<String, Object>();

                if (((Map) request.get("params")).isEmpty()) {
                    String bodyString = htmlGenerator.generateForm();
                    response.put("body", convertStringToBytes(bodyString));
                    putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                } else {
                    String bodyString = htmlGenerator.generateFormParams((Map) request.get("params"));
                    response.put("body", convertStringToBytes(bodyString));
                    putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                }

                return response;
            }
        });

        httpServer.registerRoute("default", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                Map<String, Object> response;

                if (isDirectory(directory + request.get("uri")))
                    response = directoryResponseGenerator(request);

                else if (resourceExists(expandFilePath((String) request.get("uri"))))
                    response = fileResponseGenerator(request);

                else
                    response = fourOhFourResponseGenerator(request);

                return response;
            }
        });

        httpServer.registerRoute("badRequest", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                return fourHundredResponseGenerator();
            }
        });
    }

    public static Map<String, Object> fileResponseGenerator(Map<String, Object> request) throws FileNotFoundException, IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        File requestedFile = new File(directory + request.get("uri"));

        FileInputStream fileStream = new FileInputStream(requestedFile);
        byte[] buf = new byte[(int) requestedFile.length()];
        fileStream.read(buf);
        response.put("body", buf);
        putHeadersInResponseMap(response, request, "200 OK", MimeTypeMatcher.getMimeType((String) request.get("uri")), buf.length);
        return response;
    }

    public static Map<String, Object> directoryResponseGenerator(Map<String, Object> request)
    {
       String bodyString = htmlGenerator.generateIndex((String) request.get("uri"), directory);
       return twoHundredResponseGenerator(request, bodyString, "text/html");
    }

    public static Map<String, Object> twoHundredResponseGenerator(Map<String, Object> request, String body, String mimeType)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("body", body.getBytes(Charset.forName("UTF-8")));
        putHeadersInResponseMap(response, request, "200 OK", mimeType, body.length());
        return response;
    }

    public static Map<String, Object> fourOhFourResponseGenerator(Map<String, Object> request)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        String bodyString = htmlGenerator.generate404();
        response.put("body", convertStringToBytes(bodyString));
        putHeadersInResponseMap(response, request, "404 Not Found", "text/html", 700);
        return response;
    }

    public static Map<String, Object> fourHundredResponseGenerator()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> request  = new HashMap<String, Object>();
        request.put("method", "GET");
        request.put("uri", "null");

        String bodyString = "Error 400. Bad Request";
        putHeadersInResponseMap(response, request, "400 Bad Request", "text/plain", 30);
        return response;
    }

    public static void putHeadersInResponseMap(Map<String, Object> response, Map<String, Object> request, String status, String contentType, int contentLength)
    {
        DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy");

        String CRLF = "\r\n";
        String RESPONSE = "HTTP/1.1 " + status;
        String CONNECTION = "Connection: Close";
        String DATE = "Date: " + dateFormat.format(new Date());
        String SERVER = "Server: BoomTown";
        String CONTENTTYPE = "Content-Type: " + contentType;
        String CONTENTLENGTH = "Content-Length: " + contentLength;

        response.put("response", (RESPONSE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("connection", (CONNECTION + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("date", (DATE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("server", (SERVER + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("content-type", (CONTENTTYPE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("content-length", (CONTENTLENGTH + CRLF + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("log", request.get("method") + " " + request.get("uri") + " " + status + "\r\n" + dateFormat.format(new Date()));
        response.put("end", (CRLF).getBytes(Charset.forName("UTF-8")));
    }

    public static boolean isDirectory(String dir) {
        File currDir = new File(dir);
        return currDir.isDirectory();
    }

    public static boolean resourceExists(String resource) {
       return new File( resource ).exists();
    }

    public static String expandFilePath(String resource) {
        return resource.replaceFirst("/", directory + "/");
    }

    public static byte[] convertStringToBytes(String bodyString)
    {
       return bodyString.getBytes(Charset.forName("UTF-8"));
    }

    private static void parseCommands(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].equals("-p"))
            {
                port = Integer.parseInt(args[i + 1]);
                startable = true;
            }
            else if(args[i].equals("-d"))
            {
                directory = args[i + 1];
                startable = true;
            }
            else if(args[i].equals("-h"))
            {
                startable = false;
                displayHelp();
            }
            else if(args[i].equals("--help"))
            {
                startable = false;
                displayHelp();
            }
        }
    }

    public static void displayHelp()
    {
        System.out.println("\nUsage:");
        System.out.println("java -jar rickhttp.jar -p <port> -d <directory/to/serve>");
        System.out.println("\nExample:");
        System.out.println("$ java -jar rickhttp.jar -p 3000 -d ~/Documents\n\n");
    }

    public static void stop() throws IOException
    {
        httpServer.stop();
    }
}
