package connection;

/**
 * Created by HiekmaHe on 17.11.2016.
 */
public enum MyRequestMethod
{
	POST("POST");

	private final String method;

	MyRequestMethod(String strMethod)
	{
		method = strMethod;
	}
}
