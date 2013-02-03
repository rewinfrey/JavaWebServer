package server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostWrangler extends Wrangler {
    private SocketWriter socketWriter;
    private HttpRequestParser httpRequestParser;

    public PostWrangler(HttpRequestParser httpRequestParser, SocketWriter socketWriter) throws IOException {
        this.httpRequestParser = httpRequestParser;
        this.socketWriter      = socketWriter;
    }

    @Override
    public void process() throws IOException {
        String formResult = constructParamsList(httpRequestParser.httpPostData());
        socketWriter.writeOutputToClient(formResult);
    }

    public String constructParamsList(String postContent) {
        socketWriter.writeLogToTerminal(httpRequestParser.requestLine, "200 OK");
        String[] parsedPostData = postContent.split("&");
        StringBuilder paramsString = new StringBuilder();
        for(String value: parsedPostData)
            paramsString.append(value.replace("=", " = ") + "\r\n");
        return paramsString.toString();
    }
}
