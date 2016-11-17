package development;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import connection.MySpecialConnection;
import connection.Sendable;
import useCase.UseCaseMySpecial;
import useCase.UseCaseable;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 16.11.2016.
 */
public class UseCaseMySpecialTest
{
	private static int port = 8080;
	@ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(port);
	@Rule	public WireMockClassRule instanceRule = wireMockRule;

	private UseCaseable useCase;
	private String urlPath = "/test.txt";

	@Before
	public void before() throws IOException
	{
		List<String> messages = new ArrayList<>();
		messages.add("A");
		messages.add("B");
		messages.add("C");
		URL url = new URL("http://localhost:" + port + urlPath);
		Sendable sender = new MySpecialConnection(url);
		useCase = new UseCaseMySpecial(messages, sender);
		stubFor(post(urlEqualTo(urlPath)).willReturn(
				aResponse()
						.withStatus(202)
				)
		);
		// assert
		assertEquals("http://localhost:8080/test.txt", url.toString());
	}

	@Ignore
	@Test
	public void testWiremockManually() throws Exception
	{
		// arrange

		// act
		System.out.println("start...");
		Thread.sleep(60000);

		System.out.println("end...");
		// assert
	}

	@Test
	public void testCreation() {
		// arrange

		// act

		// assert
		assertNotNull(useCase);
	}

	@Test
	public void testOneIteration() {
		// arrange

		// act
		useCase.doOneIteration();

		// assert
		assertEquals(202, useCase.getResponseCode());
	}
}