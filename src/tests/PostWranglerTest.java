package tests;

import org.junit.Before;
import org.junit.Test;
import server.GetWrangler;
import server.HttpRequestParser;
import server.PostWrangler;
import server.SocketWriter;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/5/13
 * Time: 1:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostWranglerTest {
    ByteArrayOutputStream dataStream    = new ByteArrayOutputStream();
    DataOutputStream outData            = new DataOutputStream( dataStream );
    OutputStreamWriter outWriter        = new OutputStreamWriter( dataStream );

    String testDir                      = "/Users/rickwinfrey/play/files";
    SocketWriter socketWriter;
    HttpRequestParser httpRequestParser;

    String postRequest = "POST /form HTTP/1.1\r\nContent-Length: 27\r\n\r\nPost-Data: name=rick&age=30\r\n";

    BufferedReader inputPostStream = new BufferedReader( new StringReader(postRequest));

    PostWrangler postWrangler;

    @Before
    public void setup() throws Exception {
        socketWriter = new SocketWriter(outData, outWriter);
        httpRequestParser = new HttpRequestParser(inputPostStream);
        postWrangler  = new PostWrangler(httpRequestParser, socketWriter, testDir );
    }

    @Test
    public void postWrangler() throws IOException {
        PostWrangler testWrangler = new PostWrangler(httpRequestParser, socketWriter, testDir);
        assertNotNull(testWrangler.httpRequestParser);
        assertNotNull(testWrangler.socketWriter);
        assertNotNull(testWrangler.directory);
        assertNotNull(testWrangler.mimeTypeMatcher);
        assertNotNull(testWrangler.httpGenerator);
    }

    @Test
    public void process() throws Exception {
        StringBuilder resultString = new StringBuilder();
        PostWrangler testWrangler = new PostWrangler(new HttpRequestParser(inputPostStream), socketWriter, testDir);
        String params = testWrangler.httpRequestParser.httpPostData();
        resultString.append(
                        "HTTP/1.1 200 OK\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Date: "+ postWrangler.dateFormat.format(new Date()) + "\r\n" +
                        "Server: BoomTown\r\n" +
                        "Last-Modified: "+ postWrangler.dateFormat.format(new Date()) + "\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n" +
                        "Content-Length: 265\r\n\r\n"
        );
        resultString.append(postWrangler.httpGenerator.generateFormParams(params));
        postWrangler.process();
        assertEquals(resultString.toString(), dataStream.toString());
    }

}
