package all;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Convert between different time multitudes
 */
public class Converter
{
	public static long secondsToNanos(long seconds)
	{
		return seconds * 1000 * 1000 * 1000;
	}

	public static long nanosToSeconds(long nanos) {
		return nanos / 1000 / 1000 / 1000;
	}

	public static long nanosToMillis(long nanos)
	{
		return nanos / 1000 / 1000;
	}

	public static long millisToNanos(long millis)
	{
		return millis * 1000 * 1000;
	}
}
