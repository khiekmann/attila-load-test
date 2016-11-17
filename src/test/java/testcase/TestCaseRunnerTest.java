package testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import all.UseCaseDummy;
import time.Time;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
public class TestCaseRunnerTest
{

	public static String expectedToString = "TestCase:\n"
			+ "state: TERMINATED\n";


	private UseCaseDummy useCase;
	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseRunner testCaseRunner;

	@Before
	public void before() {
		// arrange
		useCase = new UseCaseDummy();
		data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		testCase = new TestCase(useCase, data);

		// act
		testCaseRunner = new TestCaseRunner(testCase);

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
		testCaseRunner = null;

		// assert
	}


	@Test
	public void testCreation()
	{
		// arrange
		UseCaseDummy useCase = new UseCaseDummy();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		TestCase testCase = new TestCase(useCase, data);

		// act
		TestCaseRunner testCaseRunner = new TestCaseRunner(testCase);

		// assert
		assertNotNull(testCaseRunner);
	}

	@Test
	public void runTest() throws Exception
	{
		// arrange

		// act
		boolean isAlive1 = testCaseRunner.isRunning();
		testCaseRunner.startRun();
		boolean isAlive2 = testCaseRunner.isRunning();
		Thread.sleep(300);
		boolean isAlive3 = testCaseRunner.isRunning();
		Thread.sleep(300);
		boolean isAlive4 = testCaseRunner.isRunning();

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
		boolean isAlive1 = testCaseRunner.isRunning();
		testCaseRunner.startRun();
		boolean isAlive2 = testCaseRunner.isRunning();
		Thread.sleep(300);
		testCaseRunner.stopRun();
		Thread.sleep(1000);
		boolean isAlive3 = testCaseRunner.isRunning();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertFalse(isAlive3);
	}

	@Test
	public void measureHowLongToStop() throws Exception {
		// arrange

		// act
		testCaseRunner.startRun();
		Thread.sleep(200);
		testCaseRunner.stopRun();
		Time timestampStart = Time.now();
		Time timestampEnd = Time.ZERO;
		while(testCaseRunner.isRunning()) {
			timestampEnd = Time.now();
		}
		Time delta = timestampEnd.difference(timestampStart);

		// assert
		System.out.println("Stopping took " + delta.toMillis() + " ms.");
	}

	@Test
	public void testToString() throws Exception
	{
		// arrange
		Time aShort = Time.millis(300);
		testCaseRunner = new TestCaseRunner(testCase);
		String newState = "TestCase:\n"
			+ "state: NEW\n";
		String timedWaitingState = "TestCase:\n"
				+ "state: TIMED_WAITING\n";
		String terminatedState = "TestCase:\n"
				+ "state: TERMINATED\n";

		// act
		String toString1 = testCaseRunner.toString();
		testCaseRunner.startRun();
		aShort.sleep();
		String toString2 = testCaseRunner.toString();
		aShort.sleep();
		testCaseRunner.stopRun();
		aShort.sleep();
		String toString3 = testCaseRunner.toString();

		// assert
		assertEquals(newState, toString1);
		assertEquals(timedWaitingState, toString2);
		assertEquals(terminatedState, toString3);
	}

	@Test
	public void testExecutor() {
		System.out.println("Implement java.util.concurrent.Executor");

	}
}