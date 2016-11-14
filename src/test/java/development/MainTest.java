package development;

import org.junit.Before;
import org.junit.Test;

import all.*;
import testcase.DataForTestCase;
import testcase.TestCase;
import testcase.TestCaseRunner;
import time.Time;

import static junit.framework.TestCase.*;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class MainTest
{

	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseRunner testCaseRunner;

	@Before
	public void before() {
		UseCase useCase = new UseCase();
		data = new DataForTestCase();
		data.expectedDuration = Time.seconds(10);
		testCase = new TestCase(useCase, data);
		testCaseRunner = new TestCaseRunner(testCase);
	}

	@Test
	public void runOutOfTheBox() throws Exception
	{
		// arrange
		Time stopAfter = Time.millis(1500);

		// act
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();

		Time actual = testCaseRunner.duration();
		Time delta = stopAfter.difference(actual);

		// assert
		assertTrue("Runs too long. " + stopAfter + " ! > " + actual + " delta: " + delta, stopAfter.greaterThan(actual));
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
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();
		Time duration = testCaseRunner.getDuration();

		// assert
		assertTrue("Took not long enough." + testCase,  duration.greaterThan(stopAfter));
	}

	@Test
	public void runUseCaseFor2Seconds_assertUpperBound() throws Exception
	{
		// arrange
		data.expectedDuration = Time.seconds(2);

		// act
		testCaseRunner.start();
		Time expectedLongestDurationOfRun = Time.seconds(2).add(Time.millis(500));

		// assert
		assertTrue("Took too long." + expectedLongestDurationOfRun + "!>" + testCase.getDuration(), expectedLongestDurationOfRun.greaterThan(testCase.getDuration()));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertLowerBound() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);

		// act
		Time timestampStart = Time.now();
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();
		Time duration = Time.now().difference(timestampStart);

		// assert
		assertTrue("Took not long enough." + duration + " " + stopAfter, duration.greaterThan(stopAfter));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertUpperBound() throws Exception {
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = stopAfter.add(Time.millis(500));

		// act
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();

		// assert
		assertTrue("Took too long.", maxDuration.greaterThan(testCaseRunner.duration()));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCallAsThread() throws Exception
	{
		// arrange
		Time stopAfter = Time.seconds(2);
		Time maxDuration = data.expectedDuration.add(Time.millis(500));

		// act
		Time timestampStart = Time.now();
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();
		Time duration = Time.now().difference(timestampStart);

		// assert
		assertTrue("Run to quick.." + testCase, duration.greaterThan(stopAfter));
		assertTrue("Run takes too long." + testCase,   maxDuration.greaterThan(duration));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_TestCaseRunnableNow() throws Exception
	{
		// arrange
		data.expectedDuration = Time.seconds(5);
		Time acceptedDeviation = Time.millis(500);
		Time stopAfter = Time.seconds(2);
		Time expectedLongestDurationOfRun = Time.seconds(2).add(acceptedDeviation);

		// act
		testCaseRunner.start();
		stopAfter.sleep();
		testCaseRunner.stop();

		// assert
		assertTrue("Took too long.", expectedLongestDurationOfRun.greaterThan(testCaseRunner.duration()));
	}

	@Test
	public void handleSleepInterrupted() {

	}
}