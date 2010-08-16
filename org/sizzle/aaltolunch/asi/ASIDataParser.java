package org.sizzle.aaltolunch.asi;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sizzle.aaltolunch.asi.datatype.ASIUserAddressBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserAvtarBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserNameBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserStatusBean;

public class ASIDataParser 
{
	public List<ASIUserBean> parseUsers (String response) throws ParseException
	{
		List<ASIUserBean> userBeans = null;
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject)jsonParser.parse(response);
		
		if (jsonObj != null)
		{
			if (jsonObj.containsKey("entry"))
			{
				JSONArray jsonArr = (JSONArray)jsonObj.get("entry");
				
				if (jsonArr != null && !jsonArr.isEmpty())
				{
					userBeans = new ArrayList<ASIUserBean>();
					
					for (int i = 0; i < jsonArr.size(); i++)
					{
						JSONObject o = (JSONObject)jsonArr.get(i);
						
						ASIUserBean userBean = parse (o);
						
						userBeans.add(userBean);
					}
				}
			}
		}
		
		return userBeans;
	}
	
	public ASIUserBean parseUser (String response) throws ParseException
	{
		ASIUserBean userBean = null;
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject)jsonParser.parse(response);
		
		if (jsonObj != null)
		{
			if (jsonObj.containsKey("entry"))
			{
				JSONObject entryObj = (JSONObject)jsonObj.get("entry");
				
				if (entryObj != null)
				{
					userBean = parse(entryObj);
				}
			}
		}
		
		return userBean;
	}
	
	private ASIUserBean parse (JSONObject o)
	{
		ASIUserBean userBean = null;
		
		ASIUserAddressBean parsedUserAddressInfo = null;
		ASIUserNameBean parsedUserNameInfo = null;
		String parsedBirthdate = null;
		String parsedConnection = null;
		String parsedIsAssociation = null;
		String parsedUpdatedAt = null;
		String parsedRole = null;
		String parsedUserName = null;
		String parsedGender = null;
		ASIUserAvtarBean parsedUserAvtarInfo = null;
		String parsedId = null;
		String parsedPhoneNumber = null;
		String parsedMsnNick = null;
		String parsedWebsite = null;
		String parsedDescription = null;
		String parsedIrcNick = null;
		ASIUserStatusBean parsedUserStatusInfo = null;
		String parsedEmail = null;
	
		if (o.containsKey("address"))
		{
			JSONObject addressObj = (JSONObject)o.get("address");
			ASIUserAddressBean auab = new ASIUserAddressBean();
			parsedUserAddressInfo = auab.parseAddressObject(addressObj);
		}
		
		if (o.containsKey("name"))
		{
			JSONObject nameObj = (JSONObject)o.get("name");
			ASIUserNameBean aunb = new ASIUserNameBean();
			parsedUserNameInfo = aunb.parseNameObject(nameObj);
		}
		
		parsedBirthdate = o.get("birthdate") != null ? o.get("birthdate").toString() : null;
		parsedConnection = o.get("connection") != null ? o.get("connection").toString() : null;
		parsedIsAssociation = o.get("is_association") != null ? o.get("is_association").toString() : null;
		parsedUpdatedAt = o.get("updated_at") != null ? o.get("updated_at").toString() : null;
		parsedRole = o.get("role") != null ? o.get("role").toString() : null;
		parsedUserName = o.get("username") != null ? o.get("username").toString() : null;
		parsedGender = o.get("gender") != null ? o.get("gender").toString() : null;
		
		if (o.containsKey("avatar"))
		{
			JSONObject avatarObj = (JSONObject)o.get("avatar");
			ASIUserAvtarBean auavb = new ASIUserAvtarBean();
			parsedUserAvtarInfo = auavb.parseAvtarObject(avatarObj);
		}
		parsedId = o.get("id") != null ? o.get("id").toString() : null;
		parsedPhoneNumber = o.get("phone_number") != null ? o.get("phone_number").toString() : null;
		parsedMsnNick = o.get("msn_nick") != null ? o.get("msn_nick").toString() : null;
		parsedWebsite = o.get("website") != null ? o.get("website").toString() : null;
		parsedDescription = o.get("description") != null ? o.get("description").toString() : null;
		parsedIrcNick = o.get("irc_nick") != null ? o.get("irc_nick").toString() : null;
		
		if (o.containsKey("status"))
		{
			JSONObject statusObj = (JSONObject)o.get("status");
			ASIUserStatusBean ausb = new ASIUserStatusBean();
			parsedUserStatusInfo = ausb.parseStatusObject(statusObj);
		}
		parsedEmail = o.get("email") != null ? o.get("email").toString() : null;
		
		userBean = new ASIUserBean(parsedUserAddressInfo, parsedUserNameInfo, parsedBirthdate, parsedConnection, 
				parsedIsAssociation, parsedUpdatedAt, parsedRole, parsedUserName, parsedGender, parsedUserAvtarInfo, 
				parsedId, parsedPhoneNumber, parsedMsnNick, parsedWebsite, parsedDescription, parsedIrcNick, 
				parsedUserStatusInfo, parsedEmail);
		
		return userBean;
	}
}
