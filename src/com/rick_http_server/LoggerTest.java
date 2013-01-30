package com.rick_http_server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

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

    String requestFile = "Requested Content:\n" +
                         "<!DOCTYPE HTML>\n" +
                         "<html>\n" +
                         "<head>\n" +
                         "  <titLe>Rick Server Form</title>\n" +
                         "</head>\n" +
                         "<body>\n" +
                         "  <div style=\"margin: auto; width: 206px;\">\n" +
                         "    <h1 style=\"text-align: center;\">Form!</h1>\n" +
                         "    <form action=\"/form\" method=\"post\">\n" +
                         "      <ul style=\"list-style: none;\">\n" +
                         "        <li style=\"margin-top: 20px;\">\n" +
                         "          <label for=\"one\"></label><input type=\"text\" name=\"one\" value=\"one\">\n" +
                         "        </li>\n" +
                         "        <li style=\"margin-top: 20px;\">\n" +
                         "          <label for=\"two\"></label><input type=\"text\" name=\"two\" value=\"two\">\n" +
                         "        </li>\n" +
                         "        <li style=\"margin-top: 20px;\">\n" +
                         "          <label for=\"three\"></label><input type=\"text\" name=\"three\" value=\"three\">\n" +
                         "        </li>\n" +
                         "        <input style=\"width: 80px; margin: 20px 22px;\" type=\"submit\" />\n" +
                         "      </ul>\n" +
                         "    </form>\n" +
                         "  </div>\n" +
                         "</body>\n" +
                         "</html>\n";

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
