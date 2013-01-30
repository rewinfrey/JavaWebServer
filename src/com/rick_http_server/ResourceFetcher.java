package com.rick_http_server;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/28/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceFetcher {
    public ResourceFetcher() {
    }

    public boolean processRequest(String resource) throws Exception {
        String requestedFile = expandedPath(resource);
        return exists(requestedFile);
    }

    public boolean exists(String resource) {
        return new File( resource ).exists();
    }

    public String expandedPath(String resource) {
        return resource.replace("/", "files/");
    }
}
