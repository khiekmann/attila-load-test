package testcase;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestWireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import time.Time;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class TestCaseRunnable_StartStop_Test
{

	@Rule
	public WireMockClassRule instanceRule = TestWireMockClassRule.createInstance();
	private TestCaseRunnable testCaseRunnable;
	private DataForTestCase data;

	@Before
	public void before() throws IOException
	{
		instanceRule.stubFor(TestWireMockClassRule.stubFor());
		testCaseRunnable = TestCaseTestHelper.createTestCaseRunner();
	}

	@Test
	public void runOutOfTheBox() throws Exception
	{
		// arrange
		Time aShort = Time.millis(300);

		// act
		Time timestartStamp = Time.now();
		testCaseRunnable.startRun();
		aShort.sleep();
		testCaseRunnable.stopRun();
		Time duration = Time.elapseSince(timestartStamp);

		// assert
		assertTrue("Runs too long.", duration.greaterThan(aShort));
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
		assertTrue("Run takes too long." + testCaseRunnable,   maxDuration.greaterThan(duration));
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