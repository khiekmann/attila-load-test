package all;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class ATestCase implements Runnable
{
	private final DataForTestCase data;
	private Result result;
	private boolean isRunning;
	private Thread thread;

	public ATestCase(UseCase useCase, DataForTestCase inData)
	{

		data = inData;
		thread = new Thread(this);
	}

	public void start()
	{
		thread.start();
	}

	public void run()
	{
		isRunning = true;
		data.timestampStartInNanos = System.nanoTime();
		while(isRunning) {
			threadSleepForExpectedDurationInNanos();
		}
		stop();
	}

	private void threadSleepForExpectedDurationInNanos()
	{
		try
		{
			long start = System.nanoTime();
			long end = System.nanoTime();
			long delta = end - start;
			while (delta <= data.expectedDurationInNanos) {
				Thread.sleep(100);
				delta = System.nanoTime() - start;
			}
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
		finally {
			doKeepRunning();
		}
	}

	private void doKeepRunning()
	{
		long timestampEnd = System.nanoTime();
		data.actualDurationInNanos = timestampEnd - data.timestampStartInNanos;
		if (data.actualDurationInNanos > data.expectedDurationInNanos)
		{
			isRunning = false;
		}
	}

	public Result getResult()
	{
		return result;
	}

	public void calcResult()
	{
		result = new Result();
		result.expectedDurationInNanos = data.expectedDurationInNanos;
		result.actualDurationInNanos = data.actualDurationInNanos;
	}

	public void stop()
	{
		isRunning = false;
		long timestampEnd = System.nanoTime();
		data.actualDurationInNanos = timestampEnd - data.timestampStartInNanos;
		data.actualTimestampEndInMillis = System.nanoTime();
		calcResult();
	}

	@Override
	public String toString() {
		String message = "ATestCase:\n";
		message += "expected duration: " + data.expectedDurationInNanos + "\n";
		message += "actual duration: " + data.actualDurationInNanos + "\n";
		message += "delta: " + (data.expectedDurationInNanos - data.actualDurationInNanos);
		return message;
	}
}