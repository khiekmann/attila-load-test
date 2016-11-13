package all;

/**
 * Created by HiekmaHe on 10.11.2016.
 *
 * SRP: Starting data structure for TestCase
 */
public class DataForTestCase
{
	public Time expectedDuration = Time.seconds(0);
	public Time actualDuration = Time.seconds(0);
	public Time timestampEnd = Time.seconds(0);
	public Time timestampStart = Time.seconds(0);

	@Override
	public String toString() {
		String message = "DataForTestCase\n";
		message += "expectedDuration: " + expectedDuration + "\n";
		message += "actualDuration: " + actualDuration + "\n";
		message += "timestampEnd: " + timestampEnd + "\n";
		message += "timestampStart: " + timestampStart + "\n";
		return message;
	}
}
