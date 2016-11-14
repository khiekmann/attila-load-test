package testcase;

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

	public TestCaseRunner(TestCase testCaseToRun)
	{
		testCase = testCaseToRun;
		thread = new Thread(testCase);
	}

	public void start() throws InterruptedException
	{
//		timestampStart = Time.now(); 2016-11-14 delete in 2 weeks
		thread.start();
		Thread.sleep(2);
	}

	public void stop()
	{
		testCase.isRunning(false);
//		timestampEnd = Time.now(); 2016-11-14 delete in 2 weeks
	}

	@Override
	public String toString() {
		String message = "TestCase:\n";
		message += "state: " + thread.getState() + "\n";
		message += testCase.toString();
   	return message;
	}

	public boolean isAlive()
	{
		return thread.isAlive();
	}
}