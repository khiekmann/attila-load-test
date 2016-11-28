package useCase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import send.SendingCreate;


/**
 * Created by HiekmaHe on 25.11.2016.
 */
public class UseCaseExampleTestSendingCreate extends SendingCreate
{
	private URL url;

	public UseCaseExampleTestSendingCreate(HttpURLConnection httpUrl, URL url) {
		super(httpUrl);
		this.url = url;
	}

	@Override
	public SendingCreate create() throws IOException
	{
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		return new UseCaseExampleTestSendingCreate(httpUrl, url);
	}
}
