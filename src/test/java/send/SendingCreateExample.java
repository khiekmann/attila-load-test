package send;

import java.io.IOException;
import java.net.HttpURLConnection;


/**
 * Created by HiekmaHe on 13.12.2016.
 */
public class SendingCreateExample extends SendingCreate
{
	public SendingCreateExample(HttpURLConnection httpUrl)
	{
		super(httpUrl);
	}

	@Override
	public SendingCreate create() throws IOException
	{
		return new SendingCreateExample(getHttpUrl());
	}
}
