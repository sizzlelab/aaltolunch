package org.sizzle.aaltolunch.dataproviders;

import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
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
	private String m_dataProviderUrl = "";
	private static final String HTTP_PORT = "80";
	private static final String HTTP_PROTOCOL_STRING = "http";
	private static final String HTTPS_PORT = "443";
	private static final String HTTPS_PROTOCOL_STRING = "https";
	private static final String m_dataProviderMainPage = "";
	
	private ArrayList<Restaurant> m_restaurants = null;
	
	public HseDataProvider()
	{
		m_httpClient = new HttpClient();
		m_restaurants = new ArrayList<Restaurant>();
	}
	
	public SchoolDailyMenu fetchData()
	{
	
		// Create SchoolDailyMenu object
		SchoolDailyMenu hseDailyMenu = new SchoolDailyMenu(SchoolName.HSE); 
		hseDailyMenu.setRestaurants(m_restaurants);
		
		return hseDailyMenu;
	}
	
	public void resetRestaurantData()
	{
		m_restaurants.clear();
	}
	
	public static void main(String[] args) 
	{
	}
}
