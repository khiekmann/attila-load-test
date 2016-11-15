package testcase;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class TestCaseRunner implements TestCaseRunnable
{
	private TestCase testCase;
	private Thread thread;

	public TestCaseRunner(TestCase testCaseToRun)
	{
		testCase = testCaseToRun;
		thread = new Thread(testCase);
	}

	@Override
	public void startRun()
	{
//		timestampStart = Time.now(); 2016-11-14 delete in 2 weeks
		thread.start();
	}

	@Override
	public void stopRun()
	{
		testCase.isRunning(false);
//		timestampEnd = Time.now(); 2016-11-14 delete in 2 weeks
	}

	@Override
	public String toString() {
		String message = "TestCase:\n";
		message += "state: " + thread.getState() + "\n";
   	return message;
	}

	@Override
	public boolean isRunning()
	{
		return thread.isAlive();
	}
}