package useCase;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 20.11.2016.
 */
public class UseCaseableTest
{
	private static UseCaseable useCaseable;

	@Before
	public void before() {
		useCaseable = UseCaseableTestHelper.createUseCaseable();
	}

	@Test
	public void testDoOneIteration() throws Exception
	{
		// arrange

		// act
		useCaseable.doOneIteration();

		// assert
		assertEquals(1,  UseCaseableTestHelper.size());
		assertTrue(UseCaseableTestHelper.wasDoOneIterationExecuted());
	}

	@Test
	public void testGetResponseCode() throws Exception
	{
		// arrange

		// act
		int responseCode = useCaseable.getResponseCode();

		// assert
		assertEquals(0, responseCode);
	}
}