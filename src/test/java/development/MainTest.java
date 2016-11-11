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
	private Result result;

	@Before
	public void before() {
		useCase = new UseCaseA();
		data = new DataForTestCase();
		testCase = new ATestCase(useCase, data);
	}

	@Test
	public void easySetup() {
		// arrange

		// act
		testCase.start();
		testCase.stop();
		Result result = testCase.getResult();

		// assert
		assertNotNull(result);
	}

	@Test
	public void setExpectedDurationAndCheckItsValueInResult() {
		// arrange
		long expectedDurationInNanos = data.expectedDurationInNanos;

		// act
		testCase.start();
		Result result = testCase.getResult();
		System.out.println(result == null);
		long actualExpectedDurationInNanos = result.expectedDurationInNanos;

		// assert
		assertEquals(expectedDurationInNanos, actualExpectedDurationInNanos);
	}

	@Test
	public void actualDurationInNanosIfUseCaseWasNotRunReturnsNull() {
		// arrange

		// act
		Result result = testCase.getResult();

		// assert
		assertNull(result);
	}

	@Test
	public void runUseCaseFor2Seconds_assertLowerBound() {
		// arrange
		data.expectedDurationInNanos = Converter.secondsToNanos(2);
		long acceptedDeviation = Converter.secondsToNanos(1);

		// act
		testCase.run(); // makes no multi-threading
		System.out.println(testCase);
		Result result = testCase.getResult();
		long expectedLeastDurationOfRun = Converter.secondsToNanos(2);
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Took not long enough.\n" + testCase,  actualDurationOfRun > expectedLeastDurationOfRun);
	}

	@Test
	public void runUseCaseFor2Seconds_assertUpperBound() {
		// arrange
		data.expectedDurationInNanos = Converter.secondsToNanos(2);
		long acceptedDeviation = Converter.secondsToNanos(1);

		// act
		testCase.run();
		Result result = testCase.getResult();
		long expectedLeastDurationOfRun = Converter.secondsToNanos(2);
		long expectedLongestDurationOfRun = expectedLeastDurationOfRun + acceptedDeviation;
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Took too long.\n" + testCase, expectedLongestDurationOfRun > actualDurationOfRun);
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertLowerBound() {
		// arrange
		long expectedDurationInNanos = Converter.secondsToNanos(5);
		data.expectedDurationInNanos = expectedDurationInNanos;
		long killAfterNanos = Converter.secondsToNanos(2);
		long deviation = Converter.millisToNanos(500);
		long expectedLeastDurationOfRun = killAfterNanos;
		long expectedLongestDurationOfRun = killAfterNanos + deviation;

		// act
		testCase.start();
		Eris.threadSleep(Converter.nanosToMillis(killAfterNanos));
		testCase.stop();
		result = testCase.getResult();
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Took not long enough.\n" + testCase,  actualDurationOfRun > expectedLeastDurationOfRun);
		assertTrue("Took too long.\n" + testCase,  expectedLongestDurationOfRun > actualDurationOfRun);
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_assertUpperBound() {
		// arrange
		long expectedDurationInNanos = Converter.secondsToNanos(5);
		data.expectedDurationInNanos = expectedDurationInNanos;
		long acceptedDeviation = 500 * 1000 * 1000;
		long killAfterNanos = Converter.secondsToNanos(2);
		long expectedLongestDurationOfRun = Converter.secondsToNanos(2) + acceptedDeviation;

		// act
		Thread aThread = new Thread(testCase);
		aThread.start();
		Eris.threadSleep(Converter.nanosToMillis(killAfterNanos));
		Eris.interrupt(aThread);
		testCase.stop();
		result = testCase.getResult();
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Took too long.", expectedLongestDurationOfRun > actualDurationOfRun);
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCallAsThread() {
		// arrange
		long expectedLeastDurationOfRun = Converter.secondsToNanos(4);
		data.expectedDurationInNanos = expectedLeastDurationOfRun;
		long acceptedDeviation = Converter.secondsToNanos(1);
		long expectedLongestDurationOfRun = data.expectedDurationInNanos + acceptedDeviation;
		long killAfterNanos = Converter.secondsToNanos(2);

		// act
		Thread aThread = new Thread(testCase);
		aThread.start();
		Eris.threadSleep(Converter.nanosToMillis(killAfterNanos));
		Eris.interrupt(aThread);
		testCase.stop();
		result = testCase.getResult();
		assertNotNull(testCase.getResult());
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Run takes not enough time.\n" + testCase, actualDurationOfRun > killAfterNanos - Converter.millisToNanos(100));
		assertTrue("Run killed too early.\n" + testCase, expectedLeastDurationOfRun > actualDurationOfRun);
		assertTrue("Run takes too long.\n" + testCase,   expectedLongestDurationOfRun > actualDurationOfRun);
	}

	@Test
	public void runUseCaseFor2SecondsBecauseOfExternalStopCall_TestCaseRunnableNow() {
		// arrange
		long expectedDurationInNanos = Converter.secondsToNanos(5);
		data.expectedDurationInNanos = expectedDurationInNanos;
		long acceptedDeviation = 500 * 1000 * 1000;
		long killAfterNanos = Converter.secondsToNanos(2);
		long expectedLongestDurationOfRun = Converter.secondsToNanos(2) + acceptedDeviation;

		// act
		testCase.start();
		Eris.threadSleep(Converter.nanosToMillis(killAfterNanos));
		testCase.stop();
		result = testCase.getResult();
		long actualDurationOfRun = result.actualDurationInNanos;

		// assert
		assertTrue("Took too long.", expectedLongestDurationOfRun > actualDurationOfRun);
	}


	@Test
	public void timeStampOwnUnit() {

	}
}