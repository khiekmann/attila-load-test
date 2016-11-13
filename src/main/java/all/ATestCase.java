package all;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class ATestCase implements Runnable
{
	private DataForTestCase data;
	private DataForTestCase result;
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

	/**
	 * no multi-threading
	 */
	public void run()
	{
		isRunning = true;
		data.timestampStart = Time.now();
		while(isRunning) {
			threadSleepForExpectedDurationInNanos();
		}
		stop();
	}

	private void threadSleepForExpectedDurationInNanos()
	{
		data.actualDuration = Time.now().substract(data.timestampStart);
		while (data.actualDuration.lessThan(data.expectedDuration)) {
			threadSleep(100);
			data.timestampEnd = Time.now();
			data.actualDuration = data.timestampEnd.substract(data.timestampStart);
		}
		isRunning = false;
	}

	private void threadSleep(long millis)
	{
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}

	private void calcResult()
	{
		data.actualDuration = data.timestampEnd.substract(data.timestampStart);
		result = new DataForTestCase();
		result.actualDuration = data.actualDuration;
		result.expectedDuration = data.expectedDuration;
		result.timestampStart = data.timestampStart;
		result.timestampEnd = data.timestampEnd;
	}

	public void stop()
	{
		isRunning = false;
		if (data.timestampEnd.equals(Time.seconds(0))) {
			data.timestampEnd = Time.now();
		}
		calcResult();
	}

	@Override
	public String toString() {
		String message = "ATestCase:\n";
		message += data.toString();
   	return message;
	}

	public DataForTestCase getResult()
	{
		return result;
	}
}