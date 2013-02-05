package tests;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/5/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({RequestHandlerTest.class, HttpRequestRouterTest.class, GetWranglerTest.class, HttpGeneratorTest.class, HttpRequestParserTest.class, LoggerTest.class, MimeTypeMatcherTest.class, PostWranglerTest.class, ResourceFetcherTest.class, SocketWriterTest.class, HttpServerTest.class, WranglerTest.class, HelloTest.class })
public class TestSuite {
}
