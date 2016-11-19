package development;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import useCase.UseCaseDummy;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class SendingTest
{
	private static int port = 8080;
	@ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule public WireMockClassRule instanceRule = wireMockRule;
	private String urlPath = "/cairtmv1d";
	private URL url;
	private String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private TestCaseExecutor testCaseRunnable;

	@Before
	public void before() throws Exception
	{
		String host = "http://localhost:";
		url = new URL(host + port + urlPath);
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		String urlPath = "/somewhere";
		URL url = new URL("http://localhost:" + port + urlPath);
		Sendable sender = new Send_Response_Disconnect(AttilaConnectionCreate.createInstance(url));
		UseCaseDummy useCase = new UseCaseDummy(messages, sender);
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		testCaseRunnable = new TestCaseExecutor(testCase);
	}

	@Test
	public void actuallySendData() throws Exception{
		// arrange
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection connection = (HttpURLConnection) urlConnection;
		connection.setConnectTimeout(0);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.getOutputStream().write("Hallo".getBytes());
		connection.getOutputStream().flush();
		connection.getResponseCode(); //TODO async ??
		connection.getResponseMessage();
		connection.disconnect();

		// act

		// assert
	}

	@Test
	public void testSending() throws Exception
	{
		// arrange

		// act
		boolean isRunning1_false = testCaseRunnable.isRunning();
		testCaseRunnable.startRun();
		Thread.sleep(2000);
		boolean isRunning2_true = testCaseRunnable.isRunning();
		testCaseRunnable.stopRun();
		boolean isRunning3_false = testCaseRunnable.isRunning();

		// assert
		assertFalse(isRunning1_false);
		assertTrue(isRunning2_true);
		assertFalse(isRunning3_false);
	}
}