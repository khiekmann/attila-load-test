package _thirdparty.wiremock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 12.11.2016.
 *
 * http://www.nailedtothex.org/roller/kyle/entry/articles-test-wiremock
 */
public class _1_Refactor
{
	private String responseContent = "hoge";
	private MockWrapper mock = new MockWrapper("/hoge.txt", responseContent);
	@Rule public WireMockClassRule rule = mock.getWireMockClassRule();

	@Before
	public void before()
	{
		givenThat(mock.receivesAnyRequestThenReturn200TextplainContent());
	}

	@Test
	public void ok() throws Exception {
		String actual = mock.readPage();
		assertEquals(responseContent, actual);
	}
}
