package _thirdparty;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.*;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertEquals;


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
public class MockActuallySendDataTest
{

	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	String host = "http://localhost";
	String urlPath = "/some/path.txt";
	private static URL url;
	private String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	@Before
	public void before() throws MalformedURLException
	{
		url = new URL(host + ":" + port + urlPath);
		stubFor(post(urlEqualTo(urlPath))
				.willReturn(
						aResponse()
								.withStatus(200)
				)
		);
	}

	@Ignore
	@Test
	public void testWiremockManually() throws Exception {
		System.out.println(host + ":" + port + urlPath);
		Thread.sleep(60000);
	}

	@Test
	public void testSomething() throws Exception
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
		assertEquals(200, responseCode);
	}
}