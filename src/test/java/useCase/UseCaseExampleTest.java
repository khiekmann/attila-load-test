package useCase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import send.Sendable;
import send.Sending;
import send.SendingCreate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by HiekmaHe on 25.11.2016.
 *
 * SRP:
 */
public class UseCaseExampleTest
{

	private static int port = 8080;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	//	public WireMockClassRule rule = wireMockRule;
	private String urlPath= "/useCaseExampleTest";
	private String strUrl;
	private UseCaseExample useCaseExample;

	@Before
	public void before() throws IOException
	{
		strUrl = "http://0.0.0.0:8080" + urlPath;
		//createStubMapping();
		useCaseExample = createUseCaseExample();
	}

	private void createStubMapping()
	{
		givenThat(post(urlEqualTo(urlPath)).withRequestBody(equalTo("foo"))
				.willReturn(
						aResponse()
								.withStatus(200)
				)
		);
	}

	private UseCaseExample createUseCaseExample() throws IOException
	{
		URL url = new URL(strUrl);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setDoOutput(true);
		SendingCreate creator = new UseCaseExampleTestSendingCreate(httpUrl, url);
		Sendable sender = new Sending(creator);
		return new UseCaseExample(sender);
	}

	@Test
	public void testExample() throws IOException, InterruptedException
	{
		// arrange

		// act
		useCaseExample.executeOnce();
		useCaseExample.executeOnce();

		// assert
	}
}