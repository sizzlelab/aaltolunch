<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aalto Lunch - create new user account</title>
		
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
	
	<script language="JavaScript">
		function checkForm() 
		{
			var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		
			var t1 = document.getElementById("t1").value;
			var t4 = document.getElementById("t4").value;
			var t5 = document.getElementById("t5").value;
			var t6 = document.getElementById("t6").value;
			
			if (t1.length <= 0) 
			{
				document.getElementById("name").innerHTML='&nbsp;<span class="error">Please enter a use name.</span>'
		  		return false;
		  	}
		  	else if (t4.length <= 0)
		  	{
		  		document.getElementById("password1").innerHTML='&nbsp;<span class="error">Please enter a password.</span>'
		  		return false;
		  	}
		  	else if (t4 != t5)
		  	{
		  		document.getElementById("password2").innerHTML='&nbsp;<span class="error">Passwords are not matching.</span>'
		  		return false;		  	
		  	}
		  	else if (reg.test(t6) == false)
		  	{
		  		document.getElementById("email").innerHTML='&nbsp;<span class="error">Email address is not valid.</span>'
		  		return false;		  	
		  	}
		  	
		  	return true;
		}
	</script>
	
	<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
	<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
	<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
	<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
	<BR>

	<!-- <H2 align="center" STYLE="font-family:Arial;font-size:10pt;color:#2E2E2E">Create new account for AaltoLunch service</H2> -->
			
		<TABLE align="center" border="0">
			<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
				<td align="left" style="font-family:Arial;font-size:10pt;font-weight:bold">Create new account for AaltoLunch service</td>
			</tr>
			<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
				<td align="center" style="font-family:Arial;font-size:10pt">Fields with asterisk(*) are mandatory.</td>
			</tr>
		</TABLE>
		
		<FORM onsubmit="return checkForm(this);" name="newuser" action="newuser" method="post">
		
			<table width="315" border="0" cellpadding="0" cellspacing="0" align="center" bgcolor="#FFFFFF">
			 	<tr><td>&nbsp;</td></tr>
			 	<tr><td align="center" style="font-family:Arial;font-size:10pt;color:#FF0080"><span id="name"><span id="password1"><span id="password2"></span><span id="email"></span></td></tr>
			 </table>
		
			<TABLE align="center" border="0">
				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">User name*</td></tr>
				<tr><td><input type="text" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="20" name="t1" id="t1" ></td></tr>
				
				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">First name</td></tr>
				<tr><td><input type="text" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="100" name="t2" id="t2" ></td></tr>
				
				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">Last name</td></tr>
				<tr><td><input type="text" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="100" name="t3" id="t3" ></td></tr>
				
				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">password*</td></tr>
				<tr><td><input type="password" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="15" name="t4" id="t4" ></td></tr>
				
				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">Re-enter password*</td></tr>
				<tr><td><input type="password" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="15" name="t5" id="t5" ></td></tr>

				<tr><td  style="font-family:Arial;font-size:10pt;color:#2E2E2E">Email*</td></tr>
				<tr><td><input type="text" style="color:#00B0F0;font-family:Arial;font-weight:bold;font-size:12px;background-color:#EFF5FB;" size="40" maxlength="100" name="t6" id="t6" ></td></tr>

				<tr><td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><input type="checkbox" name="consent" value="yes"> I accept the Research consent</td></tr>
				<tr><td></td></tr>

				<tr>
					<td><input type="submit" name="b1" value="sign up"></td>
				</tr>
			</TABLE>		
		</FORM>
		
		<BR>

		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>