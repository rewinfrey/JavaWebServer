package server;

import java.io.IOException;
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
                response.put("body", ServerUtils.convertStringToBytes(bodyString));
                ServerUtils.putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
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
                response.put("body", ServerUtils.convertStringToBytes(bodyString));
                ServerUtils.putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
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
                response.put("body", ServerUtils.convertStringToBytes(bodyString));
                ServerUtils.putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
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
                    response.put("body", ServerUtils.convertStringToBytes(bodyString));
                    ServerUtils.putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                } else {
                    String bodyString = htmlGenerator.generateFormParams((Map) request.get("params"));
                    response.put("body", ServerUtils.convertStringToBytes(bodyString));
                    ServerUtils.putHeadersInResponseMap(response, request, "200 OK", "text/html", bodyString.length());
                }

                return response;
            }
        });

        httpServer.registerRoute("default", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                Map<String, Object> response;

                if (ServerUtils.isDirectory(directory + request.get("uri")))
                    response = ServerUtils.directoryResponseGenerator(request, directory);

                else if (ServerUtils.resourceExists(ServerUtils.expandFilePath((String) request.get("uri"), directory)))
                    response = ServerUtils.fileResponseGenerator(request, directory);

                else
                    response = ServerUtils.fourOhFourResponseGenerator(request);

                return response;
            }
        });

        httpServer.registerRoute("badRequest", new Responder()
        {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                return ServerUtils.fourHundredResponseGenerator();
            }
        });
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
