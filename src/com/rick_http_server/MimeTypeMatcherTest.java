package com.rick_http_server;

import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MimeTypeMatcherTest extends TestCase {
    MimeTypeMatcher mimeTypeMatcher = new MimeTypeMatcher();
     public void testGetMimeType() {
        assertEquals("application/pdf", mimeTypeMatcher.getMimeType("example.pdf"));
        assertEquals("image/png", mimeTypeMatcher.getMimeType("example.png"));
        assertEquals("image/jpg", mimeTypeMatcher.getMimeType("example.jpg"));
        assertEquals("image/gif", mimeTypeMatcher.getMimeType("example.gif"));
        assertEquals("text/html;charset=utf-8", mimeTypeMatcher.getMimeType("example.html"));
        assertEquals("text/plain", mimeTypeMatcher.getMimeType("example.txt"));
    }
}
