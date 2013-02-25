package server;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class HtmlGenerator {

    public String generate404() {
        StringBuilder notFoundString = new StringBuilder();
        notFoundString.append("<!DOCTYPE html><html><head><title>404</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>\n");
        notFoundString.append("<body style=\"background-color: whitesmoke;\"><div style=\"width: 1400px; margin: auto;\"><img src=\"public/images/error404.png\" style=\"width: 1400px; height: 800px;\"/></div>\n");
        notFoundString.append("</body></html>\n");
        return notFoundString.toString();
    }

    public String generateHello() {
        return Hello.helloHTML;
    }

    public String generateTime(String time) {
        StringBuilder timeString = new StringBuilder();
        timeString.append(generateHtmlHead());
        timeString.append("<h1 style=\"text-align: center; margin-top: 100px; font-size: 50px; font-family: monaco;\">");
        timeString.append(time);
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
        indexString.append("<h2 style=\"margin-left: 210px; font-weight: 500; margin-top: 100px;\">Current Location: "+rootDirectory + currDirectory+"</h2>");
        indexString.append(generateFileListing(currDirectory, rootDirectory));
        indexString.append(generateHtmlEnd());
        return indexString.toString();
    }

    public String generateFileListing(String currDirectory, String rootDirectory) {
        StringBuilder indexString = new StringBuilder();
        String tempHrefBase = currDirectory.replace(rootDirectory, "");
        File nextDir = new File(rootDirectory + currDirectory);
        File[] files = nextDir.listFiles();

        for(File dirFile: files) {
            indexString.append("<div style=\"margin-top: 12px;\">");
            if (dirFile.isDirectory()) {
                indexString.append("<a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #766F67;\" href=\""+ tempHrefBase + "/" + dirFile.getName() + "\">" + dirFile.getName() + "/</a>");
            } else {
                indexString.append("<a style=\"margin-top: 12px; margin-left: 220px; text-decoration: none; color: #A89E92;\" href=\""+ tempHrefBase + "/" + dirFile.getName() + "\">" + dirFile.getName() + "</a>");
            }
            indexString.append("</div>");
        }
        return indexString.toString();
    }

    public String generateFormParams(Map<String, String> params) {
        StringBuilder formParams = new StringBuilder();
        formParams.append(generateHtmlHead());
        formParams.append("<div style=\"width: 400px; margin: 60px auto 0 auto;\">");
        formParams.append("<h1>Parameters Received:</h1>");

        Iterator it = params.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            formParams.append("<h3>" + pairs.getKey() + " = " + pairs.getValue() + "</h3>");
        }

        formParams.append("</div>");
        formParams.append(generateHtmlEnd());
        return formParams.toString();
    }

    public String generateHtmlHead() {
        return "<!DOCTYPE html><head><link href=\"/public/images/favicon.ico\" rel=\"shortcut icon\" type=\"image/vnd.microsoft.icon\"></head><html><body style=\"background-color: #ECDFCE; color: #766F67; font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;\">";
    }

    public String generateHtmlEnd() {
        return "</body></html>";
    }
}
