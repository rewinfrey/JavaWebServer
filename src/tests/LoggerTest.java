package tests;

import junit.framework.TestCase;
import server.Logger;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoggerTest extends TestCase {
    String requestLine = "GET / HTTP/1.1";
    String dateLine    = "10:21:30 30/01/2013";

    Logger logger = new Logger();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public void testRequest() {
        System.setOut(new PrintStream(outContent));
        logger.request(requestLine, dateLine);
        assertEquals("\nGET / HTTP/1.1\n10:21:30 30/01/2013\n", outContent.toString());
        System.setOut(null);
    }

    public void testWriteFileToLog() throws IOException {
        //System.setOut(new PrintStream(outContent));
        //logger.writeFileToLog("Users/rickwinfrey/IdeaProjects/RickHttpServer/files/form.html");
        //assertEquals(requestFile, outContent.toString());
        //System.setOut(null);
    }
}
