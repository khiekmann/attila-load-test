package attila;

import message.Message;


/**
 * Created by HiekmaHe on 01.12.2016.
 */
public class AttilaMessage extends Message
{
	long xtraceId;

	public AttilaMessage(String content, long xtraceId)
	{
		super(content);
		this.xtraceId = xtraceId;
	}
}
