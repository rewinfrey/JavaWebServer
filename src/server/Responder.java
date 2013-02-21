package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface Responder
{
  Map<String, Object> respond(Map<String, Object> request) throws IOException, InterruptedException;
}
