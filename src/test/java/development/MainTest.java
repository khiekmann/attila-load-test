package development;

import org.junit.Before;
import org.junit.Test;

import all.*;

import static junit.framework.TestCase.*;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 */
public class MainTest
{

	private UseCase useCase;
	private DataForTestCase data;
	private ATestCase testCase;

	@Before
	public void before() {
		useCase = new UseCase();
		data = new DataForTestCase();
		testCase = new ATestCase(useCase, data);
	}

	@Test
	public void runInitialHenceExpectNull() {
		// arrange
		Time runFor = Time.millis(1500);

		// act
		testCase.start();
		Eris.threadSleep(runFor);
		testCase.stop();
		long expected = runFor.toNanos();
		long actual = testCase.getResult().actualDuration.toNanos();
		long delta = expected - actual;

		// assert
		assertTrue("Runs too long. " + expected + " ! > " + actual + " delta: " + delta, expected > actual);
	}

	@Test
	public void actualDurationInNanosIfUseCaseWasNotRunReturnsNull() {
		// arrange

		// act
		DataForTestCase result = testCase.getResult();

		// assert
		assertNull(result);
	}

	@Test
	public void runUseCaseFor2Seconds_assertLowerBound() {
		// arrange
		data.expectedDuration = Time.seconds(2);

		// act
		testCase.run();
		DataForTestCase result = testCase.getResult();
		Time expectedLeastDurationOfRun = Time.seconds(2);
		Time actualDurationOfRun = result.actualDuration;

		// assert
		assertTrue("Took not long enough." + testCase,  actualDurationOfRun.greaterThan(expectedLeastDurationOfRun));
	}

	@Test
	public void runUseCaseFor2Seconds_assertUpperBound() {
		// arrange
		data.expectedDuration = Time.seconds(2);

		// act
		testCase.run();
		Time expectedLongestDurationOfRun = Time.seconds(2).add(Time.millis(500));

		// assert
		assertTrue("Took too long." + expectedLongestDurationOfRun + "!>" + testCase.getResult().actualDuration, expectedLongestDurationOfRun.greaterThan(testCase.getResult().actualDuration));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertLowerBound() {
		// arrange
		data.expectedDuration = Time.seconds(5);
		Time killAfter = Time.seconds(2);
		Time expectedLeastDurationOfRun = killAfter.substract(Time.millis(200));

		// act
		testCase.start();
		Eris.threadSleep(killAfter);
		testCase.stop();
		DataForTestCase result = testCase.getResult();

		Time actualDurationOfRun = result.actualDuration;
		Time expectedLongestDurationOfRun = killAfter.add(Time.millis(500));

		// assert
		assertTrue("Took not long enough.", actualDurationOfRun.greaterThan(expectedLeastDurationOfRun));
		assertTrue("Took too long.",  expectedLongestDurationOfRun.greaterThan(actualDurationOfRun));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertUpperBound() {
		// arrange
		Time expectedDurationInNanos = Time.seconds(5);
		data.expectedDuration = expectedDurationInNanos;
		Time acceptedDeviation = Time.millis(500);
		Time killAfterNanos = Time.seconds(2);
		Time expectedLongestDurationOfRun = Time.seconds(2).add(acceptedDeviation);

		// act
		Thread aThread = new Thread(testCase);
		aThread.start();
		Eris.threadSleep(killAfterNanos);
		Eris.interrupt(aThread);
		testCase.stop();
		DataForTestCase result = testCase.getResult();
		Time actualDurationOfRun = result.actualDuration;

		// assert
		assertTrue("Took too long.", expectedLongestDurationOfRun.greaterThan(actualDurationOfRun));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCallAsThread() {
		// arrange
		Time expectedLeastDurationOfRun = Time.seconds(4);
		data.expectedDuration = expectedLeastDurationOfRun;
		Time acceptedDeviation = Time.seconds(1);
		Time expectedLongestDurationOfRun = data.expectedDuration.add(acceptedDeviation);
		Time killAfterNanos = Time.seconds(2);

		// act
		Thread aThread = new Thread(testCase);
		aThread.start();
		Eris.threadSleep(killAfterNanos);
		Eris.interrupt(aThread);
		testCase.stop();
		DataForTestCase result = testCase.getResult();
		assertNotNull(testCase.getResult());
		Time actualDurationOfRun = result.actualDuration;

		// assert
		assertTrue("Run takes not enough time." + testCase, actualDurationOfRun.greaterThan(killAfterNanos.substract(Time.millis(100))));
		assertTrue("Run killed too early." + testCase, expectedLeastDurationOfRun.greaterThan(actualDurationOfRun));
		assertTrue("Run takes too long." + testCase,   expectedLongestDurationOfRun.greaterThan(actualDurationOfRun));
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_TestCaseRunnableNow() {
		// arrange
		Time expectedDurationInNanos = Time.seconds(5);
		data.expectedDuration = expectedDurationInNanos;
		Time acceptedDeviation = Time.millis(500);
		Time killAfterNanos = Time.seconds(2);
		Time expectedLongestDurationOfRun = Time.seconds(2).add(acceptedDeviation);

		// act
		testCase.start();
		Eris.threadSleep(killAfterNanos);
		testCase.stop();
		DataForTestCase result = testCase.getResult();
		Time actualDurationOfRun = result.actualDuration;

		// assert
		assertTrue("Took too long.", expectedLongestDurationOfRun.greaterThan(actualDurationOfRun));
	}

	@Test
	public void handleSleepInterrupted() {

	}
}