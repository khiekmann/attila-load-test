package send;

import java.io.IOException;

import message.Message;


/**
 * Created by HiekmaHe on 15.11.2016.
 *
 * SRP: Send message, save responseCode
 */
public interface Sendable
{
	void send(Message message) throws IOException;

	int getResponseCode();
}