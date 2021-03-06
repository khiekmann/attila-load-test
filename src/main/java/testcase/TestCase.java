package testCase;

import java.io.IOException;

import time.Time;
import useCase.UseCaseable;


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

	public TestCase(UseCaseable useCase, DataForTestCase data)
	{
		this.useCase = useCase;
		this.data = data;
		result = new DataForTestCase();
	}

	public void run()
	{
		isRunning(true);
		while(keepRunning())
		{
			doOneIterationOrHandleException();
		}
		stop();
	}

	private boolean keepRunning()
	{
		if (isRunning) {
			Time actualDuration = data.timestampStart.difference(Time.now());
			isRunning = actualDuration.lessThan(data.expectedDuration);
		}
		return isRunning;
	}

	private void doOneIterationOrHandleException()
	{
		try
		{
			useCase.executeOnce();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		isRunning(false);
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

	@Override
	public String toString() {
		String message = data.toString();
   	return message;
	}

	public Time getDuration()
	{
		return data.actualDuration;
	}

	public DataForTestCase getResult()
	{
		calcResult();
		return result;
	}

	public void timestampStartNow()
	{
		data.timestampStart = Time.now();
	}

	public void timestampStartEnd()
	{
		data.timestampEnd = Time.now();
	}
}