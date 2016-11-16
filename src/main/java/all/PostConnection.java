package all;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by HiekmaHe on 16.11.2016.
 */
public class PostConnection implements Connectionable
{
	private URL url;
	private int responseCode;
	private String responseMessage;
	private HttpURLConnection connection;

	public PostConnection(URL useThisURL){
		url = useThisURL;
		responseCode = -1;
		responseMessage = "not set yet";
	}

	@Override
	public void send(String message) throws IOException
	{
		createConnection();
		sendToConnection(message);
		readStatusFromConnection();
		closeConnection();
	}

	@Override
	public int getResponseCode()
	{
		return responseCode;
	}

	private void createConnection() throws IOException
	{
		connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(0);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
	}

	private void sendToConnection(String message) throws IOException
	{
		try {
			connection.getOutputStream().write(message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
	}

	private void readStatusFromConnection() throws IOException
	{
		responseCode = connection.getResponseCode();
		responseMessage = connection.getResponseMessage();
	}

	private void closeConnection() throws IOException
	{
		connection.disconnect();
	}
}