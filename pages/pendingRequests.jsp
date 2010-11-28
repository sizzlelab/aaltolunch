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
		<title>AaltoLunch - pending friendship requests</title>
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
		<a class="search" href="settings.jsp">Back</a>
		<a class="logout" href="/aaltolunch/logout">Logout</a>
	</div>
	<div id="logo"><a href="mainPage.jsp" title="Main page"><img src="images/logo3.png"></a></div>
	<h3 class="intro">Pending friendship requests</h3>
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
				<tr style="background: none repeat scroll 0% 0% white;">
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
					<A STYLE="font-family:Arial;font-size:10pt;color:#153E7E;">No friendship request.</A>
				</DIV>
<%
		}
%>		
		</tbody></TABLE>
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