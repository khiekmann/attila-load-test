package attila;

import _thirdparty.wiremock.MockWrapper;


/**
 * Created by HiekmaHe on 27.11.2016.
 *
 * SRP: Extend MockWrapper to wiremock attila's server
 */
public class AttilaMockWrapper extends MockWrapper
{
	public AttilaMockWrapper()
	{
		super("/c/r/v/d", null);
	}
}
