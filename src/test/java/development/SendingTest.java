package development;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Ignore;
import org.junit.Test;

import all.Connectionable;
import all.PostConnection;
import all.UseCaseDummy;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseExecutor;
import testcase.TestCaseRunnable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public class SendingTest
{

	@Ignore //Travis
	@Test
	public void actuallySendData() throws Exception{
		// arrange
		URL url = new URL("http://localhost");
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
	public void testSendingSender() throws MalformedURLException
	{
		// arrange
		URL url = new URL("http://localhost");
		Connectionable sender = new PostConnection(url);

		// act

		// assert
	}
}