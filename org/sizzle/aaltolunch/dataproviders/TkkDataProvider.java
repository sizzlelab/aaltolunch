package org.sizzle.aaltolunch.dataproviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.sizzle.aaltolunch.dataproviders.datatype.MenuItem;
import org.sizzle.aaltolunch.dataproviders.datatype.Restaurant;
import org.sizzle.aaltolunch.dataproviders.datatype.SchoolDailyMenu;
import org.sizzle.aaltolunch.dataproviders.datatype.SchoolDailyMenu.SchoolName;

/**
 * 
 * @author Nalin Chaudhary
 */
public class TkkDataProvider 
{
	private HttpClient m_httpClient = null;
	private String m_dataProviderUrl = "ruokalistat.net";
	private static final String HTTP_PORT = "80";
	private static final String HTTP_PROTOCOL_STRING = "http";
	private static final String m_dataProviderMainPage = "/";
	
	private ArrayList<Restaurant> m_restaurants = null;
	
	public TkkDataProvider()
	{
		m_httpClient = new HttpClient();
		m_restaurants = new ArrayList<Restaurant>();
	}
	
	public SchoolDailyMenu fetchData()
	{
		try {
			// Host configuration
			m_httpClient.getHostConfiguration().setHost(m_dataProviderUrl, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

			// Create a GET request for main page
			GetMethod mainPageGet = new GetMethod(m_dataProviderMainPage); 

			long t1 = System.currentTimeMillis();

			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
			
//			System.out.println("Got MAIN page. Time(ms) : " + (System.currentTimeMillis() - t1));

			String responseMainPage = mainPageGet.getResponseBodyAsString();
			
			dirtyParsing(responseMainPage);
//			System.out.println(responseMainPage);
			
			// Release connection
			mainPageGet.releaseConnection();
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		catch (HttpException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create SchoolDailyMenu object
		SchoolDailyMenu tkkDailyMenu = new SchoolDailyMenu(SchoolName.TKK); 
		tkkDailyMenu.setRestaurants(m_restaurants);
		
		// debugging
//		if (m_restaurants != null)
//		{
//			for (Restaurant r : m_restaurants)
//			{
//				System.out.println("------- " + r.getName() + " -------");
//				
//				if (r.getMenuList() != null)
//				{
//					ArrayList<MenuItem> items = r.getMenuList();
//					
//					for (MenuItem m : items)
//					{
//						System.out.println("--> " + m.getName());						
//					}
//				}
//				else
//				{
//					System.out.println("menu list is null");
//				}
//			}
//		}
		
		return tkkDailyMenu;
	}
	
	private boolean dirtyParsing(String response)
	{
		boolean done = false;
		
		if (response != null)
		{
			if (response.contains("<h3>"))
			{
				String rawData = response.substring(response.indexOf("<h3>") + (new String("<h3>")).length());
				if (rawData.contains("<h3>"))
				{
					String rawData1 = rawData.substring(0, rawData.indexOf("<h3>"));
					
					if (rawData1 != null && rawData1.trim().length() > 0)
					{
						StringTokenizer st1 = new StringTokenizer(rawData1, "\n");
						
						while (st1.hasMoreTokens())
						{
							String rawLine = st1.nextToken();
							if (rawLine.contains("class=\"ravintolat\""))
							{
//								System.out.println("RAW Restaurant name: " + rawLine);
								if (rawLine.contains("a href="))
								{
									String name = rawLine.replaceAll("\\<[^>]*>","").trim();
									if (name.contains("&auml;"))
									{
										name = name.replaceAll("&auml;", "ä");
									}
									
									String link = rawLine.substring(rawLine.indexOf("a href=") + (new String("a href=")).length() + 1);
									link = link.substring(0, link.indexOf('"'));
//									System.out.println("name : " + name + ", link : " + link);
									
									if (name != null)
									{
										Restaurant r = new Restaurant(name, link);
										m_restaurants.add(r);
									}
								}
							}
						}
						
						String rawData2 = null;
						if (rawData1.contains("<td valign=\"top\" class=\"ruokateksti\">"))
						{
							rawData1 = rawData1.replaceAll("<td valign=\"top\" class=\"ruokateksti\">", "td_valign_top_class_ruokateksti");
							rawData2 = rawData1.substring(rawData1.indexOf("td_valign_top_class_ruokateksti") + (new String("td_valign_top_class_ruokateksti")).length());
//							rawData2 = rawData1.substring(rawData1.indexOf("td_valign_top_class_ruokateksti"));
						}
						
//						System.out.println(rawData2.indexOf("class=" + '"' + "ruokateksti"));
//						System.out.println(rawData2.substring(0, rawData2.indexOf("class=" + '"' + "ruokateksti")));
						rawData2 = rawData2.substring(0, rawData2.indexOf("class=" + '"' + "ruokateksti"));
						
						HashMap<String, String> restaurantsMenus = new HashMap<String, String>();
						if (rawData2 != null)
						{
							int i = 0;
							while (rawData2.contains("td_valign_top_class_ruokateksti"))
							{
//								System.out.println("=======================================================================================================================================");
								
								String rawMenu = rawData2.substring(0, rawData2.indexOf("td_valign_top_class_ruokateksti"));
//								System.out.println("RESTAURANT ----------->" + m_restaurants.get(i).getName());

								if (rawMenu.indexOf("</td>") > 0)
								{
									rawMenu = rawMenu.substring(0, rawMenu.indexOf("</td>"));
								}
//								System.out.println(rawMenu);
//								System.out.println(rawMenu.replaceAll("\\<[^>]*>","").trim());
//								rawMenu = rawMenu.replaceAll("\\<[^>]*>","").trim();
//								System.out.println(rawMenu);
								
								rawMenu = rawMenu.replaceAll("  	", "---");
//								rawMenu = rawMenu.replaceAll("\n", "===");
//								rawMenu = rawMenu.replaceAll(System.getProperty("line.separator"), "===");
//								System.out.println(rawMenu);
								
								restaurantsMenus.put(m_restaurants.get(i).getName(), rawMenu);
								
//								restaurantsMenus.add(rawData2.substring(0, rawData2.indexOf("td_valign_top_class_ruokateksti")));
								rawData2 = rawData2.substring(rawData2.indexOf("td_valign_top_class_ruokateksti") + (new String("td_valign_top_class_ruokateksti")).length());
								i++;
							}
							
//							System.out.println("=======================================================================================================================================");
//							System.out.println("RESTAURANT ----------->" + m_restaurants.get(i).getName());
							
							if (rawData2.indexOf("</td>") > 0)
							{
								rawData2 = rawData2.substring(0, rawData2.indexOf("</td>"));
							}
							
//							rawData2 = rawData2.replaceAll("\\<[^>]*>","").trim();
//							 System.out.println(rawData2);
							 
							 restaurantsMenus.put(m_restaurants.get(i).getName(), rawData2);
							 
							 
							 // parse the data
							 done = dirtyParsingContinued(restaurantsMenus);
						}
					}
				}
			}
		}
		
		return done;
	}
	
	private boolean dirtyParsingContinued(HashMap<String, String> restaurantsMenus)
	{
		boolean done = false;
		
		Iterator<Entry<String, String>> it = restaurantsMenus.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, String> entry = it.next();

			if (entry.getKey().equals("Teekkariravintolat"))
			{
				ArrayList<MenuItem> menuList = parseTeekkariravintolat(entry.getValue());
				setRestaurantMenuItemList("Teekkariravintolat", menuList);
			}
			else if (entry.getKey().equals("Täffä"))
			{
				ArrayList<MenuItem> menuList = parseTaffa(entry.getValue());
				setRestaurantMenuItemList("Täffä", menuList);
			}
			else if (entry.getKey().equals("Alvari"))
			{
				ArrayList<MenuItem> menuList = parseAlvari(entry.getValue());
				setRestaurantMenuItemList("Alvari", menuList);
			}
			else if (entry.getKey().equals("Puu"))
			{
				ArrayList<MenuItem> menuList = parsePuu(entry.getValue());
				setRestaurantMenuItemList("Puu", menuList);
			}
			else if (entry.getKey().equals("Smökki"))
			{
				ArrayList<MenuItem> menuList = parseSmokki(entry.getValue());	
				setRestaurantMenuItemList("Smökki", menuList);
			}
			else if (entry.getKey().equals("Cantina"))
			{
				ArrayList<MenuItem> menuList = parseCantina(entry.getValue());
				setRestaurantMenuItemList("Cantina", menuList);
			}
			else if (entry.getKey().equals("TUAS-talo"))
			{
				ArrayList<MenuItem> menuList = parseTUASTalo(entry.getValue());
				setRestaurantMenuItemList("TUAS-talo", menuList);
			}
			else if (entry.getKey().equals("Silinteri"))
			{
				ArrayList<MenuItem> menuList = parseSilinteri(entry.getValue());
				setRestaurantMenuItemList("Silinteri", menuList);
			}
			else if (entry.getKey().equals("Kvarkki"))
			{
				ArrayList<MenuItem> menuList = parseKvarkki(entry.getValue());
				setRestaurantMenuItemList("Kvarkki", menuList);
			}
		}
		
		return done;
	}
	
	private ArrayList<MenuItem> parseTeekkariravintolat (String menus)
	{
//		System.out.println("parseTeekkariravintolat: menu: " + menus);
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		menus = menus.replaceAll("<br>","~").replaceAll("\\<[^>]*>","").trim();
//		System.out.println("parseTeekkariravintolat: menu: " + menus);
		if (menus.length() > 0)
		{
			menus = menus + "~" + "---";
		}
//		System.out.println("parseTeekkariravintolat: menu: " + menus);
		StringTokenizer st = new StringTokenizer(menus, "~");
		
		String menuItem = "";
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				if (token.equals("---"))  commented on 06.11.2010
				if (token.equals("---") && menuItem.trim().length() > 0)
				{
//					System.out.println("parseTeekkariravintolat: menuItem: " + menuItem);
					ret.add(new MenuItem(menuItem, ""));
					menuItem = "";
					
					continue;
				}
				
				if (menuItem.length() > 0)
				{
					menuItem = menuItem + System.getProperty("line.separator");
				}
				menuItem = menuItem + token;
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseTaffa (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseAlvari (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parsePuu (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseSmokki (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		menus = menus.replaceAll("<br>","BR_OPEN").replaceAll("\\<[^>]*>","").trim();

		if (menus.length() > 0)
		{
			menus = menus + "BR_OPEN" + "---";
		}
//		System.out.println((menus));
		StringTokenizer st = new StringTokenizer(menus, "BR_OPEN");
		
		String menuItem = "";
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				if (token.equals("---"))  commented on 06.11.2010
				if (token.equals("---") && menuItem.trim().length() > 0)
				{
					ret.add(new MenuItem(menuItem, ""));
					menuItem = "";
					
					continue;
				}
				
				if (menuItem.length() > 0)
				{
					menuItem = menuItem + System.getProperty("line.separator");
				}
				menuItem = menuItem + token;
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseCantina (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseTUASTalo (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseSilinteri (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private ArrayList<MenuItem> parseKvarkki (String menus)
	{
		ArrayList<MenuItem> ret = new ArrayList<MenuItem>();
		
		menus = menus.replaceAll("<br>","===").replaceAll("\\<[^>]*>","").trim();
		
//		System.out.println(menus);

		StringTokenizer st = new StringTokenizer(menus, "===");
		
		while (st.hasMoreTokens())
		{
			String token = st.nextToken().trim();
			if (token.length() > 0)
			{
//				System.out.println(token);
				ret.add(new MenuItem(token, ""));
			}
		}
		
//		for (MenuItem i : ret)
//		{
//			System.out.println("------------------------------");
//			System.out.println(i.getName());
//			System.out.println("------------------------------");
//		}
		
		return ret;
	}
	
	private void setRestaurantMenuItemList (String restaurantName, ArrayList<MenuItem> menuList)
	{
		if (m_restaurants != null)
		{
			for (Restaurant r : m_restaurants)
			{
				if (r.getName() != null && r.getName().equalsIgnoreCase(restaurantName))
				{
					r.setMenuList(menuList);
					
//					System.out.println("Restaurant name : " + r.getName());
					
//					for (MenuItem i : menuList)
//					{
//						System.out.println("Name: " + i.getName() + ", type: " + i.getType());
//					}
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
		TkkDataProvider p = new TkkDataProvider();
		p.fetchData();
	}
}
