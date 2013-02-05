package tests;

import junit.extensions.TestSetup;
import junit.framework.TestCase;
import org.junit.Test;
import server.Logger;

import java.io.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoggerTest {
    String requestLine = "GET / HTTP/1.1";
    String dateLine    = "10:21:30 30/01/2013";

    Logger logger = new Logger();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void request() {
        System.setOut(new PrintStream(outContent));
        logger.request(requestLine, dateLine);
        assertEquals("\nGET / HTTP/1.1\n10:21:30 30/01/2013\n", outContent.toString());
    }
}
