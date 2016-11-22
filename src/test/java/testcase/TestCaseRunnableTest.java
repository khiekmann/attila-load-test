package testcase;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.Context;
import _framework.WireMockVanilla;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import time.Time;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class TestCaseRunnableTest
{

	private Context context;
	private WireMockVanilla mock;
	@Rule
	public WireMockRule rule;
	private TestCaseRunnable runner;
	private DataForTestCase data;

	@Before
	public void before() throws IOException
	{
		context = new Context();
		mock = new WireMockVanilla();
		rule = mock.getRule();
		rule.givenThat(mock.whenAnyRequestReceivedThenReturn200());
		data = context.getData();
		runner = context.getTestCaseRunnerVanilla();
	}

	@Test
	public void runOutOfTheBox() throws Exception
	{
		// arrange
		Time aShort = Time.millis(300);

		// act
		Time timestartStamp = Time.now();
		runner.startRun();
		aShort.sleep();
		runner.stopRun();
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
		runner.startRun();
		stopAfter.sleep();
		runner.stopRun();
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
		runner.startRun();
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
		runner.startRun();
		stopAfter.sleep();
		runner.stopRun();
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
		runner.startRun();
		stopAfter.sleep();
		runner.stopRun();
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
		runner.startRun();
		stopAfter.sleep();
		runner.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Run to quick. " + duration + " " + stopAfter, duration.greaterThan(stopAfter));
		assertTrue("Run takes too long. " + maxDuration + " " + duration,   maxDuration.greaterThan(duration));
	}


	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_TestCaseRunnableNow() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = Time.seconds(2).add( Time.millis(500));

		// act
		Time timestampStart = Time.now();
		runner.startRun();
		stopAfter.sleep();
		runner.stopRun();
		Time duration = Time.elapseSince(timestampStart);

		// assert
		assertTrue("Took too long.", maxDuration.greaterThan(duration));
	}

	@Test
	public void testGetResult_AfterRunningFor2Seconds() throws Exception
	{
		// arrange
		Time range = Time.millis(300);
		Time aShort = Time.seconds(2);

		// act
		Time timestampStartSoon = Time.now();
		runner.startRun();
		aShort.sleep();
		runner.stopRun();
		Time timestampEndSoon = Time.now();
		DataForTestCase result = runner.getResult();

		// assert
		assertNotEquals(Time.ZERO, result.timestampStart);
		assertTrue(timestampStartSoon.inRange(result.timestampStart, range));
		assertNotEquals(Time.ZERO, result.timestampEnd);
		assertTrue(timestampEndSoon.inRange(result.timestampEnd, range));
		assertNotEquals(Time.ZERO, result.expectedDuration);
		assertNotEquals(Time.ZERO, result.actualDuration);
		assertTrue(result.expectedDuration.greaterThan(result.actualDuration));
		assertTrue(result.actualDuration.greaterThan(aShort));
	}
}