package _thirdparty.travis;

import org.junit.Ignore;
import org.junit.Test;

import time.Time;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP: Travis will make these fail.
 */
public class FailsInTravis
{
	@Ignore
	@Test
	public void testFormat_yearBlaBlaSeconds() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String yearBlaBlaSeconds = timeObject.format("yyyy-MM-dd-HH-mm-ss");

		// assert
		System.err.println("1970-01-01-01-00-05" + " - " + yearBlaBlaSeconds);
		assertEquals("1970-01-01-01-00-05", yearBlaBlaSeconds);
	}

	@Ignore
	@Test
	public void testFormat_dateAndNanos() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String dateAndNanos = timeObject.format("dd MMM yyyy HH:mm:ss,SSS");

		// assert
		System.err.println("01 Jan 1970 01:00:05,000" + " - " + dateAndNanos);
		assertEquals("01 Jan 1970 01:00:05,000", dateAndNanos);
	}
}