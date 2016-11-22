package _development;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.Context;
import _framework.WireMockVanilla;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import testcase.TestCaseRunnable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class SendingTest
{

	private Context context;
	private WireMockVanilla mock;
	@Rule public WireMockRule rule;
	private TestCaseRunnable runner;

	@Before
	public void before() throws Exception
	{
		context = new Context();
		mock = new WireMockVanilla();
		rule = mock.getRule();
		rule.givenThat(mock.whenAnyRequestReceivedThenReturn200());
		runner = context.getTestCaseRunnerVanilla();
	}

	@Test
	public void actuallySendData() throws Exception{
		// arrange
		URL url = mock.getURL();
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection connection = (HttpURLConnection) urlConnection;
		connection.setConnectTimeout(0);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.getOutputStream().write("SendingTest.actuallySendData".getBytes());
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
		boolean isRunning1_false = runner.isRunning();
		runner.startRun();
		Thread.sleep(1000);
		boolean isRunning2_true = runner.isRunning();
		runner.stopRun();
		boolean isRunning3_false = runner.isRunning();

		// assert
		assertFalse(isRunning1_false);
		assertTrue(isRunning2_true);
		assertFalse(isRunning3_false);
	}
}