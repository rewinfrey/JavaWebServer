package server;

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
        int index = requestFile.lastIndexOf('.');
        String parsedFileEnding = requestFile.substring(index);
        String mimeType;
        if (parsedFileEnding.equals(".html")) {
            mimeType = "text/html; charset=UTF-8";
        } else if (parsedFileEnding.equals(".txt")) {
            mimeType = "text/plain";
        } else if (parsedFileEnding.equals(".gif")) {
            mimeType = "image/gif";
        } else if (parsedFileEnding.equals(".png")) {
            mimeType = "image/png";
        } else if (parsedFileEnding.equals(".pdf")) {
            mimeType = "application/pdf";
        } else if (parsedFileEnding.equals(".jpg")) {
            mimeType = "image/jpg";
        } else if (parsedFileEnding.equals(".ico")) {
            mimeType = "image/png";
        } else {
            mimeType = "text/html;charset=UTF-8";
        }
        return mimeType;
    }
}

