package development;

import all.DataForTestCase;
import all.Result;
import all.UseCase;


/**
 * Created by HiekmaHe on 10.11.2016.
 */
public class ATestCase
{
	private Result result;

	public ATestCase(UseCase useCase, DataForTestCase data)
	{
		setResultsFromData(data);
	}

	public void start()
	{
	}

	public Result getResult()
	{
		return result;
	}

	public void setResultsFromData(DataForTestCase resultsFromData)
	{
		result = new Result();
		result.expectedDurationInMinutes = resultsFromData.expectedDurationInMinutes;
	}
}
