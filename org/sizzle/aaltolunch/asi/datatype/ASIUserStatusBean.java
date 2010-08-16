package org.sizzle.aaltolunch.asi.datatype;
import org.json.simple.JSONObject;


public class ASIUserStatusBean 
{
	private String message;
	private String changed;
	
	public ASIUserStatusBean (String message, String changed)
	{
		this.message = message;
		this.changed = changed;
	}

	public ASIUserStatusBean() {
	}

	public String getMessage() {
		return message;
	}

	public String getChanged() {
		return changed;
	}
	
	public ASIUserStatusBean parseStatusObject(JSONObject statusObj)
	{
		ASIUserStatusBean userName = null;
		
		if (statusObj != null)
		{
			String message = statusObj.get("message") != null ? statusObj.get("message").toString() : null;
			String changed = statusObj.get("changed") != null ? statusObj.get("changed").toString() : null;
			
			userName = new ASIUserStatusBean (message, changed);
		}
		
		return userName;
	}
}