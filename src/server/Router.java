package server;

import java.util.HashMap;
import java.util.Map;

public class Router implements Responder
{
  private Map<String, Responder> routes = new HashMap<String, Responder>();

  public Map<String, Object> respond(Map<String, Object> request)
  {
    final String uri = (String)request.get("uri");
    final Responder responder = routes.get(uri);
    if(responder != null)
      return responder.respond(request);
    else
      return null;
  }

  public void register(String route, Responder responder)
  {
    routes.put(route, responder);
  }
}
