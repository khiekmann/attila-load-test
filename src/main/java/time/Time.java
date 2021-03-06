package time;

import java.text.SimpleDateFormat;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 * SRP: Include all time representation here.
 */
public class Time implements Comparable<Time>
{

	public static final Time ZERO = new Time(0);
	public static final Time MINUSONE = new Time(-1);

	public static Time seconds(long amountOfSeconds)
	{
		return new Time(TimeConverter.secondsToNanos(amountOfSeconds));
	}

	public static Time now()
	{
		return nanos(System.nanoTime());
	}

	public static Time millis(long amountOfMillis)
	{
		return new Time(TimeConverter.millisToNanos(amountOfMillis));
	}

	public static Time nanos(long nanos)
	{
		return new Time(nanos);
	}

	private long timeAmount;

	public Time(long timeAmountinNanos) {
		this.timeAmount = timeAmountinNanos;
	}

	public long getTimeAmountInNanos() {
		return timeAmount;
	}

	@Override
	public int compareTo(Time other)
	{
		long delta = this.timeAmount - other.timeAmount;
		int deltaCompareToZero = 0;					// equal
		if (delta > 0 ) deltaCompareToZero = 1;	// this > that
		if (delta < 0 ) deltaCompareToZero = -1;  // this < that
		return deltaCompareToZero;
	}

	public Time substract(Time other)
	{
		return new Time(this.timeAmount - other.timeAmount);
	}

	public boolean lessThan(Time other)
	{
		return this.compareTo(other) < 0;
	}

	public boolean greaterThan(Time other)
	{
		return this.compareTo(other) > 0;
	}

	public Time add(Time addend)
	{
		return new Time(this.timeAmount + addend.timeAmount);
	}

	public long toMillis()
	{
		return timeAmount / 1000 / 1000;
	}

	@Override
	public String toString()
	{
		return String.valueOf(timeAmount);
	}

	public String format(String strDateFormat)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		return dateFormat.format(toMillis());
	}

	@Override
	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other instanceof Time) {
			Time otherTimeObject = (Time) other;
			isEqual = (this.timeAmount == otherTimeObject.timeAmount);
		}
		return isEqual;
	}

	public Time negate()
	{
		return this.multiply(Time.MINUSONE);
	}

	public Time multiply(Time multiplier)
	{
		return new Time(this.timeAmount * multiplier.timeAmount);
	}

	public Time difference(Time other)
	{
		Time subtracted = this.substract(other);
		if (subtracted.lessThan(Time.ZERO))
		{
		subtracted = subtracted.negate();
		}
		return subtracted;
	}

	public void sleep() throws InterruptedException
	{
		Thread.sleep(toMillis() + 1);
	}

	public static Time elapseSince(Time then)
	{
		return Time.now().difference(then);
	}

	public boolean inRange(Time other, Time range)
	{
		return this.difference(other).lessThan(range);
	}
}