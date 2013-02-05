package server;

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
    public void request(String requestSummary, String date) {
        System.out.println("\n" + requestSummary);
        System.out.println(date);
    }
}
