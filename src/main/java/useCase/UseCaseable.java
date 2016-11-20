package useCase;

import java.io.IOException;


/**
 * Created by HiekmaHe on 15.11.2016.
 */
public interface UseCaseable
{
	void doOneIteration() throws IOException;

	int getResponseCode();
}
