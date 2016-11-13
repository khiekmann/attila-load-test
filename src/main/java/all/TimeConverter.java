package all;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Convert between different time multitudes
 */
public class TimeConverter
{
	public static long nanosToSeconds(long nanos) {
		double seconds = (double) nanos / 1000 / 1000 / 1000;
		return (long) seconds;
	}

	public static long nanosToMillis(long nanos)
	{
		double millis = (double) nanos / 1000 / 1000;
		return (long) millis;
	}

	public static long millisToNanos(long millis)
	{
		return millis * 1000 * 1000;
	}

	public static long secondsToNanos(long seconds)
	{
		return seconds * 1000 * 1000 * 1000;
	}
}
