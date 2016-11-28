package testcase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import attila.AttilaSendingCreate;
import send.Sendable;
import send.Sending;
import time.Time;
import useCase.UseCaseExample;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
public class TestCaseExecutorTest
{
	public static String expectedToString = "TestCaseExecutor:\n"
			+ "isShutdown: true\n"
			+ "isTerminated: true\n";

	private UseCaseExample useCase;
	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseExecutor testCaseExecutor;

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
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		useCase = new UseCaseExample(sender);
		data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		testCase = new TestCase(useCase, data);

		// act
		testCaseExecutor = new TestCaseExecutor(testCase);

		// assert
		assertNotNull(testCase);

	}

	@After
	public void tearDown() throws Exception
	{
		// arrange

		// act
		useCase = null;
		data = null;
		testCase = null;
		testCaseExecutor = null;

		// assert
	}

	@Test
	public void runTest() throws Exception
	{
		// arrange

		// act
		boolean isAlive1 = testCaseExecutor.isRunning();
		testCaseExecutor.startRun();
		Thread.sleep(100);
		boolean isAlive2 = testCaseExecutor.isRunning();
		Thread.sleep(100);
		boolean isAlive3 = testCaseExecutor.isRunning();
		Thread.sleep(100);
		boolean isAlive4 = testCaseExecutor.isRunning();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertTrue(isAlive3);
		assertTrue(isAlive4);
	}

	@Test
	public void runAndStopTestCase() throws Exception {
		// arrange

		// act
		boolean isAlive1 = testCaseExecutor.isRunning();
		testCaseExecutor.startRun();
		Thread.sleep(100);
		boolean isAlive2 = testCaseExecutor.isRunning();
		Thread.sleep(100);
		testCaseExecutor.stopRun();
		Thread.sleep(100);
		boolean isAlive3 = testCaseExecutor.isRunning();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertFalse(isAlive3);
	}

	@Test
	public void measureHowLongToStop() throws Exception {
		// arrange
		Time aShort = Time.millis(200);
		Time maxDuration = Time.millis(100);

		// act
		testCaseExecutor.startRun();
		aShort.sleep();
		Time timestampStart = Time.now();
		testCaseExecutor.stopRun();
		Time timestampEnd = Time.now();
		while(testCaseExecutor.isRunning()) {
			timestampEnd = Time.now();
		}
		Time delta = timestampEnd.difference(timestampStart);

		// assert
		assertTrue(maxDuration.greaterThan(delta));
	}

	@Test
	public void testToString() throws Exception
	{
		// arrange
		Time aShort = Time.millis(300);
		String neitherShutdownNorTerminated = "TestCaseExecutor:\n"
				+ "isShutdown: false\n"
				+ "isTerminated: false\n";
		String  shutdownAndTerminated  = "TestCaseExecutor:\n"
				+ "isShutdown: true\n"
				+ "isTerminated: true\n";

		// act
		testCaseExecutor = new TestCaseExecutor(testCase);
		String toString1 = testCaseExecutor.toString();
		testCaseExecutor.startRun();
		aShort.sleep();
		String toString2 = testCaseExecutor.toString();
		aShort.sleep();
		testCaseExecutor.stopRun();
		aShort.sleep();
		String toString3 = testCaseExecutor.toString();

		// assert
		assertEquals(neitherShutdownNorTerminated, toString1);
		assertEquals(neitherShutdownNorTerminated, toString2);
		assertEquals(shutdownAndTerminated, toString3);
	}
}