package org.sizzle.aaltolunch.asi.datatype;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ASISessionBean 
{
	private String appId;
	private String userId;
	
	public ASISessionBean(String app_id, String user_id)
	{
		this.appId = app_id;
		this.userId = user_id;
	}

	public ASISessionBean() {
	}

	public String getAppId() {
		return appId;
	}

	public String getUserId() {
		return userId;
	}
	
	public ASISessionBean parseSessionResponse (String response) throws ParseException
	{
		ASISessionBean sessionBean = null;
		String user_id = null;
		String app_id = null;		
		JSONParser jsonParser = new JSONParser();

		final JSONObject jsonObj = (JSONObject)jsonParser.parse(response);
		if (jsonObj != null)
		{
			if (jsonObj.containsKey("entry"))
			{
				final JSONObject entryObj = (JSONObject)jsonObj.get("entry");
				
				user_id = entryObj.get("user_id") != null ? entryObj.get("user_id").toString() : null;
				app_id = entryObj.get("app_id") != null ? entryObj.get("app_id").toString() : null;

				sessionBean = new ASISessionBean (app_id, user_id);
			}
		}
		
		return sessionBean;
	}
}
