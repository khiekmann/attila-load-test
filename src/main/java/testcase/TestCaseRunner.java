package testcase;

import time.Time;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class TestCaseRunner
{
	private TestCase testCase;
	private Thread thread;
	private Time timestampEnd;
	private Time timestampStart;

	public TestCaseRunner(TestCase testCaseToRun)
	{
		testCase = testCaseToRun;
		thread = new Thread(testCase);
	}

	public void start() throws InterruptedException
	{
		timestampStart = Time.now();
		thread.start();
		Thread.sleep(2);
	}

	public void stop()
	{
		testCase.isRunning(false);
		timestampEnd = Time.now();
	}

	@Override
	public String toString() {
		String message = "TestCase:\n";
		message += "state: " + thread.getState() + "\n";
		message += testCase.toString();
   	return message;
	}

	public Time duration()
	{
		return testCase.getDuration();
	}

	public boolean isAlive()
	{
		return thread.isAlive();
	}

	public Time getDuration()
	{
		return timestampEnd.difference(timestampStart);
	}
}