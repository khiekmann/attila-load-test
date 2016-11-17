package development;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.*;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.PostConnection;
import connection.Sendable;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;
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

	@Before
	public void before() throws MalformedURLException
	{
		String host = "http://localhost:";
		url = new URL(host + port + urlPath);
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
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
		UseCaseDummy useCase = new UseCaseDummy();
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		TestCaseRunnable testCaseRunnable = new TestCaseExecutor(testCase);

		// act
		boolean isRunning1_false = testCase.isRunning();
		testCaseRunnable.startRun();
		Thread.sleep(2000);
		boolean isRunning2_true = testCaseRunnable.isRunning();
		testCaseRunnable.stopRun();
		boolean isRunning3_false = testCase.isRunning();

		// assert
		assertFalse(isRunning1_false);
		assertTrue(isRunning2_true);
		assertFalse(isRunning3_false);
	}

	@Test
	public void testSendingSender() throws IOException
	{
		// arrange
		URL url = new URL("http://localhost:" + port + urlPath);
		Sendable sender = new PostConnection(url);

		// act
		sender.send("hallo welt");

		// assert
	}
}