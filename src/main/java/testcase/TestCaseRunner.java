package testcase;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class TestCaseRunner implements TestCaseRunnable
{

	private final TestCase testCase;
	private final Thread thread;

	public TestCaseRunner(TestCase testCaseToRun)
	{
		testCase = testCaseToRun;
		thread = new Thread(testCaseToRun);
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

	@Override
	public void startRun()
	{
		testCase.isRunning(true);
		thread.start();
	}

	@Override
	public void stopRun()
	{
		testCase.isRunning(false);
		thread.interrupt();
	}
}