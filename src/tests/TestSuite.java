package tests;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({HtmlGeneratorTest.class, RouterTest.class, HttpRequestParserTest.class, MimeTypeMatcherTest.class, HttpServerTest.class, HelloTest.class, HttpResponseTest.class, ServerMainTest.class})
public class TestSuite {
}