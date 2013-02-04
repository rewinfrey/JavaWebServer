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

    public PostWrangler(HttpRequestParser httpRequestParser, SocketWriter socketWriter, String directory) throws IOException {
        super(httpRequestParser, socketWriter, directory);
    }

    @Override
    public void process() throws IOException {
        String formResult = constructParamsList(httpRequestParser.httpPostData());
        outToSocket(formResult, "200 OK");
    }

    public String constructParamsList(String postContent) {
        return httpGenerator.generateFormParams(postContent);
    }
}
