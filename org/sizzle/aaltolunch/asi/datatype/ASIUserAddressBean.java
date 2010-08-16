package org.sizzle.aaltolunch.asi.datatype;
import org.json.simple.JSONObject;


public class ASIUserAddressBean 
{
	private String streetAddress;
	private String postalCode;
	private String locality;
	private String unstructured;
	
	public ASIUserAddressBean (String street_address, String postal_code, String locality, String unstructured)
	{
		this.streetAddress = street_address;
		this.postalCode = postal_code;
		this.locality = locality;
		this.unstructured = unstructured;
	}

	public ASIUserAddressBean() 
	{
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public String getUnstructured() {
		return unstructured;
	}
	
	public ASIUserAddressBean parseAddressObject(JSONObject addressObj)
	{
		ASIUserAddressBean userAddress = null;
		
		if (addressObj != null)
		{
			String street_address = addressObj.get("street_address") != null ? addressObj.get("street_address").toString() : null;
			String postal_code = addressObj.get("postal_code") != null ? addressObj.get("postal_code").toString() : null;
			String locality = addressObj.get("locality") != null ? addressObj.get("locality").toString() : null;
			String unstructured = addressObj.get("unstructured") != null ? addressObj.get("unstructured").toString() : null;
			
			userAddress = new ASIUserAddressBean (street_address, postal_code, locality, unstructured);
		}
		
		return userAddress;
	}
}