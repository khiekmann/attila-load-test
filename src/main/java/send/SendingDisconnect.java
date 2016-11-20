package send;

/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Disconnect
 */
public class SendingDisconnect extends SendingAbstract
{

	public SendingDisconnect(SendingResponse response)
	{
		super(response);
		getHttpUrl().disconnect();
	}
}