package org.sizzle.aaltolunch.asi.datatype;
import org.json.simple.JSONObject;


public class ASIUserNameBean 
{
	private String givenName;
	private String familyName;
	private String unstructured;
	
	public ASIUserNameBean (String given_name, String family_name, String unstructured)
	{
		this.givenName = given_name;
		this.familyName = family_name;
		this.unstructured = unstructured;
	}

	public ASIUserNameBean() 
	{
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getUnstructured() {
		return unstructured;
	}
	
	public ASIUserNameBean parseNameObject(JSONObject nameObj)
	{
		ASIUserNameBean userName = null;
		
		if (nameObj != null)
		{
			String given_name = nameObj.get("given_name") != null ? nameObj.get("given_name").toString() : null;
			String family_name = nameObj.get("family_name") != null ? nameObj.get("family_name").toString() : null;
			String unstructured = nameObj.get("unstructured") != null ? nameObj.get("unstructured").toString() : null;
			
			userName = new ASIUserNameBean (given_name, family_name, unstructured);
		}
		
		return userName;
	}
}