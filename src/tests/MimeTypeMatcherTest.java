package tests;

import org.junit.Before;
import org.junit.Test;
import server.MimeTypeMatcher;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

public class MimeTypeMatcherTest {
    String[] fileExtensions = { ".pdf", ".html", ".png", ".jpg", ".gif", ".ico", ".rb", ".java", ".coffee", ".php", "" };
    String[] mimeTypes      = { "application/pdf", "text/html; charset=UTF-8", "image/png", "image/jpg", "image/gif", "image/png", "text/plain", "text/plain", "text/plain", "text/plain", "text/plain" };
    MimeTypeMatcher mimeTypeMatcher = new MimeTypeMatcher();

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void request() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGetMimeType() {
        for ( int i = 0; i < fileExtensions.length; i++ ) {
            assertEquals(mimeTypes[i], mimeTypeMatcher.getMimeType("example"+fileExtensions[i]));
        }
    }

    @Test
    public void parseFileExtension() {
        for(String fileExt: fileExtensions)
            assertEquals(fileExt, mimeTypeMatcher.parseFileExtension("example"+fileExt));

    }

    @Test
    public void getMimeTypeDefault() {
        assertEquals("text/plain", mimeTypeMatcher.getMimeType("file.unknown_extension"));
    }
}
