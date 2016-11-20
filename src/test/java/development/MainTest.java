package development;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import attila.AttilaConnectionCreate;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.Send_Response_Disconnect;
import connection.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;
import useCase.UseCaseDummy;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.fail;
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
	private String onlyPostUrlPath = "/onlyPost";

	@Before
	public void before() {
		stubFor(post(urlEqualTo(onlyPostUrlPath)).withRequestBody(equalTo("foo"))
				.willReturn(
						aResponse()
								.withStatus(200)
				)
		);

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
		URL url = new URL("http://localhost:" + 8080 + onlyPostUrlPath);
		Sendable sender = new Send_Response_Disconnect(AttilaConnectionCreate.createInstance(url));

		// act
		sender.send("foo");

		// assert
		assertEquals(200, sender.getResponseCode());
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
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Send_Response_Disconnect(AttilaConnectionCreate.createInstance(url));
		UseCaseable useCase = new UseCaseDummy(messages, sender);
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		TestCaseRunnable runner = new TestCaseExecutor(testCase);
		boolean unexpectedExceptionThrown = false;

		// act
		runner.startRun();
		Time.seconds(2).sleep();
		runner.stopRun();

		// assert
	}

	@Test
	public void testTimeUnit() {
		fail("Use Javas Time Unit");
	}
}