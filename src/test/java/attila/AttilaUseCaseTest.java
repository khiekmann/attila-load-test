package attila;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.TestHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 *
 * SRP: Tests for use case attila.
 */
public class AttilaUseCaseTest
{

	private AttilaMockWrapper mock = new AttilaMockWrapper();
	@Rule	public WireMockClassRule rule = mock.getWireMockClassRule();
	private AttilaUseCase useCase;

	@Before
	public void before() throws IOException
	{
		rule.givenThat(mock.receivesPostRequestWithContent_ThenReturn201());
		useCase = TestHelper.createAttilaUseCase();
	}

	@Test
	public void testGetResponse() throws Exception
	{
		// arrange

		// act
		useCase.executeOnce();

		// assert
		assertEquals(HttpURLConnection.HTTP_ACCEPTED, useCase.getResponseCode());
	}
}