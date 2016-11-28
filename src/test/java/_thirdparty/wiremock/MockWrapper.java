package _thirdparty.wiremock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


/**
 * Created by HiekmaHe on 27.11.2016.
 *
 * SRP: Wrapp WireMockClassRule to offer needed functionality.
 *
 */
public class MockWrapper
{
	private static String host = "http://localhost";
	private static String newline = System.getProperty("line.separator");

	private int port;
	private String urlPath;
	private final String content;
	private WireMockClassRule wireMockClassRule;

	public MockWrapper(String urlPath, String content) {
		this.port = 8080;
		this.urlPath = urlPath;
		this.content = content;
		this.wireMockClassRule = new WireMockClassRule(port);
	}

	public WireMockClassRule getWireMockClassRule()
	{
		return wireMockClassRule;
	}

	public String getStrUrl()
	{
		return host + ":" + port + urlPath;
	}

	public MappingBuilder receivesAnyRequestThenReturn200TextplainContent()
	{
		return any(urlEqualTo(urlPath))
				.willReturn(
						aResponse()
								.withStatus(HttpURLConnection.HTTP_OK)
								.withHeader("Content-Type", "text/plain")
								.withBody(content)
				);
	}

	public MappingBuilder receivesPostRequestWithContent_ThenReturn201()
	{
		return post(
				urlEqualTo(urlPath))
					.withRequestBody(containing("xml"))
					.willReturn(
							aResponse()
								.withStatus(HttpURLConnection.HTTP_ACCEPTED)
				);
	}

	public MappingBuilder receivesPostRequestNoContent_ThenReturn406()
	{
		return post(
				urlEqualTo(urlPath))
					.withRequestBody(equalTo(""))
						.willReturn(
							aResponse()
								.withStatus(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				);
	}

	public MappingBuilder receivesGetRequest_ThenReturn406()
	{
		return get(urlEqualTo(urlPath))
				.willReturn(
						aResponse()
								.withStatus(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				);
	}

	public URL createUrlExitOnException()
	{
		URL url;
		try {
			url = new URL(this.getStrUrl());
		} catch (MalformedURLException e) {
			url = null;
			System.err.println("Important URL must not be malformed. Exiting.\n" + e.getMessage());
			System.exit(0);
		}
		return url;
	}

	public MappingBuilder doesNotReceivePostRequestThenReturn406()
	{
		return any(anyUrl())
				.willReturn(
						aResponse()
								.withStatus(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				);
	}

	public String readPage() throws IOException
	{
		return readPage(createUrlExitOnException());
	}

	public String readAdminPage() throws IOException
	{
		return readPage(new URL(host + ":" + port + "/__admin"));
	}

	private String readPage(URL url) throws IOException
	{
		String readPage;
		URLConnection conn = url.openConnection();
		InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
		try (BufferedReader reader = new BufferedReader(inputStreamReader))
		{
			readPage = reader.lines().collect(Collectors.joining(newline));
		}
		return readPage;
	}
}