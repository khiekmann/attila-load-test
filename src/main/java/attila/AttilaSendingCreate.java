package attila;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import send.SendingCreate;


/**
 * Created by HiekmaHe on 19.11.2016.
 */
public class AttilaSendingCreate extends SendingCreate
{
	private URL url;

	public static AttilaSendingCreate createInstance (URL url) throws IOException
	{
		return new AttilaSendingCreate(createHttpUrl(url), url);
	}

	private static HttpURLConnection createHttpUrl(URL url) throws IOException
	{
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		httpUrl.setConnectTimeout(0);
		httpUrl.setRequestMethod("POST");
		return httpUrl;
	}

	private AttilaSendingCreate(HttpURLConnection httpUrl, URL url) {
		super(httpUrl);
		this.url = url;
	}

	@Override
	public SendingCreate create() throws IOException
	{
		return AttilaSendingCreate.createInstance(url);
	}
}