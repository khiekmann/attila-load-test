package all;

import java.io.IOException;


/**
 * Created by HiekmaHe on 15.11.2016.
 *
 */
public interface Connectionable
{
	void send(String message) throws IOException;

	int getResponseCode();
}
