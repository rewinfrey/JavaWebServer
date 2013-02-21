package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router implements Responder
{
  private Map<String, Responder> routes = new HashMap<String, Responder>();

  public Map<String, Object> respond(Map<String, Object> request) throws IOException, InterruptedException {
    if (request.get("uri") == null) // added to handle Chrome's phantom requests
        return routes.get("badRequest").respond(request);

    final String uri = (String)request.get("uri");
    final Responder responder = routes.get(uri);

    if(responder != null)
        return responder.respond(request);

    else
        return routes.get("default").respond(request);

  }

  public void register(String route, Responder responder)
  {
    routes.put(route, responder);
  }
}
