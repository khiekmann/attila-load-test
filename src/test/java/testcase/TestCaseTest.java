package testcase;

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
import time.Time;
import useCase.UseCaseDummy;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 13.11.2016.
 */
public class TestCaseTest
{

	UseCaseDummy useCase;
	DataForTestCase data;
	TestCase testCase;

	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	private String host = "http://localhost:";
	private static URL url;
	private String urlPath = "/cai/rtm/v1/d";

	@Before
	public void before() throws IOException
	{
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
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		Sendable sender = new Send_Response_Disconnect(AttilaConnectionCreate.createInstance(url));
		useCase = new UseCaseDummy(messages, sender);
		data = new DataForTestCase();
		testCase = new TestCase(useCase, data);
	}

//	@Test
	public void run() throws Exception
	{
		// arrange

		// act
		testCase.run();
		Thread.sleep(1000);
		testCase.stop();

		// assert
	}

//	@Test
	public void stop() throws Exception
	{
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();
		testCase.run();
		boolean isRunning2 = testCase.isRunning();
		testCase.stop();
		boolean isRunning3 = testCase.isRunning();

		// assert
		assertEquals(false, isRunning1);
		assertEquals(true, isRunning2);
		assertEquals(false, isRunning3);
	}

	@Test
	public void testToString() throws Exception
	{
		// arrange

		// act
		String toString = testCase.toString();
		String expected = "ATestCase:\n"
				+ "DataForTestCase\n"
				+ "expectedDuration: 0\n"
				+ "duration: 0\n"
				+ "timestampEnd: 0\n"
				+ "timestampStart: 0\n";

		// assert
		assertEquals(expected, toString);
	}

	@Test
	public void getDuration() throws Exception
	{
		// arrange

		// act
		Time duration = testCase.getDuration();

		// assert
		assertEquals(Time.ZERO, duration);
	}

	@Test
	public void isRunning_directAccess() throws Exception {
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();
		testCase.isRunning(true);
		boolean isRunning2 = testCase.isRunning();
		testCase.isRunning(false);
		boolean isRunning3 = testCase.isRunning();

		// assert
		assertFalse(isRunning1);
		assertTrue(isRunning2);
		assertFalse(isRunning3);
	}

	@Test
	public void isRunning_accessByRunning() throws Exception {
		// arrange

		// act
		boolean isRunning1 = testCase.isRunning();

		Thread thread = new Thread(
			new Runnable() {
				@Override
				public void run()
				{
					testCase.run();
				}
			}
		);
		thread.start();
		Thread.sleep(500);
		boolean isRunning2 = testCase.isRunning();
		testCase.isRunning(false);
		boolean isRunning3 = testCase.isRunning();
		testCase.isRunning(false);

		// assert
		assertFalse(isRunning1);
		assertTrue(isRunning2);
		assertFalse(isRunning3);
	}
}