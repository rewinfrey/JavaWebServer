package tests;

import org.junit.Test;
import server.HttpResponse;

import static org.junit.Assert.assertNotNull;

public class HttpResponseTest {
    @Test
    public void instantiateTest()
    {
        HttpResponse httpResponse = new HttpResponse();
        assertNotNull(httpResponse);
    }
}
