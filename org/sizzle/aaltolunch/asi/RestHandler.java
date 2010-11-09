package org.sizzle.aaltolunch.asi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.datatype.ASISessionBean;
import org.sizzle.aaltolunch.asi.datatype.ASIUserBean;
import org.sizzle.aaltolunch.asi.datatype.PaginationBean;
import org.sizzle.aaltolunch.utils.SearchResult;

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
//			System.out.println("loginResponse: " + loginResponse);
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
	
	public ASISessionBean loginAsUser(String userName, String password)
	{
		ASISessionBean sBean = null;
		
		try 
		{
			Form formData = new Form();
			
			formData.add("session[username]", userName);
			formData.add("session[password]", password);
			formData.add("session[app_name]", APPLICATION_NAME);
			formData.add("session[app_password]", APPLICATION_PASSWORD);
			
			WebResource webResource = m_client.resource(ASI_URI	+ RESOURCE_SESSION);
			String loginResponse = webResource.type(TYPE_FORM_URLENCODED).post(String.class, formData);
//			System.out.println("loginResponse: " + loginResponse);
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
	
	private void logout()
	{
		WebResource webResource = m_client.resource(ASI_URI + RESOURCE_SESSION);
		webResource.type("application/x-www-form-urlencoded").delete();
	}
	
	public SearchResult searchUsers(HttpServletRequest httpRequest)
	{
		SearchResult result = null;
		
		try 
		{
			String searchString = httpRequest.getParameter("t1");
			String page = httpRequest.getParameter("p");
			
			HttpSession session = httpRequest.getSession(true);
			String uid = (String) session.getAttribute("uid");
			
			System.out.println("uid         : " + uid);
			System.out.println("searchString: " + searchString);
			System.out.println("page        : " + page);

			// Login as application			
			ASISessionBean asBean = loginAsApplication();
			
			if (asBean != null)
			{
				WebResource webResource = m_client.resource(ASI_URI + "people?search=" + searchString + "&per_page=20&page=" + page);

				String response = webResource.type("application/x-www-form-urlencoded").get(String.class);
				System.out.println(response);
				
				ASIDataParser aSIDataParser = new ASIDataParser();
				List<ASIUserBean> users = aSIDataParser.parseUsers(response);
				
				PaginationBean pbean = aSIDataParser.parsePagination(response);
				
				result = new SearchResult(users, pbean);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			logout();
		}

		return result;
	}
	
	/**
	 * Returns the uid of the logged in user.
	 * @param httpRequest
	 * @return
	 */
	public ASISessionBean createNewUser(HttpServletRequest httpRequest)
	{
		ASISessionBean usb = null;
		
		try 
		{
			String userName = httpRequest.getParameter("t1");
			String firstName = httpRequest.getParameter("t2");
			String lastName = httpRequest.getParameter("t3");
			String password = httpRequest.getParameter("t4");
			String email = httpRequest.getParameter("t6");
			String consent = httpRequest.getParameter("consent");   // TODO
			
			System.out.println("New user request...");
			System.out.println("userName: " + userName);
			System.out.println("firstName: " + firstName);
			System.out.println("lastName: " + lastName);
			System.out.println("password: " + password);
			System.out.println("email: " + email);
			System.out.println("consent: " + consent);

			String consentValue = "";
			if (consent.equalsIgnoreCase("yes"))
			{
				consentValue = "EN1.0-AaltoLunch";		// As discussed with Antti Virolainen
			}
			else 
			{
				consentValue = "EN1.0-AaltoLunch-no";		// As discussed with Antti Virolainen
			}
			
			// Login as application			
			ASISessionBean asBean = loginAsApplication();
			
			if (asBean != null)
			{
				// create a new user account
				WebResource webResource1 = m_client.resource(ASI_URI + "people");
				
				Form formData1 = new Form();
				formData1.add("person[username]", userName);
				formData1.add("person[password]", password);
				formData1.add("person[email]", email);
				formData1.add("person[is_association]", "false");
				formData1.add("person[consent]", consentValue);
				
				String response1 = webResource1.type("application/x-www-form-urlencoded").post(String.class, formData1);
				System.out.println(response1);
				
				if (response1 != null && (lastName != null || firstName != null))
				{
					// update user account with First name and Last name
					WebResource webResource2 = m_client.resource(ASI_URI + "people/@me/@self");
					
					Form formData2 = new Form();
					formData2.add("person[name[family_name]]", lastName);
					formData2.add("person[name[given_name]]", firstName);
					
					String response2 = webResource2.type("application/x-www-form-urlencoded").put(String.class, formData2);
					
					System.out.println(response2);
				}
				
				// delete current session
				WebResource webResource3 = m_client.resource(ASI_URI + RESOURCE_SESSION);
				webResource3.type(TYPE_FORM_URLENCODED).delete();
				m_client.destroy();
				
				/* ******************** Login as user ************************* */
				
				// initialize the client
				init();
				
				Form formData4 = new Form();
				formData4.add("session[username]", userName);
				formData4.add("session[password]", password);
				formData4.add("session[app_name]", APPLICATION_NAME);
				formData4.add("session[app_password]", APPLICATION_PASSWORD);
				
				WebResource webResource4 = m_client.resource(ASI_URI + RESOURCE_SESSION);
				String loginResponse = webResource4.type(TYPE_FORM_URLENCODED).post(String.class, formData4);
				
				System.out.println("loginResponse: " + loginResponse);
				
				if (loginResponse != null)
				{
					ASISessionBean asb = new ASISessionBean();
					usb = asb.parseSessionResponse(loginResponse);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			logout();
		}

		return usb;
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
	
	public void askFriendship(String uName, String uPwd, String friendToBeUid) throws Exception 
	{
		// Login as user			
		ASISessionBean asBean = loginAsUser(uName, uPwd);
		
		if (asBean != null)
		{
			try 
			{
				WebResource wrAskFriendship = m_client.resource(ASI_URI + "people/@me/@friends");
				
				Form fdAskFriendship = new Form();
				fdAskFriendship.add("friend_id", friendToBeUid);
				
				String response = wrAskFriendship.type("application/x-www-form-urlencoded").post(String.class, fdAskFriendship);
				System.out.println("response: " + response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Friendship request failed.");
			}
			finally
			{
				logout();
			}
		}
		else
		{
			System.out.println("Failed to login as user!");
		}
	}
	
	/**
	 * @param uName
	 * @param uPwd
	 * @param friendToBeUid
	 * @param type				confirm (c) or reject (r) 
	 * @throws Exception
	 */
	public void handleFriendRequest(String uName, String uPwd, String friendToBeUid, String type) throws Exception 
	{
		// Login as user			
		ASISessionBean asBean = loginAsUser(uName, uPwd);
		
		if (asBean != null)
		{
			try 
			{
				if (type.equalsIgnoreCase("c"))
				{
					WebResource wrAcceptFriendsRequest = m_client.resource(ASI_URI + "people/@me/@friends");
					
					Form fdAcceptFriendsRequest = new Form();
					fdAcceptFriendsRequest.add("friend_id", friendToBeUid);
	
					System.out.println("Accepting request for : " + friendToBeUid);
					String responseAcceptFriendsRequest = wrAcceptFriendsRequest.type("application/x-www-form-urlencoded").post(String.class, fdAcceptFriendsRequest);
					System.out.println(responseAcceptFriendsRequest);
				}
				else if (type.equalsIgnoreCase("r"))
				{
					WebResource wrDeclineFriendsRequest = m_client.resource(ASI_URI + "people/@me/@friends/" + friendToBeUid);
					System.out.println("Declining request for : " + friendToBeUid);
					wrDeclineFriendsRequest.delete();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new Exception("Request failed.");
			}
			finally
			{
				logout();
			}
		}
		else
		{
			System.out.println("Failed to login as user!");
		}
	}
	
	public List<ASIUserBean> fetchPendingRequests(String uName, String uPwd)
	{
		List<ASIUserBean> users = null;
		
		// Login as user			
		ASISessionBean asBean = loginAsUser(uName, uPwd);
		
		if (asBean != null)
		{
			try 
			{
				WebResource wrPendingFriendsRequest = m_client.resource(ASI_URI + "people/@me/@pending_friend_requests");
				String response = wrPendingFriendsRequest.get(String.class);
				System.out.println("responsePendingFriendsRequest: " + response);
				
				ASIDataParser aSIDataParser = new ASIDataParser();
				users = aSIDataParser.parseUsers(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally
			{
				logout();
			}
		}
		else
		{
			System.out.println("Failed to login as user!");
		}
		
		return users;
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
//			System.out.println(responsePutDataInCollection);
			
			ret = true;
		}
		catch (Exception e) 
		{
		}
		
		return ret;
	}
}
