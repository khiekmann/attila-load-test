package _framework;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 21.11.2016.
 *
 * SRP: WireMock Attila service
 */
public class WireMockAttila
{
	private int port = 8080;
	private String host = "http://localhost:";
	private String urlPath = "/cai/rtm/v1/d";
	private WireMockRule rule;

	public URL getURL() throws MalformedURLException
	{
		return new URL(host + port + urlPath);
	}

	public WireMockClassRule getClassRule()
	{
		return new WireMockClassRule(port);
	}

	public MappingBuilder whenPostRequestReceivedThenReturn202()
	{
		return post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
		);
	}

	public WireMockRule getRule()
	{
		return new WireMockRule();
	}
}