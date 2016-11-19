package attila;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.github.tomakehurst.wiremock.http.RequestMethod;
import connection.ConnectionCreate;


/**
 * Created by HiekmaHe on 19.11.2016.
 */
public class AttilaConnectionCreate extends ConnectionCreate
{
	private URL url;

	public static AttilaConnectionCreate createInstance (URL url) throws IOException
	{
		return new AttilaConnectionCreate(createHttpUrl(url), url);
	}

	private static HttpURLConnection createHttpUrl(URL url) throws IOException
	{
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		httpUrl.setConnectTimeout(0);
		httpUrl.setRequestMethod(RequestMethod.POST.getName());
		return httpUrl;
	}

	private AttilaConnectionCreate(HttpURLConnection httpUrl, URL url) {
		super(httpUrl);
		this.url = url;
	}

	@Override
	public ConnectionCreate create() throws IOException
	{
		return AttilaConnectionCreate.createInstance(url);
	}
}