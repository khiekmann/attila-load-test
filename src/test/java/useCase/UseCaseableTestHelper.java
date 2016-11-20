package useCase;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class UseCaseableTestHelper
{
	private static Collection<String> holdingExecutedMethodMessages = new Vector<>();
	private static String doOneIteration = "doOneIteration()";

	public static UseCaseable createUseCaseable() {
		return new UseCaseable()
		{
			@Override
			public void doOneIteration() throws IOException
			{
				holdingExecutedMethodMessages.add(doOneIteration);
			}

			@Override
			public int getResponseCode()
			{
				return 0;
			}
		};
	}

	public static int size()
	{
		return holdingExecutedMethodMessages.size();
	}

	public static boolean wasDoOneIterationExecuted()
	{
		return holdingExecutedMethodMessages.contains(doOneIteration);
	}
}