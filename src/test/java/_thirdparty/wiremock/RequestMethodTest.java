package _thirdparty.wiremock;

import org.junit.Test;

import com.github.tomakehurst.wiremock.http.RequestMethod;

import static org.junit.Assert.assertEquals;


/**
 * Created by HiekmaHe on 18.11.2016.
 *
 * SRP: Assert behavior of third party
 */
public class RequestMethodTest
{
	@Test
	public void testRequestMethods() {
		// arrange

		// act
		RequestMethod get = RequestMethod.GET;
		RequestMethod post = RequestMethod.POST;
		RequestMethod put = RequestMethod.PUT;
		RequestMethod delete = RequestMethod.DELETE;

		// assert
		assertEquals("GET", get.getName());
		assertEquals("POST", post.getName());
		assertEquals("PUT", put.getName());
		assertEquals("DELETE", delete.getName());
	}
}