<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aalto Lunch service - new user</title>
	</HEAD>

	<BODY bgcolor="white">
		<H1 align="center"><img src="./images/aalto-logo-small.png" alt="http://www.aalto.fi"></H1>
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		<BR>
		<H2 align="center" STYLE="font-family:Arial;font-size:11pt;color:#2E2E2E">Create a new account</H2>
		
		<FORM name="newuser" action="newuser">
			<TABLE align="center">
				<tr>
					<td  style="font-family:Arial;font-size:11pt;color:#8A4B08">username</td>
					<td><input type="text" style="color:#8A4B08;font-family:Arial;font-weight:bold;font-size:12px;background-color:#FFCC00;" size="20" maxlength="30" name="t1" ></td>
				</tr>
					<td  style="font-family:Arial;font-size:11pt;color:#8A4B08">email</td>
					<td><input type="text" style="color:#8A4B08;font-family:Arial;font-weight:bold;font-size:12px;background-color:#FFCC00;" size="50" maxlength="40" name="t2"></td>
				</tr>
				</tr>
					<td  style="font-family:Arial;font-size:11pt;color:#8A4B08">password</td>
					<td><input type="text" style="color:#8A4B08;font-family:Arial;font-weight:bold;font-size:12px;background-color:#FFCC00;" size="8" maxlength="30" name="t3"></td>
				</tr>
				</tr>
					<td  style="font-family:Arial;font-size:11pt;color:#8A4B08">confirm password</td>
					<td><input type="text" style="color:#8A4B08;font-family:Arial;font-weight:bold;font-size:12px;background-color:#FFCC00;" size="8" maxlength="30" name="t4"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="b1" value="sign up"></td>
				</tr>
			</TABLE>		
		</FORM>
		
		<BR>

		<HR width="100%" color="#8A4B08" size="1" />	
	</BODY>
</HTML>