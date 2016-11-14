package mock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by HiekmaHe on 13.11.2016.
 */
public class SendRTMTest_Deactivate
{

	private static int port = 8080;
	private static String host = "http://decgn-pr-rtm-disp-1.de.valtech.com:";
	private static String urlPath = "/cai/realtimemonitoring/v1/data";
	private static URL url;

	private String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns6:sendRealTimeMonitoringDataRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns3=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns4=\"http://www.vw.com/mbb/commonTypes\" xmlns:ns5=\"http://www.vw.com/mbb/diagnostic\" xmlns:ns6=\"http://www.vw.com/mbb/sendRealTimeMonitoringDataRequest\" ns6:messageCounter=\"37\"><ns6:instrumentClusterTime>2016-07-06T14:10:53Z</ns6:instrumentClusterTime><ns6:obdcData ns5:version=\"1.0\"><ns5:dataGroup ns5:groupID=\"0603\">/RUABwMGBEsDADnjFBoAAKcAAQCGDAAAAMC2bRsAAAAAAEAAAAAAWQUwA+ugfFf+/////v///wcxbbcAAAAAAAAE+FjApPFGy0NMAABNIE5CJlf8B/7+//7+D/7+//7+DwAAAAAAAAAAd4lmAAQAjQ7e////AAP//ldgbYaEi3qoaqd2hQkmYLVACgQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAhoQ=</ns5:dataGroup></ns6:obdcData><ns6:vin ns4:theVIN=\"IMAVIN0\"/></ns6:sendRealTimeMonitoringDataRequest>";


	@Before
	public void before() throws MalformedURLException
	{
		url = new URL(host + port + urlPath);
	}

//	@Test
	public void testNotAcceptable406() throws IOException
	{
		// arrange
		boolean caught406 = false;

		// act
		try {
			new InputStreamReader(url.openStream());
		} catch (IOException e) {
			if (e.getMessage().contains(String.valueOf(406))) {
				caught406 = true;
			}
		}

		// assert
		assertTrue(caught406);
	}

//	@Test
	public void testSendPostToRTM_responseCode() throws Exception
	{
		// arrange
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write(content);
		writer.flush();
		int responseCode = connection.getResponseCode();
		writer.close();

		// assert
		assertEquals(202, responseCode);
	}

//	@Test
	public void testSendPostToRTM_emptyResponse() throws IOException
	{
		// arrange
		String expected = "Accepted";
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		// act
		writer.write(content);
		writer.flush();
		String responseCode = connection.getResponseMessage();
		writer.close();

		// assert
		assertEquals(expected, responseCode);
	}
}