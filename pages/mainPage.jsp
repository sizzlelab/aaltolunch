<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
    
    import="org.sizzle.aaltolunch.asi.RestHandler,org.sizzle.aaltolunch.asi.datatype.ASIUserBean,java.util.List,org.sizzle.aaltolunch.UserSelectionHandler,java.util.Properties"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"; encoding="ISO-8859-1">
		<title>Aalto Lunch - main page</title>
	</HEAD>

	<BODY bgcolor="white">
		<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
		<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		<HR width="100%" color="#0070C0" size="1" />
		<!-- <BR> -->
<%
	String ASI_URI = "http://cos.alpha.sizl.org";
	String uid = (String)session.getAttribute("uid");
	if (uid != null)
	{
		RestHandler handler = new RestHandler();
		handler.loginAsApplication();
		
		ASIUserBean loggedInUserDetails = handler.fetchUserDetails(uid);
		List<ASIUserBean> friends = handler.fetchUserFriends(uid);
		
		UserSelectionHandler userSelectionHandler = new UserSelectionHandler();
		userSelectionHandler.init();
		Properties userSelection = userSelectionHandler.getSelections();
		System.out.println("userSelection : " + userSelection);
		
		String name = loggedInUserDetails.getUserNameInfo().getUnstructured();
		String avtarLink = ASI_URI + loggedInUserDetails.getUserAvtarInfo().getUserAvtarLink().getHref();
		String placeToEat = "";
		String timeToEat = "";
%>
		<TABLE align="center">
			<tr>
				<td></td>
				<td></td>
				<td align="right" style="font-family:Arial;font-size:10pt;color:#153E7E" onclick="window.location.href='index.jsp'">logout</td>
			</tr>
			<tr>
				<td style="font-family:Arial;font-size:11pt;color:#2E2E2E">Welcome </td>
				<td style="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E"><%= name %></td>
				<td><img src="<%= avtarLink %>" /></td>
			</tr>
		</TABLE>
<%
		if (friends != null && friends.size() > 0)
		{
%>			
			<BR>	
			<TABLE align="center"><tbody>
<%				
			for (ASIUserBean friend : friends)
			{
				String friendUId = friend.getId();
				if (userSelection != null)
				{
					Object pt = userSelection.get(friendUId);
					
					if (pt != null)
					{
						String placeAndTime = (String)pt;
						if (((String)placeAndTime).contains("@"))
						{
							placeToEat = placeAndTime.substring(0, placeAndTime.lastIndexOf("@"));
							timeToEat = placeAndTime.substring(placeAndTime.lastIndexOf("@") + 1);
						}
						else
						{
							placeToEat = placeAndTime;
						}
				
					String friendName = friend.getUserNameInfo().getUnstructured();
					String friendavtarLink = ASI_URI + friend.getUserAvtarInfo().getUserAvtarLink().getHref();

%>				
					<tr style="background: none repeat scroll 0% 0% rgb(239, 245, 251);">
					<td><img src="<%= friendavtarLink %>" /></td>
					<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= friendName %></td>
					<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= placeToEat %></td>
					<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= timeToEat %></td>
					</tr>
<%
					}
				}
			}
%>
			</tbody></TABLE>
<%			
		}
	}
	else	
	{
%>
		<DIV align="center">	
			<A STYLE="font-family:Arial;font-size:10pt;color:#FF0033"></A>
		</DIV>
<%
	}
%>
		<BR>
		<DIV align="center">	
			<A STYLE="font-family:Arial;font-size:11pt;color:#153E7E">Choose campus for lunch</A>
		</DIV>

		<FORM name="campus" action="campus.jsp">
			<TABLE align="center">
				<tr>
					<td><input type="submit" name="c" value="TKK" style="background-color:#43C6DB;color:#ffffff;height:32px;width:250px"></td>
				</tr>
				<tr>
					<td><input type="submit" name="c" value="HSE" style="background-color:#FBB917;color:#ffffff;height:32px;width:250px"></td>
				</tr>
				<tr>
					<td><input type="submit" name="c" value="TaiK" style="background-color:#C12267;color:#ffffff;height:32px;width:250px"></td>
				</tr>
			</TABLE>		
		</FORM>

		<BR>
		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>