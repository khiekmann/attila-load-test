package _development;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Outsource/dump all clutter here
 *
 * Godess of Discord.
 */
public class Eris
{
	public static void threadSleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getStackTrace()[1].getClassName() + ": " + e.getMessage());
		}
	}

	public static void interrupt(Thread thread)
	{
		thread.interrupt();
	}

	public static void printThisMethod()
	{
		System.out.println(codeLocation());
	}

	public static String codeLocation()
	{
		StackTraceElement element = new Exception().getStackTrace()[1];
		return element.getClassName() + "." + element.getMethodName();
	}
}
