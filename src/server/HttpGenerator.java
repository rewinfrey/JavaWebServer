package server;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/3/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpGenerator {

    private DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss MM/dd/yyyy" );

    public HttpGenerator() {
    }

    public String generate404() {
        StringBuilder notFoundString = new StringBuilder();
        notFoundString.append("<!DOCTYPE html><html><head><title>404</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>\n");
        notFoundString.append("<body style=\"background-color: #171717; color: whitesmoke;\"><h1 style=\"margin: 100px auto 20px auto; width: 1080px; font-size: 60px;\">探しているページが見つかられません！</h1>\n");
        notFoundString.append("<h1 style=\"margin: 0px auto 50px auto; width: 1080px; font-size: 30px; text-align: center;\">Error 404. The page you are looking for cannot be found</h1>\n");
        notFoundString.append("<div style=\"text-align: center; font-size: 30px;\">\n");
        notFoundString.append("<p>うつくしいものを</p><p>うつくしいとおもえる</p><p>こころが</p>\n");
        notFoundString.append("<p>うつくしいものだ</p><p style=\"margin-left: 500px;\">相田みつを</p></div></body></html>\n");
        return notFoundString.toString();
    }

    public String generateHello() {
        return Hello.helloHTML;
    }

    public String generateTime() {
        StringBuilder timeString = new StringBuilder();
        timeString.append(generateHtmlHead());
        timeString.append("<h1 style=\"text-align: center; margin-top: 100px; font-size: 50px; font-family: monaco;\">");
        timeString.append(dateFormat.format(new Date())+"\n");
        timeString.append("</h1>");
        timeString.append(generateHtmlEnd());
        return timeString.toString();
    }

    public String generateForm() {
        StringBuilder formString = new StringBuilder();
        formString.append(generateHtmlHead());
        formString.append("<div style=\"margin: auto; width: 206px;\"><h1 style=\"text-align: center;\">Form!</h1>\n");
        formString.append("<form action=\"/form\" method=\"post\"><ul style=\"list-style: none;\">\n");
        formString.append("<li style=\"margin-top: 20px;\"><label for=\"one\"></label><input type=\"text\" name=\"one\" value=\"one\"></li>\n");
        formString.append("<li style=\"margin-top: 20px;\"><label for=\"two\"></label><input type=\"text\" name=\"two\" value=\"two\"></li>\n");
        formString.append("<li style=\"margin-top: 20px;\"><label for=\"three\"></label><input type=\"text\" name=\"three\" value=\"three\"></li>\n");
        formString.append("<input style=\"width: 80px; margin: 20px 22px;\" type=\"submit\" value=\"submit\" /></ul></form></div>");
        formString.append(generateHtmlEnd());
        return formString.toString();
    }

    public String generateIndex(String currDirectory, String rootDirectory) {
        StringBuilder indexString = new StringBuilder();
        indexString.append(generateHtmlHead());
        indexString.append("<h1>Index</h1>");
        indexString.append(generateFileListing(currDirectory, rootDirectory));
        indexString.append(generateHtmlEnd());
        return indexString.toString();
    }

    public String generateFileListing(String currDirectory, String rootDirectory) {
        StringBuilder indexString = new StringBuilder();
        String tempHrefBase = currDirectory.replace(rootDirectory, "");
        File nextDir = new File(currDirectory + "/");
        File[] files = nextDir.listFiles();

        for(File dirFile: files) {
            if (dirFile.isDirectory()) {
                indexString.append("<a href=\""+ tempHrefBase + "/" + dirFile.getName() + "\">" + dirFile.getName() + "/</a><br />\n");
            } else {
                indexString.append("<a href=\""+ tempHrefBase + "/" + dirFile.getName() + "\">" + dirFile.getName() + "</a><br />\n");
            }
        }
        return indexString.toString();
    }

    private String generateHtmlHead() {
        return "<!DOCTYPE html><head></head><html><body style=\"background-color: #171717; color: whitesmoke;\">";
    }

    private String generateHtmlEnd() {
        return "</body></html>";
    }
}
