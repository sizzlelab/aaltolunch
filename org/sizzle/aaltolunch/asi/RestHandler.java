package org.sizzle.aaltolunch.asi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sizzle.aaltolunch.asi.datatype.ASISessionBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserBean;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

/**
 * 
 * @author Nalin Chaudhary
 */
public class RestHandler 
{
	private static final String TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
	private static final String APPLICATION_PASSWORD = "osnaPF1486EO";
	private static final String APPLICATION_NAME = "gizzle";

	/** Rest client */
	ApacheHttpClient m_client = null;
	
	/** Aalto Social API url */
	private static final String ASI_URI = "http://cos.alpha.sizl.org/";
	
	/** Property to enable the handling of cookies in client */
	private static final String CLIENT_HANDLE_COOKIES_PROPERTY = "com.sun.jersey.impl.client.httpclient.handleCookies";

	/** Resource session */
	private static final String RESOURCE_SESSION = "session";
	
	/** Container for keeping the user's selection */
	private static final String CONTAINER_NAME = "GIZZLE_USERS_SELECTION";
	private static final String CONTAINER_ID = "buesoUvImr35XQaaWPEYjL";
	
	public RestHandler()
	{
		init();
	}
	
	private void init()
	{
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();

		Map<String, Object> props = config.getProperties();
		props.put(CLIENT_HANDLE_COOKIES_PROPERTY, true);

		m_client = ApacheHttpClient.create(config);
	}
	
	/**
	 * Returns the uid of the logged in user.
	 * @param httpRequest
	 * @return
	 */
	public ASISessionBean loginAsUser(HttpServletRequest httpRequest)
	{
		ASISessionBean sBean = null;
		
		try 
		{
			String userName = httpRequest.getParameter("t1");
			String password = httpRequest.getParameter("t2");
			
			Form formData = new Form();
			
			formData.add("session[username]", userName);
			formData.add("session[password]", password);
			formData.add("session[app_name]", APPLICATION_NAME);
			formData.add("session[app_password]", APPLICATION_PASSWORD);
			
			WebResource webResource = m_client.resource(ASI_URI	+ RESOURCE_SESSION);
			String loginResponse = webResource.type(TYPE_FORM_URLENCODED).post(String.class, formData);
			
			if (loginResponse != null)
			{
				ASISessionBean asb = new ASISessionBean();
				sBean = asb.parseSessionResponse(loginResponse);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return sBean;
	}
	
	public ASISessionBean loginAsApplication() 
	{
		ASISessionBean sBean = null;
		try 
		{
			Form formData = new Form();
			formData.add("session[app_name]", APPLICATION_NAME);
			formData.add("session[app_password]", APPLICATION_PASSWORD);
			
			WebResource webResource = m_client.resource(ASI_URI + RESOURCE_SESSION);
			String loginResponse = webResource.type(TYPE_FORM_URLENCODED).post(String.class, formData);
			
			if (loginResponse != null)
			{
				ASISessionBean asb = new ASISessionBean();
				sBean = asb.parseSessionResponse(loginResponse);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return sBean;
	}
	
	public ASIUserBean fetchUserDetails(String uid)
	{
		ASIUserBean b = null;
		try 
		{
			WebResource webResource = m_client.resource(ASI_URI + "people/" + uid + "/@self");
			String response = webResource.get(String.class);
		
			ASIDataParser aSIDataParser = new ASIDataParser();
			b = aSIDataParser.parseUser(response);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return b;
	}
	
	public List<ASIUserBean> fetchUserFriends(String uid)
	{
		List<ASIUserBean> friends = null;
		try 
		{
			WebResource webResource = m_client.resource(ASI_URI + "people/" + uid + "/@friends");
			String response = webResource.get(String.class);
		
			ASIDataParser aSIDataParser = new ASIDataParser();
			friends = aSIDataParser.parseUsers(response);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return friends;
	}
	
	public boolean putDataInCollection(String appId, String uid, String restaurantName, String time) 
	{
		boolean ret = false;
		try
		{
			WebResource wrPutDataInCollection = m_client.resource(ASI_URI + "appdata/" + appId + "/@collections/" + CONTAINER_ID);
	
			Form fdPutDataInCollection = new Form();
			fdPutDataInCollection.add("item[content_type]", "text/*");
			fdPutDataInCollection.add("item[body]", uid + "=" + restaurantName + ";;" + time);
	
			String responsePutDataInCollection = wrPutDataInCollection.type("application/x-www-form-urlencoded").post(String.class, fdPutDataInCollection);
			System.out.println(responsePutDataInCollection);
			
			ret = true;
		}
		catch (Exception e) 
		{
		}
		
		return ret;
	}
}
