package development;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.PostConnection;
import connection.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseMySpecial;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 16.11.2016.
 */
public class MainTest
{
	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	private String urlPath = "/somewhere";

	@Before
	public void before() {
		stubFor(any(urlEqualTo(urlPath)).withRequestBody(equalTo("foo"))
				.willReturn(
				aResponse()
						.withStatus(200)
				)
		);
	}

	@Test
	public void testPostRequestMethod() throws Exception
	{
		// arrange
		String onlyPostUrlPath = "/onlyPost";
		stubFor(post(urlEqualTo(onlyPostUrlPath)).withRequestBody(equalTo("foo"))
				.willReturn(
						aResponse()
								.withStatus(200)
				)
		);
		URL url = new URL("http://localhost:" + 8080 + onlyPostUrlPath);
		PostConnection postConnection = new PostConnection(url);

		// act
		postConnection.send("foo");

		// assert
		assertEquals(200, postConnection.getResponseCode());
	}

	@Test
	public void runFor5Seconds() throws IOException, InterruptedException
	{
		// arrange
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		URL url = new URL("http://localhost" + urlPath);
		Sendable connection = new PostConnection(url);
		UseCaseable useCase = new UseCaseMySpecial(messages, connection);
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		TestCaseRunnable runner = new TestCaseExecutor(testCase);
		boolean unexpectedExceptionThrown = false;

		// act
		try {
			runner.startRun();
			Time.seconds(2).sleep();
			runner.stopRun();
		} catch (Exception e) {
			unexpectedExceptionThrown = true;
		}

		// assert
		assertFalse(unexpectedExceptionThrown);
	}
}