package _thirdparty.wiremock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by HiekmaHe on 13.11.2016.
 *
 */
public class MockSendingAttila
{
   private String attilaContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	private static final String POST = RequestMethod.POST.value();
	private static final String GET = RequestMethod.GET.value();

	private String urlPath = "/c/r/v/d";
	private String responseMessage = null;
	private MockWrapper mock = new MockWrapper(urlPath, responseMessage);
	@Rule public WireMockClassRule rule = mock.getWireMockClassRule();

	@Before
	public void before() throws MalformedURLException
	{
		givenThat(mock.receivesPostRequestWithContent_ThenReturn201());
		givenThat(mock.receivesPostRequestNoContent_ThenReturn406());
		givenThat(mock.receivesGetRequest_ThenReturn406());
	}

	@Test
	public void sendPostRequestWithContent() throws Exception
	{
		// arrange
		URL url = mock.createUrlExitOnException();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(POST);
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write(attilaContent);
		writer.flush();
		writer.close();
		InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String readLine = reader.readLine();

		// assert
		assertEquals(HttpURLConnection.HTTP_ACCEPTED, connection.getResponseCode());
		assertNull(readLine);
	}

	@Test
	public void sendPostRequestNoContent() throws IOException
	{
		// arrange
		URL url = mock.createUrlExitOnException();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(POST);
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write("");
		writer.flush();
		writer.close();

		// assert
		assertEquals(HttpURLConnection.HTTP_NOT_ACCEPTABLE, connection.getResponseCode());
	}

	@Test
	public void sendGetRequest() throws IOException
	{
		// arrange
		URL url = mock.createUrlExitOnException();
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod(GET);

		// act

		// assert
		assertEquals(HttpURLConnection.HTTP_NOT_ACCEPTABLE, connection.getResponseCode());
	}
}