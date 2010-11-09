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
		<title>Aalto Lunch - restaurant selection page</title>
	
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

	<BODY bgcolor="white">
	
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
	
		<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
		<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		
		<TABLE width="270" align="center">
			<tr>
				<td width="90"><A HREF="javascript:javascript:history.go(-1)">back</A></td>
				<td width="90"></td>
		<!-- 	<td width="90" align="right"><A HREF="index.jsp">logout</A></td> 	Commented on 06.11.2010-->
				<td width="90" align="right"><A HREF="/aaltolunch/logout">logout</A></td>
		<!--	<td width="90" align="right" style="font-family:Arial;font-size:10pt;color:#153E7E" onclick="window.location.href='index.jsp'">logout</td>		-->
			</tr>
		</TABLE>
		
<HR width="100%" color="#0070C0" size="1" />
		<!-- <BR> -->

		<FORM name="selection" action="selection" onsubmit="return validate_form1(this)">  
		<!-- <FORM name="selection" action="selection">  -->

		<!-- Following table is added on 06.11.2010 -->		
		<table width="315" border="0" cellpadding="0" cellspacing="0" align="center" bgcolor="#FFFFFF">
		 	<tr><td>&nbsp;</td></tr>
		 	<tr><td align="center" style="font-family:Arial;font-size:10pt;color:#FF0080"><span id="restSelection"></span></td></tr>
		 </table>
		
		<TABLE align="center"><tbody>
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

		<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
			<td align="center" style="font-family:Arial;font-size:12pt;font-weight:bold;color:#153E7E;height=60px"><%= campusFullName %></td>
<!--		<td align="right" style="font-family:Arial;font-size:10pt;color:#153E7E;height=30px" onclick="window.location.href='index.jsp'">logout</td>   -->
		</tr>
		<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
			<td align="left" style="font-family:Arial;font-size:10pt;color:#153E7E;height=60px" width="500">List of restaurants with menus for today, <%= todayDate %>. You can choose your preferred lunch place and time for today. Your selection will be visible to your friends.</td>
		</tr>
		
<!--
					<tr style="background: none repeat scroll 0% 0% rgb(67, 191, 199);">
						<td style="font-family:Arial;font-size:11pt;font-weight:bold;color:#2E2E2E"><%= campusFullName %></td>
						<td></td>
					</tr>

					<DIV align="center">	
						<A STYLE="font-family:Arial;font-weight:bold;font-size:11pt;color:#00B0F0"><%= campusFullName %></A>
					</DIV>
-->
<%					
					ArrayList<Restaurant> restaurants = sdm.getRestaurants();
					if (restaurants != null && restaurants.size() > 0)
					{
						dataFound = true;
						for (Restaurant r : restaurants)
						{
							String rName = r.getName();
%>
							<tr style="background: none repeat scroll 0% 0% rgb(67, 191, 199);">
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><label for="<%= rName %>"> <input type=RADIO name="sel" value="<%= rName %>" id="<%= rName %>">&nbsp;<%= rName %></label></td>
								<!--  <td><input type="checkbox" name="sel" value="<%= rName %>"></td>   -->
								<!--  <td><input type=RADIO name="sel" value="<%= rName %>"></td>      -->
							</tr>
<%	
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
									<tr style="background: none repeat scroll 0% 0% rgb(239, 245, 251);">
										<td align="center" style="font-family:Arial;font-size:9pt;color:#2E2E2E">Data unavailable</td>
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
					<td><input type="submit" name="done" value="Done"></td>
				</tr>
			</TABLE>
		</DIV>
<%
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
		<BR>
		<HR width="100%" color="#0070C0" size="1" />	
	</BODY>
</HTML>