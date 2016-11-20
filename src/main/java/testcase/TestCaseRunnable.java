package testcase;

/**
 * Created by HiekmaHe on 15.11.2016.
 *
 * SRP: Threading switch
 */
public interface TestCaseRunnable
{
	boolean isRunning();

	void startRun();

	void stopRun() throws InterruptedException;
}
