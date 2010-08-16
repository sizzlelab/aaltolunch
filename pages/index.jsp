<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aalto Lunch service access</title>
	</HEAD>

	<BODY bgcolor="white">
		<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
		<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		<BR>
<%
	String loginResult = (String)session.getAttribute("loginResult");
	if (loginResult != null && loginResult.equals("failed"))
	{
%>
		<DIV align="center">	
			<A STYLE="font-family:Arial;font-size:10pt;color:#FF0033">Login failed. </A>
		</DIV>
<%
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
		<H2 align="center" STYLE="font-family:Arial;font-size:10pt;color:#2E2E2E">Login to access to AaltoLunch service</H2>
		
		<FORM name="access" action="access">
			<TABLE align="center">
				<tr>
					<!-- <td style="font-family:Arial;font-size:11pt;color:#8A4B08">username</td> -->
					<td  style="font-family:Arial;font-size:11pt;color:#2E2E2E">username</td>
					<td><input type="text" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="28" maxlength="30" name="t1" ></td>
				</tr>
					<td  style="font-family:Arial;font-size:11pt;color:#2E2E2E">password</td>
					<td><input type="password" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="28" maxlength="30" name="t2"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="b1" value="login"></td>
				</tr>
			</TABLE>		
		</FORM>
		
		<BR>
		
		<DIV align="center">	
			<A STYLE="font-family:Arial;font-size:11pt;color:#2E2E2E">New user? </A>
			<A STYLE="font-family:Arial;font-weight:bold;font-size:11pt;color:#00B0F0" href="newUser.jsp">sign-up</A>
			<A STYLE="font-family:Arial;font-size:11pt;color:#2E2E2E"> here.</A> 
		</DIV>

		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>