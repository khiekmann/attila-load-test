package time;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by HiekmaHe on 10.11.2016.
 */
public class TimeConverterTest
{
	@Test
	public void testCreation() {
		// arrange
		TimeConverter converter;

		// act
		converter = new TimeConverter();

		// assert
		assertNotNull(converter);
	}
	@Test
	public void secondsToNanos() throws Exception
	{
		// arrange
		long _2seconds = 2;
		long nanos = 2 * 1000 * 1000 * 1000;

		// act
		long _nanos = TimeConverter.secondsToNanos(_2seconds);

		// assert
		assertEquals(nanos, _nanos);
	}

	@Test
	public void nanosToMillis() throws Exception
	{
		// arrange
		long nanos = 1 * 1000 * 1000;
		long expectedMillis = 1;

		// act
		long millis = TimeConverter.nanosToMillis(1 * 1000 * 1000);

		// assert
		assertEquals(expectedMillis, millis);
	}

	@Test
	public void couldALoadTestRunLongerThanAWeek() throws Exception
	{
		// arrang
		long maxNanos = Long.MAX_VALUE;

		// act
		long expectedDurationInSeconds =  9223372036l;
		long durationInSeconds = TimeConverter.nanosToSeconds(maxNanos);
		double expectedDurationInMinutes =  1.5372286726666668E8;
		double durationInMinutes = (double) durationInSeconds / 60;
		double expectedDurationHours =  2562047.787777778;
		double durationInHours = durationInMinutes / 60;
		double expectedDurationDays =  106751.99115740742;
		double durationInDays = durationInHours / 24;
		double expectedDurationWeeks=  15250.284451058204;
		double durationInWeeks = durationInDays / 7;
		boolean couldALoadTestRunLongerThanAWeek = (durationInWeeks > 1);

		// assert
		assertEquals(expectedDurationInSeconds, durationInSeconds);
		assertEquals(expectedDurationInMinutes, durationInMinutes, 3);
		assertEquals(expectedDurationHours, durationInHours, 3);
		assertEquals(expectedDurationDays, durationInDays, 3);
		assertEquals(expectedDurationWeeks, durationInWeeks, 3);
		assertTrue(couldALoadTestRunLongerThanAWeek);

	}

	@Test
	public void millisToNanos()
	{
		// arrange
		long expectedNanos = 500 * 1000 * 1000;
		long millis = 500;

		// act
		long actualNanos = TimeConverter.millisToNanos(millis);

		// assert
		assertEquals(expectedNanos, actualNanos);
	}

	@Test
	public void nanoTime() {
		// arrange
		Time now1 = new Time(System.nanoTime());
		Time now2 = Time.now();

		// act
		Time delta = now1.substract(now2);

		// assert
	}
}