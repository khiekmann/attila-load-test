package useCase;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Execute one iteration of the use Case
 */
public class UseCaseDummy implements UseCaseable
{
	int i = 0;

	public void doOneIteration()
	{
		System.out.println("Iteration " + i++);
		try
		{
			Thread.sleep(200);
		}
		catch (InterruptedException e)
		{
			StackTraceElement element = e.getStackTrace()[0];
			System.err.println(element.getClassName() + "." + element.getMethodName() + ": " + e.getMessage());
		}
	}

	@Override
	public int getResponseCode()
	{
		return 0;
	}
}