package org.sizzle.aaltolunch.dataproviders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

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
public class TaikDataProvider 
{
	private HttpClient m_httpClient = null;
	private String m_dataProviderUrl = "www.taik.fi";
	private static final String HTTP_PORT = "80";
	private static final String HTTP_PROTOCOL_STRING = "http";
	private static final String HTTPS_PORT = "443";
	private static final String HTTPS_PROTOCOL_STRING = "https";
	private static final String m_dataProviderMainPage = "/tokyo/index.php/Ruokalistat";
	
	private ArrayList<Restaurant> m_restaurants = null;
	
	public TaikDataProvider()
	{
		m_httpClient = new HttpClient();
		m_restaurants = new ArrayList<Restaurant>();
	}
	
	public SchoolDailyMenu fetchData()
	{
		try {
			// Host configuration
			m_httpClient.getHostConfiguration().setHost(m_dataProviderUrl, (new Integer(HTTPS_PORT)).intValue(), HTTPS_PROTOCOL_STRING);

			// Create a GET request for main page
			GetMethod mainPageGet = new GetMethod(m_dataProviderMainPage); 

			long t1 = System.currentTimeMillis();

			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
			
//			System.out.println("Got MAIN page. Time(ms) : " + (System.currentTimeMillis() - t1));

			String responseMainPage = mainPageGet.getResponseBodyAsString();

			// Release connection
			mainPageGet.releaseConnection();
			
//			System.out.println(responseMainPage);
			if (dirtyParsing(responseMainPage))
			{
				// Kipsari
				if (fetchRestaurantLink("Kipsari") != null)
				{
					String kipsariUrl = fetchRestaurantLink("Kipsari");

					if (kipsariUrl != null && kipsariUrl.trim().length() > 0)
					{
						fetchTodaysMenuForKipsari(kipsariUrl);
					}
				}
				
				// Meccala
				if (fetchRestaurantLink("Meccala") != null)
				{
					String meccalaUrl = fetchRestaurantLink("Meccala");

					if (meccalaUrl != null && meccalaUrl.trim().length() > 0)
					{
						fetchTodaysMenuForMeccala(meccalaUrl);
					}
				}

				// Katri-Antell
				if (fetchRestaurantLink("Katri-Antell") != null)
				{
					String katriAntellUrl = fetchRestaurantLink("Katri-Antell");

					if (katriAntellUrl != null && katriAntellUrl.trim().length() > 0)
					{
						fetchTodaysMenuForKatriAntell(katriAntellUrl);
					}
				}
				
				// Ravintola Koskenranta
				if (fetchRestaurantLink("Ravintola Koskenranta") != null)
				{
					String koskenrantaUrl = fetchRestaurantLink("Ravintola Koskenranta");
					
					if (koskenrantaUrl != null && koskenrantaUrl.trim().length() > 0)
					{
						fetchTodaysMenuForKoskenranta(koskenrantaUrl);
					}
				}
				
				// remove unicafe from the list (As discussed there are many unicafe so avoiding it)
				if (m_restaurants != null)
				{
					for (Restaurant r : m_restaurants)
					{
//						System.out.println("r name : " + r.getName());
						if (r.getName() != null && r.getName().equalsIgnoreCase("Unicafe"))
						{
							m_restaurants.remove(r);
							break;
						}
					}
				}
			}
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
		SchoolDailyMenu taikDailyMenu = new SchoolDailyMenu(SchoolName.TaiK); 
		taikDailyMenu.setRestaurants(m_restaurants);
		
//		System.out.println("=========================== TAIK FINALLY ============================" + m_restaurants.size());
//		for (Restaurant r : m_restaurants)
//		{
//			if (r.getName() != null)
//			{
//				System.out.println("------------Restaurant name : " + r.getName());
//			
//				ArrayList<MenuItem> items = r.getMenuList();
//				if (items != null)
//				{
//					for (MenuItem i : items)
//					{
//						System.out.println("Name: " + i.getName() + ", type: " + i.getType());
//					}
//				}
//			}
//		}
		
		return taikDailyMenu;
	}
	
	/**
	 * Fetched the restaurants names and their links.
	 * @param response http response
	 * @return boolean on success
	 */
	private boolean dirtyParsing(String response)
	{
		boolean done = false;
		
		if (response != null)
		{
//			System.out.println(response);
			
			if (response.contains("<ul><li>") && (response.contains("</li></ul>")))
			{
				String rawData = response.substring(response.indexOf("<ul><li>") + (new String("<ul><li>")).length(), 
													response.indexOf("</li></ul>"));
				
				if (rawData != null && rawData.trim().length() > 0)
				{
					if (rawData.contains("</li><li>"))
					{
						rawData = rawData.replaceAll("</li><li>", "\n");
						
						StringTokenizer st1 = new StringTokenizer(rawData, "\n");
						
						while (st1.hasMoreTokens())
						{
							String rawLine = st1.nextToken();
							if (rawLine != null && rawLine.contains("a href="))
							{
//								System.out.println(rawData);
								
								if (rawLine.contains("nofollow"))
								{
									String name = rawLine.substring(rawLine.indexOf("nofollow") + (new String("nofollow")).length() + 2);
									name = name.substring(0, name.indexOf("</a>"));
									
									String link = rawLine.substring(rawLine.indexOf("a href=") + (new String("a href=")).length() + 1);
									link = link.substring(0, link.indexOf('"'));
//									System.out.println("name : " + name + ", link : " + link);
									
									if (name != null)
									{
										Restaurant r = new Restaurant(name, link);
										m_restaurants.add(r);
										done = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return done;
	}
	
	private String fetchRestaurantLink (String restaurantName)
	{
		String link = null;
		if (m_restaurants != null)
		{
			for (Restaurant r : m_restaurants)
			{
				if (r.getName() != null && r.getName().equalsIgnoreCase(restaurantName))
				{
					link = r.getUrl();
				}
			}
		}
		
		return link;
	}
	
	private void setRestaurantMenuItemList (String restaurantName, ArrayList<MenuItem> menuList)
	{
		if (m_restaurants != null)
		{
//			System.out.println("size : " + m_restaurants.size());
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
	
	/* ======================================== Kipsari ============================================== */
	
	private void fetchTodaysMenuForKipsari(String link)
	{
		String rawHost = link.substring(link.indexOf("://") + (new String("://")).length());
		String host = rawHost.substring(0, rawHost.indexOf("/"));
		String page = rawHost.substring(rawHost.indexOf("/"));
		
//		System.out.println("host: " + host);
//		System.out.println("page: " + page);
		
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(host, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(page); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
		
//			System.out.println("Got MAIN page. Time(ms) : "	+ (System.currentTimeMillis() - t1));
			
			String responseMainPage = mainPageGet.getResponseBodyAsString();
			
//			System.out.println(responseMainPage);
			
			dirtyParseForKipsari(responseMainPage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		// Release connection
		mainPageGet.releaseConnection();
	}

	private void dirtyParseForKipsari(String response)
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
//			dayOfWeek = "Fri";	// TODO : reomove it - testing only
//			System.out.println("===================== " + dayOfWeek);

			if (response.contains("Viikko "))
			{
				String rawMenu = response.substring(response.indexOf("Viikko "));

				if (rawMenu.indexOf("<br><br>") > 0)
				{
					rawMenu = rawMenu.substring(rawMenu.indexOf("<br><br>") + (new String("<br><br>")).length());
					
					if (rawMenu.indexOf("<br><br>") > 0)
					{
						rawMenu = rawMenu.substring(0, rawMenu.indexOf("<br><br>"));

						if (rawMenu.contains("<br><strong>"))
						{
//							rawMenu = rawMenu.replaceAll("\n", "");
							rawMenu = rawMenu.replaceAll("<br><strong>", "BR_STRONG");
//							System.out.println("rawMenu : " + rawMenu);
//							System.out.println(dayOfWeek);
//							System.out.println(getDayTagForKipsari(dayOfWeek));
							if (getDayTagForKipsari(dayOfWeek) != null && rawMenu.contains(getDayTagForKipsari(dayOfWeek)))
							{
								rawMenu = rawMenu.substring(rawMenu.indexOf(getDayTagForKipsari(dayOfWeek)));
//								System.out.println("rawMenu : " + rawMenu);
								if (rawMenu.contains("BR_STRONG"))
								{
									rawMenu = rawMenu.substring(0, rawMenu.indexOf("BR_STRONG"));
//									System.out.println("rawMenu 2 : " + rawMenu);
									if (rawMenu.indexOf("strong>") > 0)
									{
										String name = rawMenu.substring(rawMenu.indexOf("strong>") +  (new String("strong>")).length()).trim();
//										System.out.println("name : " + name);
										String type = "";			// Kipsari doesn't show type of menu
										
										MenuItem item = new MenuItem(name, type);
										ArrayList<MenuItem> list = new ArrayList<MenuItem>();
										list.add(item);
										
										setRestaurantMenuItemList ("Kipsari", list);
									}
								}else if (rawMenu.contains("Fri"))
								{
									if (rawMenu.indexOf("strong>") > 0)
									{
										String name = rawMenu.substring(rawMenu.indexOf("strong>") +  (new String("strong>")).length()).trim();
//										System.out.println("name : " + name);
										String type = "";			// Kipsari doesn't show type of menu
										
										MenuItem item = new MenuItem(name, type);
										ArrayList<MenuItem> list = new ArrayList<MenuItem>();
										list.add(item);
										
										setRestaurantMenuItemList ("Kipsari", list);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private String getDayTagForKipsari (String dayOfWeek)
	{
		String ret = null;
		
		if (dayOfWeek.equals("Mon") || dayOfWeek.equals("ma"))
		{
			ret = "Ma,Mon";
		}
		else if (dayOfWeek.equals("Tue") || dayOfWeek.equals("ti"))
		{
			ret = "Ti,Tue";
		}
		else if (dayOfWeek.equals("Wed") || dayOfWeek.equals("ke"))
		{
			ret = "Ke,Wed";
		}
		else if (dayOfWeek.equals("Thu") || dayOfWeek.equals("to"))
		{
			ret = "To,Tho";
		}
		else if (dayOfWeek.equals("Fri") || dayOfWeek.equals("pe"))
		{
			ret = "Pe,Fri";
		}
		
		return ret;
	}

	/* ======================================== Meccala ============================================== */
	
	private void fetchTodaysMenuForMeccala(String link)
	{
		String rawHost = link.substring(link.indexOf("://") + (new String("://")).length());
		String host = rawHost.substring(0, rawHost.indexOf("/"));
		String page = rawHost.substring(rawHost.indexOf("/"));
		
//		System.out.println("host: " + host);
//		System.out.println("page: " + page);
		
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(host, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(page); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
		
//			System.out.println("Got MAIN page. Time(ms) : "	+ (System.currentTimeMillis() - t1));
			
			String responseMainPage = mainPageGet.getResponseBodyAsString();
			
//			System.out.println(responseMainPage);
			
			dirtyParseForMeccala(responseMainPage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		// Release connection
		mainPageGet.releaseConnection();
	}
	
	private void dirtyParseForMeccala(String response)
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
			String dayTag = getDayTagForMeccala(dayOfWeek);
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
					
					setRestaurantMenuItemList ("Meccala", list);
				}
			}
		}
	}
	
	private String getDayTagForMeccala(String dayOfWeek)
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
	
	/* ======================================== Katri-Antell ============================================== */
	
	private void fetchTodaysMenuForKatriAntell(String link)
	{
		String rawHost = link.substring(link.indexOf("://") + (new String("://")).length());
		String host = rawHost.substring(0, rawHost.indexOf("/"));
		String page = rawHost.substring(rawHost.indexOf("/"));

//		System.out.println("host: " + host);
//		System.out.println("page: " + page);
		
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(host, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(page); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);

			String responseMainPage = mainPageGet.getResponseBodyAsString();

			// Release connection
			mainPageGet.releaseConnection();

//			System.out.println(responseMainPage);
			
			String responseFrame = null;
			if (responseMainPage.indexOf("src=") > 0)
			{
				String rawFrameUrl = responseMainPage.substring(responseMainPage.indexOf("src=") + (new String("src=")).length() + 3);
				String frameUrl = rawFrameUrl.substring(0, rawFrameUrl.indexOf('"'));
				
//				System.out.println(frameUrl);
				
				if (frameUrl != null)
				{
					// Host configuration
					m_httpClient.getHostConfiguration().setHost(host, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

					// Create a GET request for restaurant
					GetMethod frameGet = new GetMethod(frameUrl);
					
					// Send GET request
					m_httpClient.executeMethod(frameGet);
					
					responseFrame = frameGet.getResponseBodyAsString();

					// Release connection
					frameGet.releaseConnection();
					
					if (responseFrame != null && responseFrame.length() > 0)
					{
//						System.out.println("responseFrame : " + responseFrame);
					}
				}
			}
			
			if (responseFrame != null)
			{
				dirtyParseForKatriAntell(responseFrame);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void dirtyParseForKatriAntell(String response)
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("E");
		String dayOfWeek = format.format(date); 
//		System.out.println(dayOfWeek);
//		dayOfWeek = "Fri";	// TODO : reomove it - testing only
		String dayTag = getDayTagForKatriAntell(dayOfWeek);
//		System.out.println("======================================= " + dayTag);
		
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		
		if (dayTag != null && response.contains(dayTag))
		{
			String rawMenu = response.substring(response.indexOf(dayTag));
			
			if (rawMenu.contains("TBODY"))
			{
				rawMenu = rawMenu.substring(rawMenu.indexOf("TBODY") + (new String("TBODY")).length());
				if (rawMenu.contains("TBODY"))
				{
					rawMenu = rawMenu.substring(0, rawMenu.indexOf("TBODY"));
//					System.out.println(rawMenu);
					
					if (rawMenu.indexOf("<TD") > 0 && rawMenu.lastIndexOf("</TD>") > 0)
					{
						rawMenu = rawMenu.substring(rawMenu.indexOf("<TD"), rawMenu.lastIndexOf("</TD>"));
						
						
						if (rawMenu.indexOf("</TD><TD") > 0)
						{
							rawMenu = rawMenu.replaceAll("</TD><TD", "CLOSE_TD_N_OPEN_TD");
							rawMenu = rawMenu.replaceAll("\\<[^>]*>","").trim();
//							System.out.println(rawMenu);
							
							StringTokenizer st = new StringTokenizer(rawMenu, "\n");
							while (st.hasMoreTokens())
							{
								String line = st.nextToken();
								if (line.trim().length() > 0 && line.contains("CLOSE_TD_N_OPEN_TD") && !line.contains("TÄHDELLÄ MERKITYT OPISKELIJAHINTAAN"))
								{
									String rawName = line.substring(0, line.indexOf("CLOSE_TD_N_OPEN_TD"));
//									System.out.println(rawName);
									String type = "";
									String name = "";
									if (rawName.contains(" ("))
									{
										name = rawName.substring(0, rawName.indexOf(" ("));
										type = rawName.substring(rawName.indexOf(" (")).trim();
									}
									else
									{
										name = rawName;
									}

//									System.out.println("Name : " + name  + ", type: " + type);
									
									MenuItem item = new MenuItem(name, type);
									list.add(item);
								}
							}
						}
					}
				}
			}
		}
		
		setRestaurantMenuItemList ("Katri-Antell", list);
	}
	
	private String getDayTagForKatriAntell (String dayOfWeek)
	{
		String ret = null;
		
		if (dayOfWeek.equals("Mon") || dayOfWeek.equals("ma"))
		{
			ret = "MAANANTAI";
		}
		else if (dayOfWeek.equals("Tue") || dayOfWeek.equals("ti"))
		{
			ret = "TIISTAI";
		}
		else if (dayOfWeek.equals("Wed") || dayOfWeek.equals("ke"))
		{
			ret = "KESKIVIIKKO";
		}
		else if (dayOfWeek.equals("Thu") || dayOfWeek.equals("to"))
		{
			ret = "TORSTAI";
		}
		else if (dayOfWeek.equals("Fri") || dayOfWeek.equals("pe"))
		{
			ret = "PERJANTAI";
		}
		
		return ret;
	}

	/* ======================================== Ravintola Koskenranta ============================================== */
	
	private void fetchTodaysMenuForKoskenranta(String link)
	{
		String rawHost = link.substring(link.indexOf("://") + (new String("://")).length());
		String host = rawHost.substring(0, rawHost.indexOf("/"));
		String page = rawHost.substring(rawHost.indexOf("/"));
		
//		System.out.println("host: " + host);
//		System.out.println("page: " + page);
		
		// Host configuration
		m_httpClient.getHostConfiguration().setHost(host, (new Integer(HTTP_PORT)).intValue(), HTTP_PROTOCOL_STRING);

		// Create a GET request for restaurant
		GetMethod mainPageGet = new GetMethod(page); 

		try 
		{
			// Send GET request
			m_httpClient.executeMethod(mainPageGet);
		
			String responseMainPage = mainPageGet.getResponseBodyAsString();

			// Release connection
			mainPageGet.releaseConnection();
			
//			System.out.println(responseMainPage);
			
			dirtyParseForKoskenranta(responseMainPage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void dirtyParseForKoskenranta(String response)
	{
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		
		if (response != null && response.indexOf("brown_line") > 0)
		{
			
			String rawMenu = response.substring(response.indexOf("brown_line") + (new String("brown_line")).length());

			if (rawMenu.indexOf("brown_line") > 0)
			{
				rawMenu = rawMenu.substring(0, rawMenu.indexOf("brown_line"));
				
				if (rawMenu.indexOf("<li>") > 0)
				{
					rawMenu = rawMenu.substring(rawMenu.indexOf("<li>") + (new String("<li>")).length());
					
					if (rawMenu.indexOf("</li>") > 0)
					{
						rawMenu = rawMenu.substring(0, rawMenu.indexOf("</li>"));
						
						if (rawMenu .indexOf("<br />") > 0)
						{
							rawMenu = rawMenu.replaceAll("<br />", "\n");
							
							StringTokenizer st = new StringTokenizer(rawMenu, "\n");
							
							while (st.hasMoreTokens())
							{
								String rawName = st.nextToken();
								
								if (rawName.equals("Viikkosuositus"))
								{
									rawName = rawName + ": " + st.nextToken();
								}
								
								String type = "";
								String name = "";
								if ( (rawName.endsWith("G") || rawName.endsWith("VL")) && (rawName.contains(" ")))
								{
									name = rawName.substring(0, rawName.lastIndexOf(" "));
									type = rawName.substring(rawName.lastIndexOf(" ")).trim();
								}
								else
								{
									name = rawName;
								}

//								System.out.println("Menu: " + name + ", type: " + type);
								
								MenuItem item = new MenuItem(name, type);
								list.add(item);
							}
						}
					}
				}
				
			}
		}
		
		setRestaurantMenuItemList ("Ravintola Koskenranta", list);
	}
	
	public void resetRestaurantData()
	{
		m_restaurants.clear();
	}
	
	public static void main(String[] args) 
	{
		TaikDataProvider p = new TaikDataProvider();
		p.fetchData();
	}
}
