package attila;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import _framework.Context;
import _framework.WireMockAttila;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class AttilaUseCaseTest
{
	private Context context;
	private WireMockAttila mock;
	@Rule	public WireMockRule rule;
	private AttilaUseCase useCase;

	@Before
	public void before() throws IOException
	{
		context = new Context();
		rule.givenThat(mock.whenPostRequestReceivedThenReturn202());
		useCase = context.getAttilaUseCase();
	}

	@Test
	public void testGetResponse() throws Exception
	{
		// arrange

		// act
		useCase.doOneIteration();

		// assert
		assertEquals(202, useCase.getResponseCode());
	}
}