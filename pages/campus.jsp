<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import="org.sizzle.aaltolunch.DataCollector,org.sizzle.aaltolunch.dataproviders.datatype.*,org.sizzle.aaltolunch.asi.RestHandler,org.sizzle.aaltolunch.asi.datatype.ASIUserBean,java.util.*,java.text.*"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>AaltoLunch - Menus</title>
		<link type="text/css" rel="stylesheet" href="aaltolunch.css" />

		<script type="text/javascript">
		function validate_required(field,alerttxt)
		{
		  with (field)
		  {
		    if (field.value==null||field.value=="")
		    {
		      alert(alerttxt);return false;
		    }
		    else
		    {
		      return true;
		    }
		  }
		}
		
		function validate_form(thisform)
		{
    	  with (thisform)
		  {
		    if (validate_required(sel,"Select one of the restaurants.")==false)
		    {
		     return false;
		    }
		  }  
		}
		</script>
		
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
	
	<script language="JavaScript">
	<!--
		function validate_form1(thisForm)
		{
		var radio_choice = false;
		
		// Loop from zero to the one minus the number of radio button selections
		for (counter = 0; counter < thisForm.sel.length; counter++)
		{
		if (thisForm.sel[counter].checked)
		radio_choice = true; 
		}
		
		if (!radio_choice)
		{
			// alert("Please select a restaurant or click 'back' to go back.")  // commented on 06.11.2010
			document.getElementById("restSelection").innerHTML='&nbsp;<span class="error">Please select a restaurant or click \'back\' to go back.</span>'
			return (false);
		}
		return (true);
		}
	-->
	</script>
	
        <div id="aaltolunch">
    	<div id="content">
		<div id="header">
<%
		if (session.getAttribute("uid") != null)
		{
%>		
        	<a class="search" href="mainPage.jsp">Back</a>
<%
		}
		else
		{
%>		
        	<a class="search" href="index.jsp">Back</a>
<%
		}

		if (session.getAttribute("uid") != null)
		{
%>
            <a class="logout" href="/aaltolunch/logout">Logout</a>
<%
		}
		else
		{
%>
            <a class="logout" href="newUser.jsp">Signup</a>
<%
		}
%>
        </div>
        <div id="logo"><a href="mainPage.jsp" title="Main page"><img src="images/logo3.png"></a></div>
		
		<FORM name="selection" action="selection" onsubmit="return validate_form1(this)">  

		<table width="315" border="0" cellpadding="0" cellspacing="0" align="center" bgcolor="#C7E9EB">
		 	<tr><td>&nbsp;</td></tr>
		 	<tr><td align="center" style="font-family:Arial;font-size:10pt;color:#FF0080"><span id="restSelection"></span></td></tr>
		 </table>
		
		<TABLE align="center"  border="0"><tbody>
