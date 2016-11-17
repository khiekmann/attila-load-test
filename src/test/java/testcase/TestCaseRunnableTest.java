package testcase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import all.UseCaseDummy;
import time.Time;

import static junit.framework.TestCase.*;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
@RunWith(Parameterized.class)
public class TestCaseRunnableTest
{

	@Parameterized.Parameters( name = "{index}: {0}, {1}" )
	public static Collection<Object[]> commons() throws IOException
	{
		UseCaseDummy useCase = new UseCaseDummy();
		DataForTestCase data = new DataForTestCase();
		TestCase testCase = new TestCase(useCase, data);
		return Arrays.asList(new Object[][] {
				{new TestCaseExecutor(testCase), TestCaseExecutorTest.expectedToString},
				{new TestCaseRunner(testCase), TestCaseRunnerTest.expectedToString}
		});
	}

	@Parameterized.Parameter
	public TestCaseRunnable oneCommon;

	@Parameterized.Parameter(value = 1)
	public String oneCommonToString;

	@Test
	public void testCreation()
	{
		// arrange
		UseCaseDummy useCase = new UseCaseDummy();
		DataForTestCase data = new DataForTestCase();
		data.expectedDuration = Time.seconds(60);
		TestCase testCase = new TestCase(useCase, data);

		// act
		oneCommon = new TestCaseExecutor(testCase);

		// assert
		assertNotNull(oneCommon);
	}

	@Ignore
	@Test
	public void runTest() throws Exception
	{
		// arrange

		// act
		boolean isAlive1 = oneCommon.isRunning();
		oneCommon.startRun();
		Thread.sleep(100);
		boolean isAlive2 = oneCommon.isRunning();
		Thread.sleep(100);
		boolean isAlive3 = oneCommon.isRunning();
		Thread.sleep(100);
		boolean isAlive4 = oneCommon.isRunning();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertTrue(isAlive3);
		assertTrue(isAlive4);
	}

	@Ignore
	@Test
	public void runAndStopTestCase() throws Exception {
		// arrange

		// act
		boolean isAlive1 = oneCommon.isRunning();
		oneCommon.startRun();
		Thread.sleep(100);
		boolean isAlive2 = oneCommon.isRunning();
		Thread.sleep(100);
		oneCommon.stopRun();
		Thread.sleep(100);
		boolean isAlive3 = oneCommon.isRunning();

		// assert
		assertFalse(isAlive1);
		assertTrue(isAlive2);
		assertFalse(isAlive3);
	}

	@Ignore
	@Test
	public void measureHowLongToStop() throws Exception {
		// arrange

		// act
		oneCommon.startRun();
		Thread.sleep(200);
		oneCommon.stopRun();
		Time timestampStart = Time.now();
		Time timestampEnd = Time.ZERO;
		while(oneCommon.isRunning()) {
			timestampEnd = Time.now();
		}
		Time delta = timestampEnd.difference(timestampStart);

		// assert
		System.out.println("Stopping took " + delta.toMillis() + " ms.");
	}

	@Ignore
	@Test
	public void testToString() throws Exception
	{
		// arrange

		// act
		Time.seconds(1).sleep();

		// assert
		assertEquals(oneCommonToString, oneCommon.toString());
	}
}