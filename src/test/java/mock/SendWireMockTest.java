package mock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.*;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 * SRP: Mock behavior of a server:
 * 1.) accept POST
 * 2.) with header X-TRACE-ID
 * 3.) send body, in example, as xml
 * 4.) return 202
 * 5.) all else 406 or so
 */
public class SendWireMockTest
{

	private static int port = 8080;
	@ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule public WireMockClassRule instanceRule = wireMockRule;
	private static URL url;
	private String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	@Before
	public void before() throws MalformedURLException
	{
		String host = "http://localhost:";
		String urlPath = "/cai/realtimemonitoring/v1/data";
		url = new URL(host + port + urlPath);
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
	}

	@Ignore
	@Test
	public void testNotAcceptable406() throws IOException
	{
		// arrange
		boolean caught406 = false;

		// act
		try {
			new InputStreamReader(url.openStream());
		} catch (IOException e) {
			if (e.getMessage().contains(String.valueOf(406))) {
				caught406 = true;
			}
		}

		// assert
		assertTrue("I do not know how to implement this behavior in wiremock", caught406);
	}

	@Test
	public void testSendPostToMySpecial_responseCode() throws Exception
	{
		// arrange
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write(content);
		writer.flush();
		int responseCode = connection.getResponseCode();
		writer.close();

		// assert
		assertEquals(202, responseCode);
	}

	@Test
	public void testSendPostToMySpecial_emptyResponse() throws IOException
	{
		// arrange
		String expected = "Accepted";
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write(content);
		writer.flush();
		String responseCode = connection.getResponseMessage();
		writer.close();

		// assert
		assertEquals(expected, responseCode);
	}
}