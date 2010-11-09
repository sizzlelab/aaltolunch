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
		<title>Aalto Lunch - all friends page</title>
		
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

	<BODY bgcolor="white">
		<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
		<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		
		<TABLE width="270" align="center">
			<tr>
				<td width="90"><A HREF="javascript:javascript:history.go(-1)">back</A></td>
				<td width="90"></td>
		<!-- 	<td width="90" align="right"><A HREF="index.jsp">logout</A></td> 	Commented on 06.11.2010-->
				<td width="90" align="right"><A HREF="/aaltolunch/logout">logout</A></td>
		<!--	<td width="90" align="right" style="font-family:Arial;font-size:10pt;color:#153E7E" onclick="window.location.href='index.jsp'">logout</td>		-->
			</tr>
		</TABLE>
		
<HR width="100%" color="#0070C0" size="1" />
		<!-- <BR> -->
<%
	String ASI_URI = "http://cos.alpha.sizl.org/";
	String uid = (String)session.getAttribute("uid");
	// System.out.println("mainPage.jsp: logged in user: " + uid);
	if (uid != null)
	{
		RestHandler handler = new RestHandler();
		handler.loginAsApplication();
		
		ASIUserBean loggedInUserDetails = handler.fetchUserDetails(uid);
		List<ASIUserBean> friends = handler.fetchUserFriends(uid);
		
		UserSelectionHandler userSelectionHandler = new UserSelectionHandler();
		userSelectionHandler.init();
		Properties userSelection = userSelectionHandler.getSelections();
		// System.out.println("userSelection : " + userSelection);
		
		String name = loggedInUserDetails.getUserNameInfo().getUnstructured();
		String avtarLink = ASI_URI + loggedInUserDetails.getUserAvtarInfo().getUserAvtarLink().getHref();
		
		String uPlaceToEat = "";
		String uTimeToEat = "";
		String uDateToEat = "";
		String uSubHeader = "";
		
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
%>
		<TABLE align="center" width="270" border=0 bgcolor=#EFF5FB>
<!--		<tr>
				<td></td>
				<td></td>
				<td align="right" style="font-family:Arial;font-size:10pt;color:#153E7E" onclick="window.location.href='index.jsp'">logout</td>
			</tr>		
-->
			<tr>
				<td width="90"><img src="<%= avtarLink %>" /></td>
			<!-- <td width="180" style="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E" VALIGN="top" ALIGN="right"><%= name + System.getProperty("line.separator") + "@" + uPlaceToEat + " at " + uTimeToEat %></td> -->
			<!-- <td width="180" style="font-family:Arial;color:#153E7E" VALIGN="top" ALIGN="right"><font size="4"><b><%= name %></b></font><br><font size="2.5"><%= uSubHeader %></font></td>	-->
			
				<td width="180" style="font-family:Arial;color:#153E7E" VALIGN="top" ALIGN="right"><font size="4"><b><%= name %></b></font><br><font size="2.5"><%= uSubHeader %></font>
				
				<% if (uSubHeader.length() > 0)
				   {
				%>
					<form name="cancelSelForm" action="selection">
					<input type="hidden" name="cancelSelection" value="yes">
					<font size="2.5">
					<A HREF="javascript: submitform()">cancel</A>
					</font>	
					</form>
					<script type="text/javascript">
						function submitform()
						{
						  document.cancelSelForm.submit();
						}
					</script>
				<%
				   } 
				%>
				</td>
					
			
			
		<!--	<td style="font-family:Arial;font-size:11pt;color:#2E2E2E">Welcome </td>
				<td style="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E"><%= name %></td>
				<td  width="90"><img src="<%= avtarLink %>" /></td>   -->
			</tr>
		</TABLE>
<%
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
				<A STYLE="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E">Complete list of friends' selections</A>
				<BR>
				<A STYLE="font-family:Arial;font-size:10pt;color:#153E7E">(Click 'join' for joining your friend)</A>
			</DIV>
			<TABLE align="center" width="270" border=0><tbody>
<%	
			int numberToShow = (friendsHavingLunch.size() > 2) ? 2 : friendsHavingLunch.size(); 
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
%>				
				<tr style="background: none repeat scroll 0% 0% #F7F8E0;">
					<td width="50"><img src="<%= f.getAvtarLink() %>" /></td>
					<td width="150" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= f.getName() %><br><%= "@" + f.getPlaceToEat() + " at " + f.getTimeToEat() %></td>
					<td width="70" VALIGN="top" align="center"> <a href="/aaltolunch/selection?sel=<%= f.getPlaceToEat() %>&hour=<%= h %>&min=<%= m %>">join</a> </td>
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
		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>