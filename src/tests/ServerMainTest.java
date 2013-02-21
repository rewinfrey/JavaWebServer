package tests;

import org.junit.Test;
import server.ServerMain;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class ServerMainTest {

    private void callMain(String port) throws IOException
    {
        String[] args = { "-p", port, "-d", System.getProperty("user.dir") + "/testfiles"};
        ServerMain.main(args);
    }

    private void stopMain() throws IOException
    {
        ServerMain.stop();
    }

    private Process curlTest(String port, String route) throws IOException, InterruptedException
    {
        String cmd = "curl -I http://localhost:"+port+route;
        Runtime r = Runtime.getRuntime();
        Process p = r.exec(cmd);
        return p;
    }

    private Process curlPostTest(String port, String route, String postString) throws IOException, InterruptedException
    {
        String cmd = "curl -d \"" + postString + "\" http://localhost:"+port+route;
        Runtime r = Runtime.getRuntime();
        Process p = r.exec(cmd);
        return p;
    }

    private BufferedReader processToBufferedReader(Process p)
    {
        return new BufferedReader( new InputStreamReader( p.getInputStream() ) );
    }

    private String getResponseLine(BufferedReader input) throws IOException
    {
        return input.readLine();
    }

    @Test
    public void main() throws IOException, InterruptedException
    {
        String port = "9099";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void timeRoute() throws IOException, InterruptedException
    {
        String port = "9199";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/time")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void helloRoute() throws IOException, InterruptedException
    {
        String port = "9299";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/hello")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void formRoute() throws IOException, InterruptedException
    {
        String port = "9350";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/form")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void fileRoute() throws IOException, InterruptedException
    {
        String port = "9499";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/test.txt")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void dirRoute() throws IOException, InterruptedException
    {
        String port = "9599";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/test")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void nestedFileInDir() throws IOException, InterruptedException
    {
        String port = "9699";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/test/hk.jpg")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void notFoundRoute() throws IOException, InterruptedException
    {
        String port = "9799";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/test/this_is_not_a_file")));
        assertTrue(response.contains("404 Not Found"));
        stopMain();
    }

    @Test
    public void queryStringFormRoute() throws IOException, InterruptedException
    {
        String port = "9899";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlTest(port, "/form?name=rick&age=30")));
        assertTrue(response.contains("200 OK"));
        stopMain();
    }

    @Test
    public void postFormRoute() throws IOException, InterruptedException
    {
        String port = "9991";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlPostTest(port, "/form", "name=rick&age=30")));
        assertTrue(response.contains("name = rick"));
        assertTrue(response.contains("age = 30"));
        stopMain();
    }

    @Test
    public void postFormAndQueryString() throws IOException, InterruptedException
    {
        String port = "8997";
        callMain(port);
        String response = getResponseLine(processToBufferedReader(curlPostTest(port, "/form?location=chicago&language=java", "name=rick&age=30")));
        assertTrue(response.contains("name = rick"));
        assertTrue(response.contains("age = 30"));
        assertTrue(response.contains("location = chicago"));
        assertTrue(response.contains("language = java"));
        stopMain();
    }

    @Test
    public void callBadRequest() throws IOException
    {
        callMain("8999");
        Socket newSocket = new Socket(InetAddress.getLocalHost(), 8999);
        BufferedReader inputStream = new BufferedReader( new InputStreamReader( newSocket.getInputStream()));
        System.out.println("In bad request: " );
        assertEquals("HTTP/1.1 400 Bad Request", inputStream.readLine());
        stopMain();
    }


    @Test
    public void callMainWithHelp() throws IOException
    {
        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(outArray);
        System.setOut(outStream);
        String[] args = {"-h"};
        ServerMain.main(args);
        compareStdOutToExpectedHelpMsg(outArray);
    }

    @Test
    public void callMainWithHelpTwo() throws IOException
    {
        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(outArray);
        System.setOut(outStream);
        String[] args = {"--help"};
        ServerMain.main(args);
        compareStdOutToExpectedHelpMsg(outArray);
    }

    private void compareStdOutToExpectedHelpMsg(ByteArrayOutputStream outArray) {
        assertTrue(outArray.toString().contains("Usage:"));
        assertTrue(outArray.toString().contains("java -jar rickhttp.jar -p <port> -d <directory/to/serve>"));
        assertTrue(outArray.toString().contains("Example:"));
        assertTrue(outArray.toString().contains("java -jar rickhttp.jar -p 3000 -d ~/Documents"));
    }
}
