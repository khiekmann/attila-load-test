package development;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import all.Connectionable;
import all.PostConnection;
import all.UseCaseMySpecial;
import all.UseCaseable;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
import time.Time;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertFalse;


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
		stubFor(any(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
	}

	@Test
	public void runFor5Seconds() throws MalformedURLException, InterruptedException
	{
		// arrange
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		URL url = new URL("http://localhost" + urlPath);
		Connectionable connection = new PostConnection(url);
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