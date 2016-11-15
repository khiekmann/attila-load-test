package testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import all.UseCase;
import time.Time;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
public class TestCaseExecutorTest
{
	public static String expectedToString = "TestCaseExecutor:\n"
			+ "isShutdown: false\n"
			+ "isTerminated: false\n";

	private UseCase useCase;
	private DataForTestCase data;
	private TestCase testCase;
	private TestCaseExecutor testCaseExecutor;

	@Before
	public void before() {
		// arrange
		useCase = new UseCase();
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
	public void testCreation()
	{
		// arrange
		UseCase useCase = new UseCase();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		TestCase testCase = new TestCase(useCase, data);

		// act
		TestCaseExecutor testCaseRunner = new TestCaseExecutor(testCase);

		// assert
		assertNotNull(testCaseRunner);
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

		// act
		testCaseExecutor.startRun();
		Thread.sleep(200);
		testCaseExecutor.stopRun();
		Time timestampStart = Time.now();
		Time timestampEnd = Time.ZERO;
		while(testCaseExecutor.isRunning()) {
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

		// act
		testCaseExecutor = new TestCaseExecutor(testCase);

		// assert
		assertEquals(expectedToString, testCaseExecutor.toString());
	}

	@Test
	public void testExecutor() {
		System.out.println("Implement java.util.concurrent.Executor");

	}
}