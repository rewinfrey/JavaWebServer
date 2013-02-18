package server;

import java.util.Map;

public interface Responder
{
  Map<String, Object> respond(Map<String, Object> request);
}
