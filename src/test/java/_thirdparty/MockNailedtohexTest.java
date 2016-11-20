package _thirdparty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by HiekmaHe on 12.11.2016.
 *
 * http://www.nailedtothex.org/roller/kyle/entry/articles-test-wiremock
 */
public class MockNailedtohexTest
{

	private static int PORT = 18089;
	@ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(PORT);
	@Rule public WireMockClassRule instanceRule = wireMockRule;

	@Before
	public void before() {
		stubFor(get(urlEqualTo("/hoge.txt")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "text/plain").withBody("hoge")));
	}

	@Test
	public void ok() throws Exception {
		String actual = readAdminPage("http://localhost:18089/hoge.txt");
		String expected = "hoge";
		assertThat(actual, is(expected));
	}

	private String readAdminPage(String urlname) throws IOException
	{
		URL url = new URL(urlname);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()));

		String inputLine;
		StringBuilder stringer = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
		{
			stringer.append(inputLine);
		}
		in.close();
		return stringer.toString();
	}
}
