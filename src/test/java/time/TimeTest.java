package time;

import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by HiekmaHe on 13.11.2016.
 *
 * SRP: Testing Time Object
 */
public class TimeTest
{
	@Test
	public void testCompateTo_GreaterThan() {
		// arrange
		Time a5 = new Time(5);
		Time a3 = new Time(3);

		// act
		int a5gta3 = a5.compareTo(a3);

		// assert
		assertTrue(a5gta3 > 0);
		assertFalse(a5gta3 < 0);
	}

	@Test
	public void testCompateTo_LessThan() {
		// arrange
		Time a5 = new Time(5);
		Time a3 = new Time(3);

		// act
		int a3lta5 = a5.compareTo(a3);

		// assert
		assertTrue(a3lta5 > 0);
		assertFalse(a3lta5 < 0);
	}

	@Test
	public void testCompareTo_Equal() {
		// arrange
		Time a5 = new Time(5);
		Time a5_ = new Time(5);

		// act
		int a5eqa5_ = a5.compareTo(a5_);

		// assert
		assertFalse(a5eqa5_ > 0);
		assertFalse(a5eqa5_ < 0);
		assertTrue(a5eqa5_ == 0);
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
		Time fiveSeconds = new Time(Long.valueOf("5000000000"));

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

	@Test
	public void testNegate() {
		// arrange
		Time one = new Time(1);

		// act
		Time one_negated = one.negate();

		// assert
		assertNotEquals(one, one_negated);
		assertEquals(new Time(-1), one_negated);
	}

	@Test
	public void testDifference() {
		// arrange
		Time five = new Time(5);
		Time three = new Time(3);
		Time minusthree = new Time(-3);
		Time minusfive = new Time(-5);

		// act

		// assert
		assertEquals(new Time(2), five.difference(three));
		assertEquals(new Time(8), five.difference(minusthree));
		assertEquals(new Time(2), minusfive.difference(minusthree));
	}

	@Test
	public void testNow() {
		// arrange
		Time now = Time.now();

		// act;

		// assert
		assertNotNull(now);
	}

	@Test
	public void testSubstract() {
		// arrange
		Time five = new Time(5);
		Time two = new Time(2);

		// act

		// assert
		assertEquals(new Time(3), five.substract(two));
	}

	@Test
	public void testCreation() {
		// arrange
		Time a = Time.now();
		Time b = Time.seconds(1);
		Time c = Time.millis(2);
		Time d = Time.nanos(3);
		Time e = new Time(4);

		// act

		// assert
		assertNotNull(a);
		assertNotNull(b);
		assertNotNull(c);
		assertNotNull(d);
		assertNotNull(e);
	}
}
