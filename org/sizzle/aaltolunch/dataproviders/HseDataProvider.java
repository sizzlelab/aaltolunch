package org.sizzle.aaltolunch.dataproviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.sizzle.aaltolunch.dataproviders.datatype.MenuItem;
import org.sizzle.aaltolunch.dataproviders.datatype.Restaurant;
import org.sizzle.aaltolunch.dataproviders.datatype.SchoolDailyMenu;
import org.sizzle.aaltolunch.dataproviders.datatype.SchoolDailyMenu.SchoolName;

/**
 * 
 * @author Nalin Chaudhary
 */
public class HseDataProvider 
{
	private HttpClient m_httpClient = null;
	private String m_dataProviderHost = "www.amica.fi";
	private static final String HTTP_PORT = "80";
	private static final String HTTP_PROTOCOL_STRING = "http";
	private static final String HTTPS_PORT = "443";
	private static final String HTTPS_PROTOCOL_STRING = "https";
	private static final String m_raflaMainPage = "/rafla#Ruokalista";
	private static final String m_chydeniaMainPage = "/chydenia#Ruokalista";
	
	private ArrayList<Restaurant> m_restaurants = null;
	
	public HseDataProvider()
	{
		m_httpClient = new HttpClient();
		m_restaurants = new ArrayList<Restaurant>();
		
		// As we know the link for the restaurants
		// Adding rafla
		Restaurant rafla = new Restaurant("Rafla", "www.amica.fi/rafla#Ruokalista");
		m_restaurants.add(rafla);
		
		// Adding chydenia
		Restaurant chydenia = new Restaurant("Chydenia", "www.amica.fi/chydenia#Ruokalista");
		m_restaurants.add(chydenia);
	}
	
	public SchoolDailyMenu fetchData()
	{
		m_restaurants = new ArrayList<Restaurant>();
		
		// As we know the link for the restaurants
		// Adding rafla
		Restaurant rafla = new Restaurant("Rafla", "www.amica.fi/rafla#Ruokalista");
		m_restaurants.add(rafla);
		
		// Adding chydenia
		Restaurant chydenia = new Restaurant("Chydenia", "www.amica.fi/chydenia#Ruokalista");
		m_restaurants.add(chydenia);
		
		
		
		// Fetch menu for Rafla
		fetchTodaysMenuForRafla();
		
		// Fetch menu for Chydenia
		fetchTodaysMenuForChydenia();
		
		
		// Create SchoolDailyMenu object
		SchoolDailyMenu hseDailyMenu = new SchoolDailyMenu(SchoolName.HSE); 
		hseDailyMenu.setRestaurants(m_restaurants);
		
//		System.out.println("=========================== HSE FINALLY ============================" + m_restaurants.size());
		for (Restaurant r : m_restaurants)
		{
			if (r.getName() != null)
			{
//				System.out.println("------------Restaurant name : " + r.getName());
			
				ArrayList<MenuItem> items = r.getMenuList();
				if (items != null)
				{
					for (MenuItem i : items)
					{
//						System.out.println("Name: " + i.getName() + ", type: " + i.getType());
					}
				}
			}
		}
		
		return hseDailyMenu;
	}
	
	/* ======================================== Rafla ============================================== */
	
