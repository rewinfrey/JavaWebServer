package server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {
    public static void write(Map<String, Object> responseMap, OutputStream outStream) throws IOException
    {
       if (responseMap.get("response") == null)
       {
           outStream.close();
           return;
       }
       log(responseMap);
       outStream.write((byte[])responseMap.get("response"));
       outStream.write((byte[])responseMap.get("connection"));
       outStream.write((byte[])responseMap.get("date"));
       outStream.write((byte[])responseMap.get("server"));
       outStream.write((byte[])responseMap.get("content-type"));
       outStream.write((byte[])responseMap.get("content-length"));
       outStream.write((byte[])responseMap.get("body"));
       outStream.write((byte[])responseMap.get("end"));
       outStream.flush();
       outStream.close();
    }

    public static void log(Map<String, Object> responseMap)
    {
        System.out.println(responseMap.get("log") + "\r\n");
    }
}
