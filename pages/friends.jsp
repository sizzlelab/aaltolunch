<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
    
    import="org.sizzle.aaltolunch.asi.RestHandler,org.sizzle.aaltolunch.asi.datatype.ASIUserBean,org.sizzle.aaltolunch.datatype.FriendSelection,java.util.List,org.sizzle.aaltolunch.UserSelectionHandler,java.util.Properties,java.util.ArrayList,java.util.Collections"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"; encoding="ISO-8859-1">
		<title>AaltoLunch - Friends</title>
		<link type="text/css" rel="stylesheet" href="aaltolunch.css" />
		
		<script type="text/javascript">
		
		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', 'UA-19576342-1']);
		  _gaq.push(['_trackPageview']);
		
		  (function() {
		    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();
		
		</script>
		
	</HEAD>

	<BODY>
		<div id="aaltolunch">
    	<div id="content">
    	<div id="header">
    		<a class="search" href="javascript:javascript:history.go(-1)">Back</a>
            <a class="search" href="settings.jsp">Friends</a>
            <a class="logout" href="/aaltolunch/logout">Logout</a>
        </div>
		<div id="logo"><a href="mainPage.jsp" title="Main page"><img src="images/logo3.png"></a></div>
<%
	String ASI_URI = "http://cos.alpha.sizl.org/";
	String uid = (String)session.getAttribute("uid");
	// System.out.println("mainPage.jsp: logged in user: " + uid);
	
	// Is any user user logged in?
	if (uid != null)
	{
		RestHandler handler = new RestHandler();
		handler.loginAsApplication();
		
		// Fetch the details of the logged in user
		ASIUserBean loggedInUserDetails = handler.fetchUserDetails(uid);
		
		// Fetching the friends of the logged in user
		List<ASIUserBean> friends = handler.fetchUserFriends(uid);
		
		// Find the selections of the users
		UserSelectionHandler userSelectionHandler = new UserSelectionHandler();
		userSelectionHandler.init();
		Properties userSelection = userSelectionHandler.getSelections();
		// System.out.println("userSelection : " + userSelection);
		
		// Name and avtar logged-in user
		String name = loggedInUserDetails.getUserNameInfo().getUnstructured();
		String avtarLink = ASI_URI + loggedInUserDetails.getUserAvtarInfo().getUserAvtarLink().getHref();
		
		// Place, date and time for the lunch of logged in user
		String uPlaceToEat = "";
		String uTimeToEat = "";
		String uDateToEat = "";
		String uSubHeader = "";
		// Count of friends having lunch at the same time
		int count = 0;
		
		if (userSelection != null)
		{
			Object pt = userSelection.get(uid);
			
			if (pt != null)
			{
				String placeAndTime = (String)pt;
				//System.out.println("placeAndTime: " + placeAndTime);
				if (((String)placeAndTime).contains("@") && ((String)placeAndTime).contains("#"))
				{
					uPlaceToEat = placeAndTime.substring(0, placeAndTime.lastIndexOf("@"));
					//System.out.println("uPlaceToEat: " + uPlaceToEat);
					
					// uTimeToEat = placeAndTime.substring(placeAndTime.lastIndexOf("@") + 1);  commented on 20 sep 2010
					uTimeToEat = placeAndTime.substring(placeAndTime.lastIndexOf("@") + 1, placeAndTime.lastIndexOf("#"));
					//System.out.println("uTimeToEat: " + uTimeToEat);
					
					uDateToEat = placeAndTime.substring(placeAndTime.lastIndexOf("#") + 1);
					//System.out.println("uDateToEat: " + uDateToEat);
				}
				else
				{
					uPlaceToEat = placeAndTime;
				}
				
				if (uPlaceToEat.length() > 0 && uTimeToEat.length() > 0)
				{
					uSubHeader = "Eating: " + uPlaceToEat + " at " + uTimeToEat + " on " + uDateToEat;
				}				
			}
		}
		
		// Get the selection of the friends of logged-in user
		List<FriendSelection> friendsHavingLunch = new ArrayList<FriendSelection>();
		if (friends != null && friends.size() > 0)
		{
			for (ASIUserBean friend : friends)
			{
				String placeToEat = "";
				String timeToEat = "";
				String dateToEat = "";
				String hour = "";
				String min = "";
			
				String friendUId = friend.getId();
				if (userSelection != null)
				{
					Object pt = userSelection.get(friendUId);
					
					if (pt != null)
					{
						String placeAndTime = (String)pt;
						if (((String)placeAndTime).contains("@") && ((String)placeAndTime).contains("#"))
						{
							placeToEat = placeAndTime.substring(0, placeAndTime.lastIndexOf("@"));
							timeToEat = placeAndTime.substring(placeAndTime.lastIndexOf("@") + 1, placeAndTime.lastIndexOf("#"));
							
							if (timeToEat != null && timeToEat.length() > 0 && timeToEat.contains(":"))
							{
								hour = timeToEat.substring(0, timeToEat.indexOf(":"));
								min = timeToEat.substring(timeToEat.indexOf(":") + 1);
							}
						}
						else
						{
							placeToEat = placeAndTime;
						}
						
						if ((uPlaceToEat.length() > 0 && uPlaceToEat.equalsIgnoreCase(placeToEat)) 
								&& (uTimeToEat.length() > 0 && uTimeToEat.equals(timeToEat)))
						{
							count++;
						}
						
						String friendName = friend.getUserNameInfo().getUnstructured();
						// String friendavtarLink = ASI_URI + friend.getUserAvtarInfo().getUserAvtarLink().getHref();
						String friendavtarLink = ASI_URI + "people/" + friend.getId() + "/@avatar/small_thumbnail/";
						
						if (hour.length() > 0 && min.length() > 0)
						{
							friendsHavingLunch.add(new FriendSelection(friendUId, friendName, friendavtarLink, placeToEat, timeToEat));
						}
					}       // closing of (pt != null)
				}	// closing of (userSelection != null)
			}	// closing of (ASIUserBean friend : friends)
		}	// closing of (friends != null && friends.size() > 0)

		if (friendsHavingLunch.size() > 0)
		{
			// sort the list
			Collections.sort(friendsHavingLunch);
%>			
			<BR>
			<DIV align="center">	
				<h3 class="intro">Friends</h3>
			</DIV>
			<TABLE align="center" width="290" border=0><tbody>
<%	
			for (int i = 0; i < friendsHavingLunch.size(); i++)
			{
				FriendSelection f = friendsHavingLunch.get(i);
				String tte = f.getTimeToEat();
				String h = "";
				String m = "";
				if (tte != null && tte.length() > 0 && tte.contains(":"))
				{
					h = tte.substring(0, tte.indexOf(":"));
					m = tte.substring(tte.indexOf(":") + 1);
				}
				
				boolean match = false;
				if ((uPlaceToEat.length() > 0 && uPlaceToEat.equalsIgnoreCase(f.getPlaceToEat())) 
						&& (uTimeToEat.length() > 0 && uTimeToEat.equals(f.getTimeToEat())))
				{
					match = true;
				}
				
				if (match)
				{
%>				
					<tr style="background: none repeat scroll 0% 0% #FFFF66;">
<%
				}
				else
				{
%>
					<tr style="background: none repeat scroll 0% 0% white;">
<%
				}
%>			
					<td width="50"><img class="friend-avatar" src="<%= f.getAvtarLink() %>" /></td>
					<td width="170" VALIGN="middle" style="font-size:11px;color:black;padding-left:5px;line-height:150%;"><span style="font-weight: bold"><%= f.getName() %></span><br><%= f.getPlaceToEat() + "@" + f.getTimeToEat() %></td>
<%
					if (match)
					{
%>
						<td class="eating-place" width="70" style="color:black;padding-left:5px;font-size:11px;line-height:150%" VALIGN="middle" ALIGN="center"><span>Lunch with you</span></td>
<%
					}
					else
					{
%>
						<td width="70" VALIGN="middle" align="center"> <a href="/aaltolunch/selection?sel=<%= f.getPlaceToEat() %>&hour=<%= h %>&min=<%= m %>">join</a> </td>
<%					
					}
%>
				</tr>
<%
			}	// closing for for loop
%>
			</tbody></TABLE>
<%		
			
		}      // closing of (friendsHavingLunch.size() > 0)
		else	
		{
%>
			<DIV align="center">	
				<A STYLE="font-family:Arial;font-size:10pt;color:#FF0033"></A>
			</DIV>
<%
		}
	}	// closing of (uid != null)
%>
		<div id="footer">
        	<ul class="footer-nav">
                <li>&copy; AaltoLunch 2010<li>
                <li>|</li>
                <li><a class="footer-links" href="about.html">About us</a></li>
                <li>|</li>
                <li><a class="footer-links" href="contact.html">Contact</a></li>
                <!--<li><a class="footer-links" href="www.shobbie.com/terms">Terms</a></li>-->
       		</ul>
        </div>
    </div>
    </div>
	</BODY>
</HTML>