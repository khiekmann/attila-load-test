package connection;

import java.io.IOException;


/**
 * Created by HiekmaHe on 15.11.2016.
 *
 * SRP: Send message, save responseCode
 */
public interface Sendable
{
	void send(String message) throws IOException;

	int getResponseCode();
}