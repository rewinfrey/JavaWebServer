package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/29/13
 * Time: 2:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class MimeTypeMatcher {

    public MimeTypeMatcher() {
    }

    public String getMimeType(String requestFile) {
        String parsedFileExtension = parseFileExtension(requestFile);
        String mimeType;
        List<String> plain = asList(".txt", ".rb", ".java", ".php", ".coffee");
        if (parsedFileExtension.contains(".html")) {
            mimeType = "text/html; charset=UTF-8";
        } else if (plain.contains(parsedFileExtension)) {
            mimeType = "text/plain";
        } else if (parsedFileExtension.equals(".gif")) {
            mimeType = "image/gif";
        } else if (parsedFileExtension.equals(".png")) {
            mimeType = "image/png";
        } else if (parsedFileExtension.equals(".pdf")) {
            mimeType = "application/pdf";
        } else if (parsedFileExtension.equals(".jpg")) {
            mimeType = "image/jpg";
        } else if (parsedFileExtension.equals(".ico")) {
            mimeType = "image/png";
        } else {
            mimeType = "text/html; charset=UTF-8";
        }
        return mimeType;
    }

    public String parseFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index).toLowerCase();
    }
}

