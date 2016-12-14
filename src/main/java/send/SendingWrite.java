package send;

import java.io.IOException;

import message.Message;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 * SRP: Send message
 */
public class SendingWrite extends SendingAbstract
{
	public SendingWrite(SendingCreate create, Message message) throws IOException
	{
		super(create);
		getHttpUrl().getOutputStream().write(message.getBytes());
		getHttpUrl().getOutputStream().flush();
		getHttpUrl().getOutputStream().close();
	}
}