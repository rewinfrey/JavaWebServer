package tests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/5/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({RequestHandlerTest.class, HttpRequestRouterTest.class, HttpGeneratorTest.class, HttpRequestParserTest.class, LoggerTest.class, MimeTypeMatcherTest.class, PostWranglerTest.class, ResourceFetcherTest.class, SocketWriterTest.class, HttpServerTest.class, WranglerTest.class, HelloTest.class, GetWranglerTest.class })
public class TestSuite {
}
