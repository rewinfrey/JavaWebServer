package tests;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/1/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockGetSocket extends Socket {
    public MockGetSocket(InetAddress localHost, int i) {
        super();
    }

    public OutputStream getOutPutStream() {
        return new OutputStream() {
            @Override
            public void write(int b) {
            }
        };
    }

    public InputStream getInputStream() {
        String headers = "GET /hello HTTP/1.1\r\nHost: localhost: 3006\r\n\r\n";
        return new ByteArrayInputStream(headers.getBytes());
    }

    public void flush() {
    }

    public void close() {
    }
}