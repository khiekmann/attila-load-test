package send;

import java.io.IOException;
import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 17.11.2016.
 *
 */
public abstract class SendingCreate extends SendingAbstract
{
	public SendingCreate(HttpURLConnection httpUrl)
	{
		super(httpUrl);
	}

	public abstract SendingCreate create() throws IOException;
}