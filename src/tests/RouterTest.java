package tests;

import org.junit.Before;
import org.junit.Test;
import server.Responder;
import server.Router;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertSame;

public class RouterTest
{

  public Router router;
  public MemoryResponder defaultResponder;

  private static class MemoryResponder implements Responder
  {
    private Map<String, Object> response;
    private Map<String, Object> request;

    public Map<String, Object> respond(Map<String, Object> request)
    {
      this.request = request;
      return response;
    }
  }


  @Before
  public void setUp() throws Exception
  {
    router = new Router();
    defaultResponder = new MemoryResponder();
  }

  @Test
  public void routesRoot() throws Exception
  {
    router.register("/", defaultResponder);

    defaultResponder.response = new HashMap<String, Object>();

    Map<String, Object> request = new HashMap<String, Object>();
    request.put("uri", "/");

    Map<String, Object> actualResponse = router.respond(request);

    assertSame(actualResponse, defaultResponder.response);
    assertSame(defaultResponder.request, request);
  }

  @Test
  public void unregisteredRoute() throws Exception
  {
    Map<String, Object> request = new HashMap<String, Object>();
    request.put("uri", "/unregistered_route");

    router.register("default", defaultResponder);
    Map<String, Object> actualResponse = router.respond(request);

    assertSame(null, actualResponse);
  }

  @Test
  public void handlePhantomRequests() throws Exception
  {
      Map<String, Object> request = new HashMap<String, Object>();
      request.put( null, null );

      router.register("badRequest", defaultResponder);
      Map<String, Object> actualResponse = router.respond(request);

      assertSame(null, actualResponse);
  }
}
