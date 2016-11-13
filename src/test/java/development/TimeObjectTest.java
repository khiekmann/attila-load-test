package development;

import org.junit.Test;

import all.Time;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 13.11.2016.
 */
public class TimeObjectTest
{
	@Test
	public void test() {
		// arrange
		Time a5 = new Time(5);
		Time a3 = new Time(3);
		Time _a5_ = new Time(5);

		// act
		int a5gta3 = a5.compareTo(a3);
		int a3lta5 = a5.compareTo(a3);
		int a5equal = a5.compareTo(_a5_);

		// assert
		assertTrue(a5gta3 > 0);
		assertTrue(a5gta3 < 1);
		assertFalse(a5gta3 == 0);
	}

	@Test
	public void testFormat_yearBlaBlaSeconds() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String yearBlaBlaSeconds = timeObject.format("yyyy-MM-dd-HH-mm-ss");

		// assert
		assertEquals("1970-01-01-01-00-05", yearBlaBlaSeconds);
	}

	@Test
	public void testFormat_dateAndNanos() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String dateAndNanos = timeObject.format("dd MMM yyyy HH:mm:ss,SSS");

		// assert
		assertEquals("01 Jan 1970 01:00:05,000", dateAndNanos);
	}

	@Test
	public void testFormat_secondsAndNanos() {
		// arrange
		Time timeObject = Time.seconds(5);

		// act
		String secondsAndNanos = timeObject.format("ss,SSS");

		// assert
		assertEquals("05,000", secondsAndNanos);
	}

	@Test
	public void testCompareCreated() {
		// arrange
		Time fiveSeconds = new Time(5000000000l);

		// act
		Time a5Seconds = Time.seconds(5);
		Time a5Seconds_FromMillis= Time.millis(5000);
		Time a5Seconds_FromNanos = Time.nanos((long) 5000 * 1000 * 1000);

		// assert
		assertEquals(fiveSeconds, a5Seconds);
		assertEquals(fiveSeconds, a5Seconds_FromMillis);
		assertEquals(fiveSeconds, a5Seconds_FromNanos);
	}

	@Test
	public void testEqualsCreation() {
		// arrange

		// act
		Time a5Seconds = Time.seconds(5);
		Time a5Seconds_FromMillis= Time.millis(5000);
		Time a5Seconds_FromNanos = Time.nanos((long) 5000 * 1000 * 1000);

		// assert
		assertEquals(a5Seconds, a5Seconds_FromMillis);
		assertEquals(a5Seconds_FromMillis, a5Seconds_FromNanos);
		assertEquals(a5Seconds_FromNanos, a5Seconds);
	}

	@Test
	public void testAddition() {
		// arrange
		Time a5Seconds = Time.seconds(5);
		Time a5Seconds_FromMillis = Time.millis(5000);

		// act
		Time a10Seconds = a5Seconds.add(a5Seconds_FromMillis);

		// assert
		assertEquals(Time.seconds(10), a10Seconds);
	}

	@Test
	public void testAddition2() {
		Time killAfter = Time.seconds(5);
		Time expectedLongestDurationOfRun = killAfter.add(Time.millis(500));
		// assert
		assertEquals(Time.millis(5500), expectedLongestDurationOfRun);
	}
}