<%
	String campusName = request.getParameter("c"); 
	boolean dataFound = false;
	ArrayList<SchoolDailyMenu> schoolMenus = DataCollector.getSchoolDailyMenus();
	SimpleDateFormat aFormat = new SimpleDateFormat("EEE, d MMM yyyy", new DateFormatSymbols(Locale.ENGLISH));
	if (schoolMenus != null && schoolMenus.size() > 0)
	{
		for (SchoolDailyMenu sdm : schoolMenus)
		{
			if (sdm != null)
			{
				String schoolCode = sdm.getSchoolName().getCode();
				
				if (campusName.equals(schoolCode))
				{
					String campusFullName = sdm.getSchoolName().getFullName();
					String todayDate = aFormat.format(new Date());
%>
		<h3 class="intro">Tell your friends where you eat today!</h3>

		<tr style="background:#C7E9EB;">
			<td align="center" style="font-family:Arial;font-size:12pt;font-weight:bold;color:#153E7E;height=60px"><%= campusFullName %></td>
		</tr>
		<tr style="background:#C7E9EB;">
			<td align="center" style="font-family:Arial;font-size:10pt;color:#000;height=60px" width="500"><%= todayDate %></td>
		</tr>
<%					
					ArrayList<Restaurant> restaurants = sdm.getRestaurants();
					if (restaurants != null && restaurants.size() > 0)
					{
						dataFound = true;
						for (Restaurant r : restaurants)
						{
							String rName = r.getName();
							
							if (!rName.equalsIgnoreCase("Teekkariravintolat"))
							{
%>
							<tr style="background: none repeat scroll 0% 0% rgb(67, 191, 199);">
							<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E">
								<label for="<%= rName %>">
<%
								if (session.getAttribute("uid") != null)
								{
%>
									<input type=RADIO name="sel" value="<%= rName %>" id="<%= rName %>">
<%
								}
%>
									<%= rName %>
								</label>
							</td>
							</tr>
<%	
							}
							else
							{
								if (session.getAttribute("uid") != null)
								{
%>
							<tr style="background: none repeat scroll 0% 0% rgb(67, 191, 199);">
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E">Teekkariravintolat</td>
							</tr>
							<tr style="background: none repeat scroll 0% 0% #B0E0E6;">
				<!--
					 			<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="Dipoli"> <input type=RADIO name="sel" value="Teekkariravintolat:Dipoli" id="Dipoli">&nbsp;Dipoli</label></td>
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="Sähkö"> <input type=RADIO name="sel" value="Teekkariravintolat:Sähkö" id="Sähkö">&nbsp;Sähkö</label></td>
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="Kone"> <input type=RADIO name="sel" value="Teekkariravintolat:Kone" id="Kone">&nbsp;Kone</label></td>
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="Tieto"> <input type=RADIO name="sel" value="Teekkariravintolat:Tieto" id="Tieto">&nbsp;Tieto</label></td>
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="Kasper"> <input type=RADIO name="sel" value="Teekkariravintolat:Kasper" id="Kasper">&nbsp;Kasper</label></td>
				-->
					 			<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E">
					 				<label for="Dipoli"> <input type=RADIO name="sel" value="Teekkariravintolat-Dipoli" id="Dipoli">&nbsp;Dipoli</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 				<label for="Sähkö"> <input type=RADIO name="sel" value="Teekkariravintolat-Sähkö" id="Sähkö">&nbsp;Sähkö</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<label for="Kone"> <input type=RADIO name="sel" value="Teekkariravintolat-Kone" id="Kone">&nbsp;Kone</label>
								</td>
								</tr>
								<tr style="background: none repeat scroll 0% 0% #B0E0E6;">
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E">
									<label for="Tieto"> <input type=RADIO name="sel" value="Teekkariravintolat-Tieto" id="Tieto">&nbsp;Tieto</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<label for="Kasper"> <input type=RADIO name="sel" value="Teekkariravintolat-Kasper" id="Kasper">&nbsp;Kasper</label>
								</td>
							</tr>
<%								}
								else
								{
%>
									<tr style="background: none repeat scroll 0% 0% rgb(67, 191, 199);">
										<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E">Teekkariravintolat (Dipoli, Sähkö, Kone, Tieto, Kasper)</td>
									</tr>
<%
								}
							}
							
							ArrayList<MenuItem> menuList = r.getMenuList();
							
							if (menuList != null && menuList.size() > 0)
							{
								for (MenuItem mItem : menuList)
								{
									String menuName = mItem.getName();
									String menuType = mItem.getType();
									
%>
									<tr style="background: none repeat scroll 0% 0% rgb(239, 245, 251);">
									<!--	<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= menuName %></td>
											<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= menuType %></td>		-->
											
										<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= menuName + " " + menuType %></td>
									</tr>
<%	
								}  // close for MenuItem loop
							}
							else
							{
%>
									<tr style="background: none repeat scroll 0% 0% #FFF;">
										<td align="left" style="font-family:Arial;font-size:9pt;color:#2E2E2E">Data unavailable</td>
										<!-- <td style="font-family:Arial;font-size:10pt;color:#2E2E2E"></td>  -->
									</tr>
<%
							}
						} 	// close for Restaurant loop
					}
				}   // close for if clause of campus name match
			}   // close for sdm != null
		}   // close for SchoolDailyMenu loop
	}
%>
		</tbody></TABLE>
<%
	if (dataFound)
	{
		if (session.getAttribute("uid") != null)
		{
%>
		<BR>
		<DIV align="center">
			<select name="hour">
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option selected="yes" value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
				<option value="16">16</option>
			</select>
			
			<select name="min">
				<option value="00">00</option>
				<option value="05">05</option>
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
				<option value="25">25</option>
				<option selected="yes" value="30">30</option>
				<option value="35">35</option>
				<option value="40">40</option>
				<option value="45">45</option>
				<option value="50">50</option>
				<option value="55">55</option>
			</select>
		</DIV>
		<BR>
		<DIV align="center">
			<TABLE>
				<tr>
					<td><input type="submit" name="done" class="login-button" value="Confirm"></td>
				</tr>
			</TABLE>
		</DIV>
<%
		}
	}
	else
	{
%>
		<BR>
		<DIV align="center">
			<TABLE>
				<tr style="background: none repeat scroll 0% 0% rgb(239, 245, 251);">
					<td align="center" style="font-family:Arial;font-size:9pt;color:#2E2E2E">Data unavailable</td>
					<!-- <td style="font-family:Arial;font-size:10pt;color:#2E2E2E"></td>  -->
				</tr>
			</TABLE>
<%
	}
%>
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
        
    </DIV>
    </DIV>
	</BODY>
</HTML>