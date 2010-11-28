<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>AaltoLunch - Sign up</title>
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
	
	<div id="header">
            <a class="logout" href="index.jsp">Login</a>
            <a class="search" href="index.jsp">Back</a>
	</div>
	<div id="logo"><a href="index.jsp" title="Main page"><img src="images/logo3.png"></a></div>
	<h3 class="intro">Sign up for AaltoLunch!</h3>

		<FORM onsubmit="return checkForm(this);" name="newuser" action="newuser" method="post">

		<TABLE align="center" border="0">
			 	<tr><td align="center" style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#F00"><span id="name"><span id="password1"><span id="password2"></span><span id="email"></span></td></tr>
			 	<tr><td>&nbsp;</td></tr>
			 	
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Username:*</td></tr>
				<tr><td><input type="text" class="input-field" size="40" maxlength="20" name="t1" id="t1" ></td></tr>
				
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">First name:</td></tr>
				<tr><td><input type="text" class="input-field" size="40" maxlength="100" name="t2" id="t2" ></td></tr>
				
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Last name:</td></tr>
				<tr><td><input type="text" class="input-field" size="40" maxlength="100" name="t3" id="t3" ></td></tr>
				
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Password:*</td></tr>
				<tr><td><input type="password" class="input-field" size="40" maxlength="15" name="t4" id="t4" ></td></tr>
				
				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Re-enter password:*</td></tr>
				<tr><td><input type="password" class="input-field"" size="40" maxlength="15" name="t5" id="t5" ></td></tr>

				<tr><td  style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E">Email address:*</td></tr>
				<tr><td><input type="text" class="input-field" size="40" maxlength="100" name="t6" id="t6" ></td></tr>

				<tr><td style="font-family:Verdana, Geneva, Arial, sans-serif;font-size:12px;color:#2E2E2E"><input type="checkbox" name="consent" value="yes"> I accept <a class="signup-links" href="consent.html">Consent for Research</a></td></tr>
				<tr><td></td></tr>

				<tr>
					<td><br><input type="submit" class="login-button" name="b1" value="Sign up"></td>
				</tr>
			</TABLE>		
		</FORM>
		
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