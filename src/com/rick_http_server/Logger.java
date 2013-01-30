package com.rick_http_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 1/29/13
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Logger {
    public Logger() {
    }

    public void request(String request, String date) {
        System.out.println("\n" + request);
        System.out.println(date);
    }

    public void writeFileToLog(String fileName) throws FileNotFoundException {
        File resource      = new File(fileName);
        Scanner fileReader = new Scanner(resource);
        System.out.println("\nRequested Content:");
        while(fileReader.hasNext()) {
            String line = fileReader.nextLine();
            System.out.println(line);
        }
        fileReader.close();
    }
}
