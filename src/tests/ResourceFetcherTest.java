package tests;

import junit.framework.TestCase;
import server.ResourceFetcher;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceFetcherTest extends TestCase {
    String testDirectory = "/Users/rickwinfrey/IdeaProjects/RickHttpServer/files/";
    ResourceFetcher resourceFetcher = new ResourceFetcher(testDirectory);

    public void testProcessRequest() throws Exception {
        assertTrue(resourceFetcher.processRequest("/hello.html"));
        assertTrue(resourceFetcher.processRequest("/index.html"));
    }
}

