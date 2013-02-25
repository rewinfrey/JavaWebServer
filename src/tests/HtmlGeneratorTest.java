package tests;

import org.junit.Before;
import org.junit.Test;
import server.HtmlGenerator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static server.Hello.*;

public class HtmlGeneratorTest{
    HtmlGenerator htmlGenerator = new HtmlGenerator();

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    String testDir = System.getProperty("user.dir").toString() + "/testfiles";

    @Before
    public void request() {
       System.setOut(new PrintStream(outContent));
    }

    @Test
    public void generate404() {
        String template404 = "<!DOCTYPE html><html><head><title>404</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head>\n" +
                             "<body style=\"background-color: whitesmoke;\"><div style=\"width: 1400px; margin: auto;\"><img src=\"public/images/error404.png\" style=\"width: 1400px; height: 800px;\"/></div>\n" +
                             "</body></html>\n";
        assertEquals(template404, htmlGenerator.generate404());
    }

    @Test
    public void generateHello() {
        String templateHello = helloHTML;
        assertEquals(templateHello, htmlGenerator.generateHello());
    }

    @Test
    public void generateTime() {
        String time = dateFormat.format(new Date());
        StringBuilder templateTime = new StringBuilder();
        templateTime.append("<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">" +
                            "<h1 style=\"text-align: center; margin-top: 100px; font-size: 50px; font-family: monaco;\">");
        templateTime.append(time);
        templateTime.append("</h1></body></html>");
        assertEquals(templateTime.toString(), htmlGenerator.generateTime(time));
    }

    @Test
    public void generateform() {
        String templateForm = "<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">" +
                              "<div style=\"margin: auto; width: 206px;\"><h1 style=\"text-align: center;\">Form!</h1>\n" +
                              "<form action=\"/form\" method=\"post\"><ul style=\"list-style: none;\">\n" +
                              "<li style=\"margin-top: 20px;\"><label for=\"one\"></label><input type=\"text\" name=\"one\" value=\"one\"></li>\n" +
                              "<li style=\"margin-top: 20px;\"><label for=\"two\"></label><input type=\"text\" name=\"two\" value=\"two\"></li>\n" +
                              "<li style=\"margin-top: 20px;\"><label for=\"three\"></label><input type=\"text\" name=\"three\" value=\"three\"></li>\n" +
                             "<input style=\"width: 80px; margin: 20px 22px;\" type=\"submit\" value=\"submit\" /></ul></form></div></body></html>";
        assertEquals(templateForm, htmlGenerator.generateForm());
    }

    @Test
    public void generateIndex() {
        String templateIndex = "<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">" +
                               "<h2 style=\"margin-left: 210px; font-weight: 500; margin-top: 100px;\">Current Location: "+testDir+"</h2><div style=\"margin-top: 12px;\">" +
                               "<a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #766F67;\" href=\"/test\">test/</a></div><div style=\"margin-top: 12px;\">" +
                               "<a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test.txt\">test.txt</a></div></body></html>";
        assertEquals(templateIndex, htmlGenerator.generateIndex("", testDir));
    }

    @Test
    public void generateFileListing() {
        String templateFileList = "<div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #766F67;\" href=\"/test\">test/</a>" +
                                  "</div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test.txt\">test.txt</a></div>";
        System.out.println(testDir);

        assertEquals(templateFileList, htmlGenerator.generateFileListing("", testDir));
    }

    @Test
    public void generateFormParams() {
        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("two", "two");
        testMap.put("one", "one");
        testMap.put("three", "three");
        String templateFormParams = "<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\"><div style=\"width: 400px; margin: 60px auto 0 auto;\"><h1>Parameters Received:</h1><h3>two = two</h3><h3>one = one</h3><h3>three = three</h3></div></body></html>";
        assertEquals(templateFormParams, htmlGenerator.generateFormParams(testMap));
    }

    @Test
    public void generateHtmlHead() {
        String templateHtmlHead = "<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">";
        assertEquals(templateHtmlHead, htmlGenerator.generateHtmlHead());
    }

    @Test
    public void generateHtmlEnd() {
        String templateHtmlEnd = "</body></html>";
        assertEquals(templateHtmlEnd, htmlGenerator.generateHtmlEnd());
    }
}

