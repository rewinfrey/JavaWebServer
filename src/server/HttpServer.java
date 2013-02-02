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
    private Thread serverThread;
    private ServerSocket welcomeSocket;
    private static int port = 5813;
    private static String directory = "";

    public static void main(String[] args) throws IOException {
        parseCommands(args);
        HttpServer server = new HttpServer(port);
        server.bindServerSocket();
        server.serverThreadStart();
    }

    private static void parseCommands(String[] args) {
        for(int i=0; i < args.length; i++ ) {
            if (args[i].equals("-p")) {
                port = Integer.parseInt(args[i+1]);
            }
        }
    }

    public HttpServer(int port) {
        this.port = port;
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
                System.out.println("NullPointerException caught");
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
            System.out.println("Server is running...please make your requests to http://localhost:"+port);
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

