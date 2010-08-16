<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"
    
    import="org.sizzle.aaltolunch.DataCollector,org.sizzle.aaltolunch.dataproviders.datatype.*,org.sizzle.aaltolunch.asi.RestHandler,org.sizzle.aaltolunch.asi.datatype.ASIUserBean,java.util.*"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<HTML>
	<HEAD>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aalto Lunch - main page</title>
	</HEAD>

	<BODY bgcolor="white">
		<H1 align="center"><img src="./images/aalto-lunch-logo-2.png" alt="http://www.aalto.fi"></H1>
		<!-- <H1 align="center"><img src="./images/aalto-logo-very-small.png" alt="http://www.aalto.fi"><img src="./images/aalto-lunch-logo-blue-small.png" alt="http://www.aalto.fi"></H1> -->
		<!-- <img src="./images/aalto-logo-stripped.png" alt="http://www.aalto.fi" align="middle" /> -->
		<!-- <img src="./images/aalto-lunch.png" repeat-x; width: 100%; /> -->
		<HR width="100%" color="#0070C0" size="1" />
		<!-- <BR> -->

		<FORM name="selection" action="selection">
		<TABLE align="center"><tbody>
<%
	String campusName = request.getParameter("c"); 
	boolean dataFound = false;
	ArrayList<SchoolDailyMenu> schoolMenus = DataCollector.getSchoolDailyMenus();
	
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
%>

		<tr style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">
			<td align="center" style="font-family:Arial;font-size:11pt;font-weight:bold;color:#153E7E;height=30px"><%= campusFullName %></td>
			<td align="right" style="font-family:Arial;font-size:10pt;color:#153E7E;height=30px" onclick="window.location.href='index.jsp'">logout</td>
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
								<td style="font-family:Arial;font-size:10pt;font-weight:bold;color:#2E2E2E"><%= rName %></td>
								<td><input type="checkbox" name="sel" value="<%= rName %>"></td>
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
										<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= menuName %></td>
										<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"><%= menuType %></td>
									</tr>
<%	
								}  // close for MenuItem loop
							}
							else
							{
%>
									<tr style="background: none repeat scroll 0% 0% rgb(239, 245, 251);">
										<td align="center" style="font-family:Arial;font-size:9pt;color:#2E2E2E">NO DATA AVAILABLE</td>
										<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"></td>
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
				<option value="15">15</option>
				<option selected="yes" value="30">30</option>
				<option value="45">45</option>
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
					<td align="center" style="font-family:Arial;font-size:9pt;color:#2E2E2E">NO DATA AVAILABLE</td>
					<td style="font-family:Arial;font-size:10pt;color:#2E2E2E"></td>
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