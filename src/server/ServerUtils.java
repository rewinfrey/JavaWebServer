package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 2/24/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerUtils {
    public static HtmlGenerator htmlGenerator = new HtmlGenerator();

    public static Map<String, Object> fileResponseGenerator(Map<String, Object> request, String rootDirectory) throws FileNotFoundException, IOException {
        Map<String, Object> response = new HashMap<String, Object>();
        File requestedFile = new File(rootDirectory + request.get("uri"));

        FileInputStream fileStream = new FileInputStream(requestedFile);
        byte[] buf = new byte[(int) requestedFile.length()];
        fileStream.read(buf);
        response.put("body", buf);
        putHeadersInResponseMap(response, request, "200 OK", MimeTypeMatcher.getMimeType((String) request.get("uri")), buf.length);
        return response;
    }

    public static Map<String, Object> directoryResponseGenerator(Map<String, Object> request, String rootDirectory)
    {
       String bodyString = htmlGenerator.generateIndex((String) request.get("uri"), rootDirectory);
       return twoHundredResponseGenerator(request, bodyString, "text/html");
    }

    public static Map<String, Object> twoHundredResponseGenerator(Map<String, Object> request, String body, String mimeType)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("body", body.getBytes(Charset.forName("UTF-8")));
        putHeadersInResponseMap(response, request, "200 OK", mimeType, body.length());
        return response;
    }

    public static Map<String, Object> fourOhFourResponseGenerator(Map<String, Object> request)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        String bodyString = htmlGenerator.generate404();
        response.put("body", convertStringToBytes(bodyString));
        putHeadersInResponseMap(response, request, "404 Not Found", "text/html", 700);
        return response;
    }

    public static Map<String, Object> fourHundredResponseGenerator()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> request  = new HashMap<String, Object>();
        request.put("method", "GET");
        request.put("uri", "null");

        String bodyString = "Error 400. Bad Request";
        putHeadersInResponseMap(response, request, "400 Bad Request", "text/plain", 30);
        return response;
    }

    public static void putHeadersInResponseMap(Map<String, Object> response, Map<String, Object> request, String status, String contentType, int contentLength)
    {
        DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy");

        String CRLF = "\r\n";
        String RESPONSE = "HTTP/1.1 " + status;
        String CONNECTION = "Connection: Close";
        String DATE = "Date: " + dateFormat.format(new Date());
        String SERVER = "Server: BoomTown";
        String CONTENTTYPE = "Content-Type: " + contentType;
        String CONTENTLENGTH = "Content-Length: " + contentLength;

        response.put("response", (RESPONSE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("connection", (CONNECTION + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("date", (DATE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("server", (SERVER + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("content-type", (CONTENTTYPE + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("content-length", (CONTENTLENGTH + CRLF + CRLF).getBytes(Charset.forName("UTF-8")));
        response.put("log", request.get("method") + " " + request.get("uri") + " " + status + "\r\n" + dateFormat.format(new Date()));
        response.put("end", (CRLF).getBytes(Charset.forName("UTF-8")));
    }

    public static boolean isDirectory(String dir) {
        File currDir = new File(dir);
        return currDir.isDirectory();
    }

    public static boolean resourceExists(String resource) {
       return new File( resource ).exists();
    }

    public static String expandFilePath(String resource, String rootDirectory) {
        return resource.replaceFirst("/", rootDirectory + "/");
    }

    public static byte[] convertStringToBytes(String bodyString)
    {
       return bodyString.getBytes(Charset.forName("UTF-8"));
    }
}
