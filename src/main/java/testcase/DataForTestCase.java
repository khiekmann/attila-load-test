package testCase;

import time.Time;


/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Starting data structure for TestCase
 */
public class DataForTestCase
{
	public Time expectedDuration;
	public Time actualDuration;
	public Time timestampEnd;
	public Time timestampStart;

	public DataForTestCase() {
		expectedDuration = Time.seconds(0);
		actualDuration = Time.seconds(0);
		timestampEnd = Time.seconds(0);
		timestampStart = Time.seconds(0);
	}

	@Override
	public String toString() {
		String message = "DataForTestCase\n";
		message += "expectedDuration: " + expectedDuration + "\n";
		message += "duration: " + actualDuration + "\n";
		message += "timestampEnd: " + timestampEnd + "\n";
		message += "timestampStart: " + timestampStart + "\n";
		return message;
	}
}
