package development;

import org.junit.Test;

import all.Converter;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by HiekmaHe on 10.11.2016.
 */
public class ConverterTest
{
	@Test
	public void secondsToNanos() throws Exception
	{
		// arrange
		long _2seconds = 2;
		long nanos = 2 * 1000 * 1000 * 1000;

		// act
		long _nanos = Converter.secondsToNanos(_2seconds);

		// assert
		assertEquals(nanos, _nanos);
	}

	@Test
	public void couldALoadTestRunLongerThanAWeek() throws Exception
	{
		// arrang
		long maxNanos = Long.MAX_VALUE;

		// act
		long expectedDurationInSeconds =  9223372036l;
		long durationInSeconds = Converter.nanosToSeconds(maxNanos);
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
}