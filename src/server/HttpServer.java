package server;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/29/13
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpServer implements Runnable {
    public static HttpServer server;
    private Thread serverThread;
    private ServerSocket welcomeSocket;
    public static boolean start = true;
    public static int port = 5813;
    public static String directory = "/Users";

    public static void main(String[] args) throws IOException {
        parseCommands(args);
        if ( start  == true ) {
            server = new HttpServer(port, directory);
            server.bindServerSocket();
            server.serverThreadStart();
        } else {
            return;
        }
    }

    private static void parseCommands(String[] args) {
        for(int i=0; i < args.length; i++ ) {
            if (args[i].equals("-p")) {
                port = Integer.parseInt(args[i+1]);
                start = true;
            } else if (args[i].equals("-d")) {
                directory = args[i + 1];
                start = true;
            } else if (args[i].equals("-h")) {
                start = false;
                displayHelp();
            } else if (args[i].equals("--help")) {
                start = false;
                displayHelp();
            }
        }
    }

    public static void displayHelp() {
        System.out.println("\nUsage:");
        System.out.println("java -jar out/artifacts/RickHttpServer_jar/RickHttpServer.jar -p <port> -d <directory/to/serve>");
        System.out.println("\nExample:");
        System.out.println("$ java -jar out/artifacts/RickHttpServer_jar/RickHttpServer.jar -p 3000 -d /Users/rickwinfrey/docs\n\n");
    }

    public HttpServer(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    public void runServer() throws IOException {
        System.out.println("\nServer started on port: " + port);
        while(isBound()) {
                Socket clientSocket          = welcomeSocket.accept();
                RequestHandler newRequest    = new RequestHandler(clientSocket, directory);
                Thread clientThread          = new Thread(newRequest);
                clientThread.start();
        }
    }

    public void bindServerSocket() throws IOException {
        welcomeSocket = new ServerSocket(port);
    }

    public void serverThreadStart() throws IOException {
        serverThread  = new Thread(this);
        serverThread.start();
    }

    public boolean isBound() throws IOException {
        return welcomeSocket.isBound();
    }

    public boolean isClosed() throws IOException {
        return welcomeSocket.isClosed();
    }

    public void stop() throws IOException, InterruptedException {
        serverThread.interrupt();
        unBindServerSocket();
        System.out.println("\nServer stopped on port: "+port);

    }

    public void unBindServerSocket() throws IOException {
        welcomeSocket.close();
    }

    public boolean isAlive() {
        return serverThread.isAlive();
    }

    public boolean isInterrupted() {
        return serverThread.isInterrupted();
    }

    @Override
    public void run() {
        try {
            runServer();
        } catch (Exception e) {
            System.out.println("\nThe server could not be started at the specified port: " + port);
            start = false;
        }
    }
}

