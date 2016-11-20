package send;

/**
 * Created by HiekmaHe on 20.11.2016.
 */
public enum RequestMethod
{
	POST("POST"),
	GET("GET");

	private final String value;

	RequestMethod(String value)
	{
		this.value = value;
	}

	public String value() {
		return value;
	}
}
