package _framework;

import java.io.IOException;
import java.net.HttpURLConnection;

import send.SendingCreate;


/**
 * Created by HiekmaHe on 22.11.2016.
 */
public class SendingCreateVanilla extends SendingCreate
{
	private static WireMockVanilla mock = new WireMockVanilla();

	public static SendingCreateVanilla createInstance() throws IOException
	{
		return new SendingCreateVanilla(mock.openConnection());
	}

	private SendingCreateVanilla(HttpURLConnection httpUrl) {
		super(httpUrl);
	}

	@Override
	public SendingCreate create() throws IOException
	{
		return SendingCreateVanilla.createInstance();
	}
}
