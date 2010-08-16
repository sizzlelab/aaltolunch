package org.sizzle.aaltolunch.asi.datatype;
import org.json.simple.JSONObject;

public class ASIUserAvtarBean 
{
	private String status;
	private UserAvtarLink userAvtarLink;
	
	public ASIUserAvtarBean (String status, UserAvtarLink userAvtarLink)
	{
		this.status = status;
		this.userAvtarLink = userAvtarLink;
	}
	
	public ASIUserAvtarBean() 
	{
	}

	public String getStatus() {
		return status;
	}

	public UserAvtarLink getUserAvtarLink() {
		return userAvtarLink;
	}

	public ASIUserAvtarBean parseAvtarObject (JSONObject avtarObj)
	{
		ASIUserAvtarBean userAvtar = null;
		
		if (avtarObj != null)
		{
			String status = avtarObj.get("status") != null ? avtarObj.get("status").toString() : null;

			UserAvtarLink userAvtarLink = null;
			if (avtarObj.get("link") != null)
			{
				JSONObject avtarLinkObject = (JSONObject)avtarObj.get("link");
				
				if (avtarLinkObject != null)
				{
					String href = avtarLinkObject.get("href") != null ? avtarLinkObject.get("href").toString() : null;
					String rel = avtarLinkObject.get("rel") != null ? avtarLinkObject.get("rel").toString() : null;
					
					userAvtarLink = new UserAvtarLink(href, rel);
				}
			}
			
		
			
			
			userAvtar = new ASIUserAvtarBean (status, userAvtarLink);
		}
		
		return userAvtar;
	}
	
	public class UserAvtarLink
	{
		private String href;
		private String rel;
		
		public UserAvtarLink (String href, String rel)
		{
			this.href = href;
			this.rel = rel;
		}

		public String getHref() {
			return href;
		}

		public String getRel() {
			return rel;
		}
	}
}