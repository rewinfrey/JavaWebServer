package com.rick_http_server;

import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceFetcherTest extends TestCase {
    ResourceFetcher resourceFetcher = new ResourceFetcher();

    public void testProcessRequest() throws Exception {
        assertTrue(resourceFetcher.processRequest("/hello.html"));
        assertTrue(resourceFetcher.processRequest("/index.html"));
    }
}

