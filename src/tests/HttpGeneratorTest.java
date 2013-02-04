package tests;

import org.junit.Test;
import server.Hello;
import server.HttpGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static server.Hello.*;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/4/13
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpGeneratorTest {
    HttpGenerator httpGenerator = new HttpGenerator();

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );

    @Test
    public void generate404() {
        String template404 = "<!DOCTYPE html><html><head><title>404</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>\n" +
                "<body style=\"background-color: #171717; color: whitesmoke;\"><h1 style=\"margin: 100px auto 20px auto; width: 1080px; font-size: 60px;\">探しているページが見つけられません！</h1>\n" +
                "<h1 style=\"margin: 0px auto 50px auto; width: 1080px; font-size: 30px; text-align: center;\">Error 404. The page you are looking for cannot be found</h1>\n" +
                "<div style=\"text-align: center; font-size: 30px;\">\n" +
                "<p>うつくしいものを</p><p>うつくしいとおもえる</p><p>こころが</p>\n" +
                "<p>うつくしいものだ</p><p style=\"margin-left: 500px;\">相田みつを</p></div></body></html>\n";
        assertEquals(template404, httpGenerator.generate404());
    }

    @Test
    public void generateHello() {
        String templateHello = helloHTML;
        assertEquals(templateHello, httpGenerator.generateHello());
    }

    @Test
    public void generateTime() {
        String time = dateFormat.format(new Date());
        StringBuilder templateTime = new StringBuilder();
        templateTime.append("<!DOCTYPE html><head></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\"><h1 style=\"text-align: center; margin-top: 100px; font-size: 50px; font-family: monaco;\">");
        templateTime.append(time);
        templateTime.append("</h1></body></html>");
        assertEquals(templateTime.toString(), httpGenerator.generateTime(time));
    }

    @Test
    public void generateform() {
        String templateForm = "<!DOCTYPE html><head></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\"><div style=\"margin: auto; width: 206px;\"><h1 style=\"text-align: center;\">Form!</h1>\n" +
                "<form action=\"/form\" method=\"post\"><ul style=\"list-style: none;\">\n" +
                "<li style=\"margin-top: 20px;\"><label for=\"one\"></label><input type=\"text\" name=\"one\" value=\"one\"></li>\n" +
                "<li style=\"margin-top: 20px;\"><label for=\"two\"></label><input type=\"text\" name=\"two\" value=\"two\"></li>\n" +
                "<li style=\"margin-top: 20px;\"><label for=\"three\"></label><input type=\"text\" name=\"three\" value=\"three\"></li>\n" +
                "<input style=\"width: 80px; margin: 20px 22px;\" type=\"submit\" value=\"submit\" /></ul></form></div></body></html>";
        assertEquals(templateForm, httpGenerator.generateForm());
    }

    @Test
    public void generateIndex() {
        String templateIndex = "<!DOCTYPE html><head></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\"><h2 style=\"margin-left: 210px; font-weight: 500; margin-top: 100px;\">Current Location: /Users/rickwinfrey/serverTestDir</h2><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test1.html\">test1.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test2.html\">test2.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test3.html\">test3.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test4.png\">test4.png</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test5.jpg\">test5.jpg</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test6.gif\">test6.gif</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #766F67;\" href=\"/testDir2\">testDir2/</a></div></body></html>";
        assertEquals(templateIndex, httpGenerator.generateIndex("/Users/rickwinfrey/serverTestDir", "/Users/rickwinfrey/serverTestDir"));
    }

    @Test
    public void generateFileListing() {
        String templateFileList = "<div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test1.html\">test1.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test2.html\">test2.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test3.html\">test3.html</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test4.png\">test4.png</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test5.jpg\">test5.jpg</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\"/test6.gif\">test6.gif</a></div><div style=\"margin-top: 12px;\"><a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #766F67;\" href=\"/testDir2\">testDir2/</a></div>";
        assertEquals(templateFileList, httpGenerator.generateFileListing("/Users/rickwinfrey/serverTestDir", "/Users/rickwinfrey/serverTestDir"));
    }

    @Test
    public void generateFormParams() {
        String templateFormParams = "<!DOCTYPE html><head></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\"><div style=\"width: 400px; margin: 60px auto 0 auto;\"><h1>Parameters Received:</h1><h3>one = one</h3><h3>two = two</h3><h3>three = three</h3></div></body></html>";
        assertEquals(templateFormParams, httpGenerator.generateFormParams("one=one&two=two&three=three"));
    }

    @Test
    public void generateHtmlHead() {
        String templateHtmlHead = "<!DOCTYPE html><head></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">";
        assertEquals(templateHtmlHead, httpGenerator.generateHtmlHead());
    }

    @Test
    public void generateHtmlEnd() {
        String templateHtmlEnd = "</body></html>";
        assertEquals(templateHtmlEnd, httpGenerator.generateHtmlEnd());
    }
}

