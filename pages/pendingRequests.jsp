<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
    
import="java.lang.Integer,java.util.List,java.util.ArrayList,org.sizzle.aaltolunch.asi.datatype.ASIUserBean"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aalto Lunch - pending friendship requests</title>
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

	<TABLE width="270" align="center" border="0">
		<tr>
			<!-- <td width="90"><A HREF="javascript:javascript:history.go(-1)">back</A></td>  -->
			<td width="70" align="left"><A HREF="settings.jsp">back</A></td>
			<td width="130" align="center"></td>
		<!-- 	<td width="70" align="right"><A HREF="index.jsp">logout</A></td> 	Commented on 06.11.2010-->
				<td width="70" align="right"><A HREF="/aaltolunch/logout">logout</A></td>
		</tr>
	</TABLE>
		
	<HR width="100%" color="#0070C0" size="1" />

	<!-- <H2 align="center" STYLE="font-family:Arial;font-size:10pt;color:#2E2E2E">Search your friend</H2> -->
			
		<TABLE align="center" border="0">
			<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
				<td align="center" STYLE="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E">Pending friendship requests</td>
								   
			</tr>
<!--
			<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
				<td align="center" style="font-family:Arial;font-size:10pt">Fields with asterisk(*) are mandatory.</td>
			</tr>
-->
		</TABLE>
		
<%
		int PAGE_SIZE = 10;
		int index = 0;
		String ASI_URI = "http://cos.alpha.sizl.org/";
		
		List<ASIUserBean> users = (ArrayList<ASIUserBean>) session.getAttribute("spr.results");
		if (request.getParameter("ix") != null)
		{
			index = Integer.parseInt(request.getParameter("ix"));
		}
		//if (session.getAttribute("spr.index") != null)
		//{
		//	index = Integer.parseInt((String)session.getAttribute("spr.index"));
		//}
		
		session.removeAttribute("spr.results");
		// session.removeAttribute("spr.index");
		
		if (session.getAttribute("handleFriendship.result") != null)
		{
			String result = (String) session.getAttribute("handleFriendship.result");
			String type = (String) session.getAttribute("handleFriendship.type");
			String fname = (String) session.getAttribute("handleFriendship.fname");

			session.removeAttribute("handleFriendship.result");
			session.removeAttribute("handleFriendship.type");
			session.removeAttribute("handleFriendship.fname");
			
			if (result.equalsIgnoreCase("success"))
			{
				if (type.equalsIgnoreCase("c"))
				{
%>
					<DIV align="center">	
						<A STYLE="font-family:Arial;font-size:10pt;color:#99CC00">Friendship with <b><%= fname %></b> confirmed.</A>
					</DIV>
<%	
				}
				else
				{
%>
					<DIV align="center">	
						<A STYLE="font-family:Arial;font-size:10pt;color:#99CC00">Friendship with <b><%= fname %></b> rejected.</A>
					</DIV>
<%
				}
			}
			else
			{
%>
				<DIV align="center">	
					<A STYLE="font-family:Arial;font-size:10pt;color:#FF0033"> <%= result %> </A>
				</DIV>
<%				
			}
		}
		
		if (users != null && users.size() > 0)
		{
			int size = users.size();
			System.out.println("Pending requests size: " + size);
			System.out.println("index                : " + index);
			
			int MAX_INDEX = size/PAGE_SIZE;
			if (size % PAGE_SIZE > 0)
			{
				MAX_INDEX = MAX_INDEX + 1;
			}

			/* start_index_of_list = index * PAGE_SIZE
			 * end_index_of_list   = (start_index_of_list + PAGE_SIZE) < (COMPLETE_LIST_SIZE - 1) ? (start_index_of_list + PAGE_SIZE) : (COMPLETE_LIST_SIZE - 1)
			 */
			int start_index = index * PAGE_SIZE;
			int end_index = (start_index + PAGE_SIZE - 1) < (size - 1) ? (start_index + PAGE_SIZE -1) : (size - 1);
%>
			<BR>
			<DIV align="center">	
				<A STYLE="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E;">Total requests: <%= size %></A>
				<BR>
				<A STYLE="font-family:Arial;font-size:10pt;color:#153E7E">(Click 'approve' for approving friendship)</A>
			</DIV>

			<TABLE align="center" width="270" border=0><tbody>
<%	
			
			for (int i = start_index; i <= end_index; i++)
			{
				String avtarLink = "";
				String userName = "";
				
				ASIUserBean user = users.get(i);

				if (user.getUserNameInfo() != null)
				{
					userName = user.getUserNameInfo().getUnstructured();
					
					if (userName == null || (userName != null && userName.length() == 0))
					{
						userName = user.getUserNameInfo().getGivenName();
						
						if (userName == null || (userName != null && userName.length() == 0))
						{
							userName = user.getUserNameInfo().getFamilyName();
						}
					}
				}
				
				if (user.getUserAvtarInfo() != null && user.getUserAvtarInfo().getUserAvtarLink() != null)
				{
					avtarLink = user.getUserAvtarInfo().getUserAvtarLink().getHref();
				}
				
				if (userName != null && userName.length() != 0)
				{
%>				
				<tr style="background: none repeat scroll 0% 0% #e8f0fa;">
					<td width="50"><img src="<%= ASI_URI + "people/" + user.getId() + "/@avatar/small_thumbnail/" %>" /></td>
					<td width="150" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= userName %></td>
					<td width="70" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E" align="center"> 
						<a href="/aaltolunch/friendshipHandler?t1=<%= user.getId() %>&t2=<%= userName %>&t3=c">confirm</a>
						<a href="/aaltolunch/friendshipHandler?t1=<%= user.getId() %>&t2=<%= userName %>&t3=r">reject</a> 
					</td>
				</tr>
<%			
				}
			}
%>		
			</tbody></TABLE>
			
			<TABLE align="center" width="270" border=0><tbody>
			<tr style="background: none repeat scroll 0% 0% #dfe4ea;">
				<td width="80" VALIGN="top" align="left" STYLE="font-family:Arial;font-size:10pt;font-weight:bold;color:#153E7E"> Page: </td>
<%
			for (int j = 0; j < MAX_INDEX; j++)
			{
				if (j == index)
				{
%>			
					<td width="10" VALIGN="top" align="center"><%= (j + 1) %></td>
<%					
				}
				else
				{
					session.setAttribute("spr.results", users);
					// session.setAttribute("spr.index", Integer.toString(j));
%>					
					<td width="10" VALIGN="top" align="center"><A HREF="pendingRequests.jsp?ix=<%= j %>"><%= (j + 1) %></A></td>
<%					
				}				
			}
%>			

			</tr>
			</tbody></TABLE>
<%
		}
		else
		{
			System.out.println("Search result: null");
%>
				<BR>
				<DIV align="center">	
					<A STYLE="font-family:Arial;font-size:11pt;color:#153E7E;">No friendship request.</A>
				</DIV>
<%
		}
%>		
		</tbody></TABLE>

		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>