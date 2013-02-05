package tests;

import org.junit.Before;
import org.junit.Test;
import server.GetWrangler;
import server.HttpRequestParser;
import server.SocketWriter;
import server.Wrangler;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/5/13
 * Time: 2:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class WranglerTest {
    ByteArrayOutputStream dataStream    = new ByteArrayOutputStream();
    DataOutputStream outData            = new DataOutputStream( dataStream );
    OutputStreamWriter outWriter        = new OutputStreamWriter( dataStream );

    String testDir                      = "/Users/rickwinfrey/play/files";
    SocketWriter socketWriter;
    HttpRequestParser httpRequestParser;

    String getRequest  = "GET / HTTP/1.1";

    BufferedReader inputGetStream = new BufferedReader( new StringReader(getRequest));

    Wrangler wrangler;

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() throws Exception {
        socketWriter = new SocketWriter(outData, outWriter);
        httpRequestParser = new HttpRequestParser(inputGetStream);
        wrangler  = new Wrangler(httpRequestParser, socketWriter, testDir );
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void wrangler() {
        Wrangler testWrangler = new Wrangler(httpRequestParser, socketWriter, testDir);
        assertNotNull(testWrangler.httpRequestParser);
        assertNotNull(testWrangler.socketWriter);
        assertNotNull(testWrangler.directory);
        assertNotNull(testWrangler.mimeTypeMatcher);
        assertNotNull(testWrangler.httpGenerator);
        assertNotNull(testWrangler.dateFormat);
    }

    @Test
    public void process() throws IOException, InterruptedException {
        wrangler.process();
    }
}
