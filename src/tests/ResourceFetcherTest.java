package tests;

import org.junit.Test;
import server.ResourceFetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/30/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceFetcherTest {
    String testDirectory = "/Users/rickwinfrey/play/files";
    ResourceFetcher resourceFetcher = new ResourceFetcher(testDirectory);

    private void writeFile() throws IOException {
        FileWriter fileWriter = new FileWriter( new File( testDirectory + "/test.txt") );
        fileWriter.write("This is a test file");
        fileWriter.flush();
        fileWriter.close();
    }

    @Test
    public void resourceFetcher() {
        ResourceFetcher testResourceFetcher = new ResourceFetcher(testDirectory);
        assertEquals(testDirectory, testResourceFetcher.directory);
    }

    @Test
    public void testProcessRequest() throws Exception {
        writeFile();
        assertTrue(resourceFetcher.processRequest("/test.txt"));
    }

    @Test
    public void expandedPath() {
        String temp = testDirectory + "/test.txt";
        assertEquals(temp, resourceFetcher.expandedPath("/test.txt"));
    }

    @Test
    public void exists() throws IOException {
        writeFile();
        String filePath = resourceFetcher.expandedPath("/test.txt");
        assertTrue(resourceFetcher.exists(filePath));
    }
}