	private void fetchTodaysMenuForRafla()
	{
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(m_dataProviderHost, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(m_raflaMainPage); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
		
//			System.out.println("Got MAIN page. Time(ms) : "	+ (System.currentTimeMillis() - t1));
			
			String responseMainPage = mainPageGet.getResponseBodyAsString();
			
//			System.out.println(responseMainPage);
			
			dirtyParseForRafla(responseMainPage);
			
//			System.out.println("========================Rafla================================Done");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		// Release connection
		mainPageGet.releaseConnection();
	}
	
	private void dirtyParseForRafla(String response)
	{
		if (response != null)
		{
			// Current week number
//			Calendar now = Calendar.getInstance();
//			int weekNumber = now.get(Calendar.WEEK_OF_YEAR);
			
			/* Nalin Chaudhary
			 * Java is returning different week number than that is on the site.
			 * So, no check on the week number.
			 */
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("E");
			String dayOfWeek = format.format(date); 
//			System.out.println(dayOfWeek);
//			dayOfWeek = "Fri";								// TODO : reomove it - testing only
			String dayTag = getDayTagForRafla(dayOfWeek);
//			System.out.println("======================================= " + dayTag);

			if (response.contains("<tbody>"))
			{
				String rawMenu = response.substring(response.indexOf("<tbody>"), response.indexOf("</tbody>"));
//				System.out.println(rawMenu);
				
				if (rawMenu.contains("<p><strong>" + dayTag))
				{
					rawMenu = rawMenu.substring(rawMenu.indexOf("<p><strong>" + dayTag) + (new String("<p><strong>" + dayTag)).length());
//					System.out.println(rawMenu);
					
					if (rawMenu.contains("<p><strong>"))
					{
						rawMenu = rawMenu.substring(0, rawMenu.indexOf("<p><strong>"));
					}
					
//					System.out.println(rawMenu.replaceAll("\\<[^>]*>","").trim());
//					System.out.println(rawMenu);
					
					StringTokenizer st = new StringTokenizer(rawMenu, "\n");
					ArrayList<MenuItem> list = new ArrayList<MenuItem>();
					String type = "";
					while (st.hasMoreTokens())
					{
						String rawLine = st.nextToken();
//						System.out.println("--> " + rawLine);
						if (rawLine.contains("<p>") && !rawLine.contains("<p>&nbsp;</p>"))
						{
							String name = rawLine.replaceAll("\\<[^>]*>","").trim();
							if (name.contains("&auml;"))
							{
								name = name.replaceAll("&auml;", "ä");
							}
//							System.out.println("--> " + name);
							
							MenuItem item = new MenuItem(name, type);
							list.add(item);
						}
					}
					
					setRestaurantMenuItemList ("Rafla", list);
				}
			}
		}
	}
	
	private String getDayTagForRafla(String dayOfWeek)
	{
		String ret = null;
		
		if (dayOfWeek.equals("Mon") || dayOfWeek.equals("ma"))
		{
			ret = "Maanantai";
		}
		else if (dayOfWeek.equals("Tue") || dayOfWeek.equals("ti"))
		{
			ret = "Tiistai";
		}
		else if (dayOfWeek.equals("Wed") || dayOfWeek.equals("ke"))
		{
			ret = "Keskiviikko";
		}
		else if (dayOfWeek.equals("Thu") || dayOfWeek.equals("to"))
		{
			ret = "Torstai";
		}
		else if (dayOfWeek.equals("Fri") || dayOfWeek.equals("pe"))
		{
			ret = "Perjantai";
		}
		
		return ret;
	}
	
	/* ======================================== Chydenia ============================================== */
	
	private void fetchTodaysMenuForChydenia()
	{
//		System.out.println("========================Chydenia================================");
		
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(m_dataProviderHost, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(m_chydeniaMainPage); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
		
//			System.out.println("Got MAIN page. Time(ms) : "	+ (System.currentTimeMillis() - t1));
			
			String responseMainPage = mainPageGet.getResponseBodyAsString();
			
//			System.out.println(responseMainPage);
			
			dirtyParseForChydenia(responseMainPage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		// Release connection
		mainPageGet.releaseConnection();
		
//		System.out.println("========================Chydenia================================DONE");
	}
	
	private void dirtyParseForChydenia(String response)
	{
		if (response != null)
		{
			// Current week number
//			Calendar now = Calendar.getInstance();
//			int weekNumber = now.get(Calendar.WEEK_OF_YEAR);
			
			/* Nalin Chaudhary
			 * Java is returning different week number than that is on the site.
			 * So, no check on the week number.
			 */
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("E");
			String dayOfWeek = format.format(date); 
//			System.out.println(dayOfWeek);
//			dayOfWeek = "Fri";								// TODO : reomove it - testing only
			String dayTag = getDayTagForChydenia(dayOfWeek);
//			System.out.println("HSE... day = " + dayTag);

			if (dayTag != null && response.contains("Maanantai"))
			{
				String rawMenu = response.substring(response.indexOf("Maanantai"));
//				System.out.println(rawMenu);
				
				if (rawMenu.contains("div>"))
				{
					rawMenu = rawMenu.substring(0, rawMenu.indexOf("div>") - 2).trim();
//					System.out.println(rawMenu);
					if (rawMenu.contains("<p><strong>"))
					{
						rawMenu = rawMenu.substring(0, rawMenu.indexOf("<p><strong>"));
					}
					
					rawMenu = rawMenu.replaceAll("\\<[^>]*>","").trim();
					
//					System.out.println(rawMenu);
					
//					rawMenu = rawMenu.replaceAll(System.getProperty("line.separator"), "");
					rawMenu = rawMenu.replaceAll("\\r\\n|\\r|\\n", "");
//					System.out.println("--------------------------------------------------------");
//					System.out.println(rawMenu);
//					System.out.println("--------------------------------------------------------");
					
					if (rawMenu.contains("&nbsp;&nbsp;"))
					{
//						System.out.println("yes");
						rawMenu = rawMenu.replaceAll("&nbsp;&nbsp;", "~");
						
//						System.out.println(rawMenu);						

						if (rawMenu.contains(dayTag))
						{
							rawMenu = rawMenu.substring(rawMenu.indexOf(dayTag) + dayTag.length());
							
							if (rawMenu.contains("~") && (dayTag != "Perjantai"))
							{
								rawMenu = rawMenu.substring(0, rawMenu.indexOf("~"));
							}
							
//							System.out.println(rawMenu);

							if (rawMenu.startsWith("&nbsp;"))
							{
								rawMenu = rawMenu.substring((new String("&nbsp;")).length());
							}

//							System.out.println(rawMenu);
							
							rawMenu = rawMenu.replaceAll("&nbsp;", "~");
							
//							System.out.println(rawMenu);
							StringTokenizer st = new StringTokenizer(rawMenu, "~");
							ArrayList<MenuItem> list = new ArrayList<MenuItem>();
							String type = "";
							while (st.hasMoreTokens())
							{
								String name = st.nextToken();
//								System.out.println("name: " + name);
								if (name.contains("&auml;"))
								{
									name = name.replaceAll("&auml;", "ä");
								}

//								System.out.println("--> " + name);								
								
								MenuItem item = new MenuItem(name, type);
								list.add(item);
							}
							
							setRestaurantMenuItemList ("Chydenia", list);
							
						}
					
					}
				}
			}
		}
	}
	
	private String getDayTagForChydenia(String dayOfWeek)
	{
		String ret = null;
		
		if (dayOfWeek.equals("Mon") || dayOfWeek.equals("ma"))
		{
			ret = "Maanantai";
		}
		else if (dayOfWeek.equals("Tue") || dayOfWeek.equals("ti"))
		{
			ret = "Tiistai";
		}
		else if (dayOfWeek.equals("Wed") || dayOfWeek.equals("ke"))
		{
			ret = "Keskiviikko";
		}
		else if (dayOfWeek.equals("Thu") || dayOfWeek.equals("to"))
		{
			ret = "Torstai";
		}
		else if (dayOfWeek.equals("Fri") || dayOfWeek.equals("pe"))
		{
			ret = "Perjantai";
		}
		
		return ret;
	}
	
	
	private void setRestaurantMenuItemList (String restaurantName, ArrayList<MenuItem> menuList)
	{
		if (m_restaurants != null)
		{
//			System.out.println("setRestaurantMenuItemList(): size : " + m_restaurants.size());
			for (Restaurant r : m_restaurants)
			{
				if (r.getName() != null && r.getName().equalsIgnoreCase(restaurantName))
				{
//					System.out.println("----------------------------------------------------Restaurant name : " + r.getName());
					r.setMenuList(menuList);
					
					for (MenuItem i : menuList)
					{
//						System.out.println("Name: " + i.getName() + ", type: " + i.getType());
					}
					
					break;
				}
			}
		}
	}
	
	public void resetRestaurantData()
	{
		m_restaurants.clear();
	}
	
	public static void main(String[] args) 
	{
		HseDataProvider p = new HseDataProvider();
		p.fetchData();
	}
}
