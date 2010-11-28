<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
    
import="java.lang.Integer,java.util.List,java.util.ArrayList,org.sizzle.aaltolunch.asi.datatype.ASISearchedUserBean,org.sizzle.aaltolunch.asi.datatype.ASIUserBean,org.sizzle.aaltolunch.asi.datatype.ASISearchedUserBean.FriendshipStatus"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%@page import="org.sizzle.aaltolunch.asi.datatype.ASISearchedUserBean.FriendshipStatus"%>
<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>AaltoLunch - Search</title>
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
		<a class="search" href="mainPage.jsp">Back</a>
	<!-- <a class="search" href="settings.jsp">Friends</a>	 -->
		<a class="search" href="/aaltolunch/pendingRequests">Pending requests</a>
		<a class="logout" href="/aaltolunch/logout">Logout</a>
	</div>
	<div id="logo"><a href="mainPage.jsp" title="Main page"><img src="images/logo3.png"></a></div>
	<h3 class="intro">Find friends using AaltoLunch</h3>
<%
		// Added on 29 Oct 2010
		String askFriendshipFname = (String)session.getAttribute("askFriendship.fname");
		String askFriendshipResult = (String)session.getAttribute("askFriendship.result");
		
		if (askFriendshipFname != null && askFriendshipFname.length() > 0)
		{
			// Remove friendship request info
			session.removeAttribute("askFriendship.fname");
			session.removeAttribute("askFriendship.result");
			
			// remove user search info so that the users from the previous search are not shown.
			session.removeAttribute("search.results");
			session.removeAttribute("index");
		}
		
		if (askFriendshipResult != null)
		{
			if (!askFriendshipResult.equals("success"))
			{
%>
				<DIV align="center">	
					<A STYLE="font-family:Arial;font-size:10pt;color:#FF0033"> <%= askFriendshipResult %> </A>
				</DIV>
<%
			}
			else
			{
%>
				<DIV align="center">	
					<A STYLE="font-family:Arial;font-size:10pt;color:#99CC00"> Friendship request is sent to <b><%= askFriendshipFname %></b> </A>
				</DIV>
<%			
			}
		}
		
		// Added on 29 Oct 2010 - end
%>

        <form name="search" action="search">
			<TABLE align="center" border="0">
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Search by name or username:</td></tr>
				<tr><td>
					<input type="text" class="input-field" size="40" maxlength="20" name="t1" id="t1" >
					<input type=hidden name=p value="1">
				</td></tr>
				<tr><td></td></tr>
				<tr>
					<td><input type="submit" name="b1" value="search"></td>
				</tr>
			</TABLE>
        </form>
<%
		int PAGE_SIZE = 20;
		int totalResultSize = 0;
		int currentPageNumber = 0;
		String searchString = "";
		String ASI_URI = "http://cos.alpha.sizl.org/";
		
		String fromMain = request.getParameter("fromMain");
		System.out.println("fromMain: " + fromMain);
		if (fromMain != null && fromMain.equals("yes"))
		{
			session.removeAttribute("search.results");
			session.removeAttribute("page");
			session.removeAttribute("size");
			session.removeAttribute("perPage");
			session.removeAttribute("searchString");
		}
		
		// if we have the response
		List<ASISearchedUserBean> searchResults = (ArrayList<ASISearchedUserBean>) session.getAttribute("search.results");
		if (session.getAttribute("size") != null)
		{
			totalResultSize = Integer.parseInt((String)session.getAttribute("size"));
		}
		
		if (session.getAttribute("size") != null)
		{
			currentPageNumber = Integer.parseInt((String)session.getAttribute("page"));
		}
		
		if (session.getAttribute("searchString") != null)
		{
			searchString = (String)session.getAttribute("searchString");
		}
	
		session.removeAttribute("search.results");
		session.removeAttribute("page");
		session.removeAttribute("size");
		session.removeAttribute("perPage");
		session.removeAttribute("searchString");
		
		if (searchResults != null && searchResults.size() > 0)
		{
			int size = searchResults.size();
			System.out.println("Search result size: " + size);
			System.out.println("Total  result size: " + totalResultSize);
			
			int MAX_INDEX = totalResultSize/PAGE_SIZE;
			if (totalResultSize % PAGE_SIZE > 0)
			{
				MAX_INDEX = MAX_INDEX + 1;
			}

			/* start_index_of_list = index * PAGE_SIZE
			 * end_index_of_list   = (start_index_of_list + PAGE_SIZE) < (COMPLETE_LIST_SIZE - 1) ? (start_index_of_list + PAGE_SIZE) : (COMPLETE_LIST_SIZE - 1)
			 */
			/* 
			 * int start_index = index * PAGE_SIZE;
			 * int end_index = (start_index + PAGE_SIZE - 1) < (size - 1) ? (start_index + PAGE_SIZE -1) : (size - 1);
			 */
%>
			<div id="results">	
            	<h3 class="intro"><%= totalResultSize %> user(s) found.</h3>
            </div>

			<TABLE align="center" width="290" border=0><tbody>
<%	
			
			for (int i = 0; i < size; i++)
			{
				String avtarLink = "";
				String userName = "";
				
				ASISearchedUserBean aSISearchedUser = searchResults.get(i);
				ASIUserBean user = aSISearchedUser.getUserBean();
				FriendshipStatus fstatus = aSISearchedUser.getStatus();
				System.out.println("user: " + user.getUserNameInfo().getUnstructured() + ", status: " + fstatus.toString());

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
					<td width="150" VALIGN="top" style="font-family:Arial;font-size:10pt;color:black"><%= userName %></td>
					
					<!-- Commented on 29.10.2010  <td width="70" VALIGN="top" align="right"> request </td>  -->
<% 
					if (fstatus == ASISearchedUserBean.FriendshipStatus.NOT_A_FRIEND)
					{
%>
						<td width="90" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E" align="center"> <a href="/aaltolunch/friendshipRequest?t1=<%= user.getId() %>&t2=<%= userName %>">add as friend</a> </td>
<%						
					}
					else if (fstatus == ASISearchedUserBean.FriendshipStatus.FRIEND)
					{
%>
						<td width="90" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E" align="center"> your friend </td>
<%	
					}
					else if (fstatus == ASISearchedUserBean.FriendshipStatus.MYSELF)
					{
%>
						<td width="90" VALIGN="top" style="font-family:Arial;font-size:10pt;color:#2E2E2E" align="center"> you </td>
<%
					}
%>					

					
				</tr>
<%			
				}
			}
%>		
			</tbody></TABLE>
			
			<TABLE align="center" width="290" border=0><tbody>
		<!--	<tr style="background: none repeat scroll 0% 0% #dfe4ea;">  -->
			<tr style="background: none repeat scroll 0% 0% white;">
				<td width="80" VALIGN="top" align="left" STYLE="font-family:Arial;font-size:10pt;font-weight:bold;color:#153E7E"> Page: </td>
<%
			for (int j = 1; j <= MAX_INDEX; j++)
			{
				if (j == currentPageNumber)
				{
%>			
					<td width="10" VALIGN="top" align="center" STYLE="font-family:Arial;font-size:10pt;color:black"><%= j %></td>
<%					
				}
				else
				{
%>					
					<td width="10" VALIGN="top" align="center"><A HREF="/aaltolunch/search?t1=<%= searchString %>&p=<%= j %>"><%= j %></A></td>
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
			if(fromMain == null)
		 	{
%>
				<BR>
				<DIV align="center">	
					<A STYLE="font-family:Arial;font-size:11pt;color:#153E7E;">No user found.</A>
				</DIV>
<%
			}
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