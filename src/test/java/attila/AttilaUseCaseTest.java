package attila;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class AttilaUseCaseTest
{
	@Rule
	public WireMockRule wireMockRule = AttilaTestHelper.wireMockRule();
	private AttilaUseCase useCase;

	@Before
	public void before() throws IOException
	{
		wireMockRule.stubFor(AttilaTestHelper.buildMappingInbound());
		useCase = AttilaTestHelper.createUseCase();
	}

	@Ignore
	@Test
	public void testGetResponse() throws Exception
	{
		// arrange

		// act
		AttilaUseCase useCase = AttilaTestHelper.createUseCase();
		useCase.doOneIteration();

		// assert
		assertEquals(202, useCase.getResponseCode());
	}
}