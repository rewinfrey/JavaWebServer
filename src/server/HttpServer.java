package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class HttpServer implements Runnable
{
  private int port;
  private Router router;
  public Thread serverThread;
  private ServerSocket welcomeSocket;

  public HttpServer(int port)
  {
    this.port = port;
    this.router = new Router();
  }

  public void start() throws IOException
  {
      bindServerSocket();
      serverThreadStart();
  }

  public void stop() throws IOException
  {
      welcomeSocket.close();
  }

  public void registerRoute(String uri, Responder responder)
  {
    router.register(uri, responder);
  }

  public void runServer() throws IOException
  {
    System.out.println("\nServer started on port: " + port);
      try{
        while(true)
        {
          final Socket clientSocket = welcomeSocket.accept();
          new Thread(new Runnable(){
            public void run()
            {
                try
                {
                    Map<String, Object> request = HttpRequestParser.parse(clientSocket.getInputStream());
                    Map<String, Object> responseMap = router.respond(request);
                    HttpResponse.write(responseMap, clientSocket.getOutputStream());
                    clientSocket.close();
                }
                catch (Exception e) {}
            }
          }).start();
        }
      } catch(Exception e) {};
  }

  private void bindServerSocket() throws IOException
  {
    welcomeSocket = new ServerSocket(port);
  }

  private void serverThreadStart() throws IOException
  {
    serverThread = new Thread(this);
    serverThread.start();
  }

  public boolean isBound() throws IOException
  {
    return welcomeSocket.isBound();
  }

  @Override
  public void run()
  {
    try
    {
      runServer();
    }
    catch(Exception e)
    {}
  }
}

