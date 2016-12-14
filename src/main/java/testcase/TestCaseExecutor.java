package testCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by HiekmaHe on 15.11.2016.
 *
 * SRP: Threading of TestCase using Executor
 */
public class TestCaseExecutor implements TestCaseRunnable
{
	private final TestCase testCase;
	private final ExecutorService executor;


	public TestCaseExecutor(TestCase testCase)
	{
		this.testCase = testCase;
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public boolean isRunning()
	{
		return testCase.isRunning();
	}

	@Override
	public void startRun()
	{
		testCase.timestampStartNow();
		executor.execute(testCase);
	}

	@Override
	public void stopRun()
	{
		testCase.isRunning(false);
		executor.shutdown();
		testCase.timestampStartEnd();
	}

	@Override
	public DataForTestCase getResult()
	{
		return testCase.getResult();
	}

	@Override
	public String toString() {
		return  "TestCaseExecutor:\n"
				+ "isShutdown: " + executor.isShutdown() + "\n"
				+ "isTerminated: " + executor.isTerminated() + "\n";
	}
}