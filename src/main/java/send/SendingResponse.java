package send;

import java.io.IOException;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Provide responseCode
 */
public class SendingResponse extends SendingAbstract
{
	private int responseCode;

	public SendingResponse(SendingWrite send) throws IOException
	{
		super(send);
		responseCode = getHttpUrl().getResponseCode();
	}

	public int code()
	{
		return responseCode;
	}
}
