package testcase;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import attila.AttilaSendingCreate;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import send.Sending;
import send.Sendable;
import time.Time;
import useCase.TestUseCase;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class TestCaseRunnable_StartStop_Test
{

	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseRunnable testCaseRunnable;

	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	private String host = "http://localhost:";
	private static URL url;
	private String urlPath = "/cai/rtm/v1/d";

	@Before
	public void before() throws Exception
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
		Sendable sender = new Sending(AttilaSendingCreate.createInstance(url));
		httpUrl.setDoOutput(true);
		TestUseCase useCase = new TestUseCase(sender);
		data = new DataForTestCase();
		data.expectedDuration = Time.seconds(10);
		testCase = new TestCase(useCase, data);
		testCaseRunnable = new TestCaseExecutor(testCase);
	}

	@Test
	public void runOutOfTheBox() throws Exception
	{
		// arrange
		Time stopAfter = Time.millis(1500);
		Time aShort = Time.millis(300);

		// act
		Time timestartStamp = Time.now();
		testCaseRunnable.startRun();
		aShort.sleep();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestartStamp);

		// assert
		System.out.println(duration + " > " + stopAfter);
		assertTrue("Runs too long.", duration.greaterThan(stopAfter));
	}

	@Test
	public void runOutOfTheBox_HandleResult_NotRun() {
		// arrange

		// act
		DataForTestCase result = testCase.getResult();
		String resultToString = "DataForTestCase\n"
				+ "expectedDuration: 0\n"
				+ "duration: 0\n"
				+ "timestampEnd: 0\n"
				+ "timestampStart: 0\n";

		// assert
		assertEquals(resultToString, result.toString());
	}

	@Test
	public void runUseCaseFor2Seconds_assertLowerBound() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Took not long enough." + duration + " - " + stopAfter,  duration.greaterThan(stopAfter));
	}

	@Test
	public void runUseCaseFor2Seconds_assertUpperBound() throws Exception
	{
		// arrange
		data.expectedDuration = Time.seconds(2);

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		Time duration = Time.elapseSince(timestampStart);
		Time maxDuration = Time.seconds(2).add(Time.millis(500));

		// assert
		assertTrue("Took too long.", maxDuration.greaterThan(duration));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertLowerBound() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Took not long enough." + duration + " " + stopAfter, duration.greaterThan(stopAfter));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertUpperBound() throws Exception {
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = stopAfter.add(Time.millis(500));

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Took too long.", maxDuration.greaterThan(duration));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCallAsThread() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = data.expectedDuration.add(Time.millis(500));

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Run to quick.." + duration + " " + stopAfter, duration.greaterThan(stopAfter));
		assertTrue("Run takes too long." + testCase,   maxDuration.greaterThan(duration));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_TestCaseRunnableNow() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = Time.seconds(2).add( Time.millis(500));

		// act
		Time timestampStart = Time.now();
		testCaseRunnable.startRun();
		stopAfter.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Took too long.", maxDuration.greaterThan(duration));
	}
}