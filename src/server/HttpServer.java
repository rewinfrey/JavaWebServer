package server;

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
    public static String directory = "/Users/rickwinfrey/serverTestDir";

    public static void main(String[] args) throws IOException {
        parseCommands(args);
        if ( start  == true ) {
            System.out.println(start);
            server = new HttpServer(port, directory);
            server.bindServerSocket();
            server.serverThreadStart();
        } else {
            System.out.println("not running");
            return;
        }
    }

    private static void parseCommands(String[] args) {
        for(int i=0; i < args.length; i++ ) {
            if (args[i].equals("-p")) {
                port = Integer.parseInt(args[i+1]);
            } else if (args[i].equals("-d")) {
                directory = args[i + 1];
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
        System.out.println("\nUsage: java some_path_to_jar.jar -p 3000 -d dir/you/want/served");
    }

    public HttpServer(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    public void runServer() throws IOException {
        while(isBound()) {
            try {
                Socket clientSocket          = welcomeSocket.accept();
                RequestHandler newRequest    = new RequestHandler(clientSocket, directory);
                Thread clientThread          = new Thread(newRequest);
                clientThread.start();
            } catch ( SocketException f ) {
            } catch ( NullPointerException g ) {
            }
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
        System.out.println("Server stopped on port: "+port);

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
            System.out.println("Server started on port: "+port);
            runServer();
        } catch (IOException e) {
            try {
                unBindServerSocket();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}

