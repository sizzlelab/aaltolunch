<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>AaltoLunch - Never eat alone!</title>
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
        
        <!--<div id="header">
        	<a class="logout" href="newUser.jsp">Sign up</a>
        </div>-->
                      
		<div id="logo"><img src="images/logo3.png"></div>
		<h3 class="intro">We make sure you don't eat alone!</h3>      
		
		<FORM id="login-form" name="access" action="access" method="post">
   	  	<p class="login-content">
            	<p class="username-text">Username:</p>
            	<p class="username-field"><input class="input-field" type="text" style="color: #333" name="t1" /></p>
       	  		<p class="passwd-text">Password:</p>
            	<p class="passwd-field"><input class="input-field" type="password" style="color: #333" name="t2" /></p>
   		  		<p class="input-button"><input class="login-button" value="Login" type="submit" name="b1" />
<%
				String loginResult = (String)session.getAttribute("loginResult");
				session.removeAttribute("loginResult");
				if (loginResult != null && loginResult.equals("failed"))
				{
%>
					<span class="login-failed">Login failed.</span>
<%
				}
%>
		</p>
		<p/>		
		</FORM>
		
		<div id="signup">
        	<p class="new-user"><span class="note">Note!</span> You can log in also with OtaSizzle account (Ossi/Kassi account).
            <p class="new-user">Not a member yet? <span class="sign-up"><a href="newUser.jsp">Sign up!</a></span></p>
        </div>
        
        <br>
		<FORM name="campus" action="campus.jsp">
			<TABLE align="center" border=0>
				<tr>
					<td><input type="submit" name="c" value="TKK" class="login-button" style="background-color:#FC0;color:#ffffff;height:32px;width:270px"></td>
				</tr>
				<tr>
					<td><input type="submit" name="c" value="HSE" class="login-button" style="background-color:#063;color:#ffffff;height:32px;width:270px"></td>
				</tr>
				<tr>
					<td><input type="submit" name="c" value="TaiK" class="login-button" style="background-color:#E60426;color:#ffffff;height:32px;width:270px"></td>
				</tr>
			</TABLE>
		</FORM>
        
        <div id="footer">
        	<ul class="footer-nav">
                <li>&copy; AaltoLunch 2010<li>
                <li>|</li>
                <li><a class="footer-links" href="about.html">About</a></li>
                <li>|</li>
                <li><a class="footer-links" href="contact.html">Contact</a></li>
                <!--<li><a class="footer-links" href="www.shobbie.com/terms">Terms</a></li>-->
       		</ul>
        </div>
        </div>
        </div>
	</BODY>
</HTML>