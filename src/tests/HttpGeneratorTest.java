package tests;

import org.junit.Test;
import server.Hello;
import server.HttpGenerator;

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
}

