package _framework;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 21.11.2016.
 *
 * SRP: WireMock Attila service
 */
public class WireMockVanilla
{
	private static int port = 9001;
	private static String host = "http://localhost:";
	private static String urlPath = "/vanilla/";

	public URL getURL() throws MalformedURLException
	{
		return new URL(host + port + urlPath);
	}

	public WireMockRule getRule()
	{
		return new WireMockRule(port);
	}

	public MappingBuilder whenAnyRequestReceivedThenReturn200()
	{
		return any(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(200)
		);
	}

	public HttpURLConnection openConnection() throws IOException
	{
		return (HttpURLConnection) getURL().openConnection();
	}
}