package development;

import all.Converter;
import all.DataForTestCase;
import all.Result;
import all.UseCase;


/**
 * Created by HiekmaHe on 10.11.2016.
 */
public class ATestCase
{
	private final DataForTestCase data;
	private Result result;

	public ATestCase(UseCase useCase, DataForTestCase inData)
	{
		data = inData;
	}

	public void start()
	{
		threadSleepForExpectedDurationInNanos();
		calcResult();
	}

	private void threadSleepForExpectedDurationInNanos()
	{
		long timestampStart = System.nanoTime();
		try
		{
			Thread.sleep(Converter.nanosToMillis(data.expectedDurationInNanos));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		long timestampEnd = System.nanoTime();
		data.actualDurationInNanos = timestampEnd - timestampStart;
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
}
