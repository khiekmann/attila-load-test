package testcase;

import useCase.UseCaseable;
import time.Time;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Run a UseCase
 *
 */
public class TestCase implements Runnable
{
	private final UseCaseable useCase;
	private final DataForTestCase data;

	private DataForTestCase result;
	private boolean isRunning;

	public TestCase(UseCaseable useThisUseCase, DataForTestCase useThisData)
	{
		useCase = useThisUseCase;
		data = useThisData;
		result = new DataForTestCase();
	}

	public void run()
	{
		isRunning(true);
		while(isRunning())
		{
			useCase.doOneIteration();
		}
		stop();
	}

	public void stop()
	{
		isRunning(false);
		setTimestampEndIfUnset();
		calcResult();
	}

	public boolean isRunning()
	{
		return isRunning;
	}

	public void isRunning(boolean aBoolean)
	{
		isRunning = aBoolean;
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

	private void setTimestampEndIfUnset()
	{
		if (isTimestampEndUnset()) {
			data.timestampEnd = Time.now();
		}
	}

	@Override
	public String toString() {
		String message = "ATestCase:\n";
		message += data.toString();
   	return message;
	}

	private boolean isTimestampEndUnset()
	{
		return data.timestampEnd.equals(Time.seconds(0));
	}

	public Time getDuration()
	{
		return data.actualDuration;
	}

	public DataForTestCase getResult()
	{
		return result;
	}
}