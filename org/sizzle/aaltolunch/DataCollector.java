package org.sizzle.aaltolunch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.sizzle.aaltolunch.dataproviders.HseDataProvider;
import org.sizzle.aaltolunch.dataproviders.TaikDataProvider;
import org.sizzle.aaltolunch.dataproviders.TkkDataProvider;
import org.sizzle.aaltolunch.dataproviders.datatype.SchoolDailyMenu;

/**
 * 
 * @author Nalin Chaudhary
 */
public class DataCollector  extends HttpServlet
{
	private static HseDataProvider hseDataProvider = null;
	private static TaikDataProvider taikDataProvider = null;
	private static TkkDataProvider tkkDataProvider = null;
	
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	private final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	private static ArrayList<SchoolDailyMenu> schoolDailyMenus = new ArrayList<SchoolDailyMenu>();
	
	private UserSelectionHandler userSelectionHandler = null;

	private static String dateOfLastFetch = null;
	private static String timeOfLastFetch = null;
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		System.out.println("DataCollector: init() called.");
		
		// initialize the data collector objects
		dataCollectorInit();
		
		// initialize the user selection handler
		userSelectionHandler = new UserSelectionHandler();
		userSelectionHandler.init();
		
		// Set the data collection job
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DataCollectorJob(), getMorning4am(), ONCE_PER_DAY);
	}

	private void dataCollectorInit() 
	{
		hseDataProvider = new HseDataProvider();
		taikDataProvider = new TaikDataProvider();
		tkkDataProvider = new TkkDataProvider();
	}

	private static void fetchAllDailyMenus() 
	{
        Date date = new Date();

        System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: fetching menu data...");
        
		// reset data before fetching menus
		resetData();
		
		// Actual fetching part
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching HSE data...");
		long t1 = System.currentTimeMillis();
		
		SchoolDailyMenu hseMenus = hseDataProvider.fetchData();
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching HSE data...done [" + (System.currentTimeMillis() - t1) + " ms]");
		
		if (hseMenus != null)
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " HSE SIZE: " + hseMenus.getRestaurants().size());
			schoolDailyMenus.add(hseMenus);
		}
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching TaiK data...");
		long t2 = System.currentTimeMillis();
		
		SchoolDailyMenu taikMenus = taikDataProvider.fetchData();
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching TaiK data...done [" + (System.currentTimeMillis() - t2) + " ms]");
		if (taikMenus != null)
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " TAIK SIZE: " + taikMenus.getRestaurants().size());			
			schoolDailyMenus.add(taikMenus);
		}
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching TKK data...");
		long t3 = System.currentTimeMillis();
		
		SchoolDailyMenu tkkMenus = tkkDataProvider.fetchData();
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " DataCollector: Fetching TKK data...done [" + (System.currentTimeMillis() - t3) + " ms]");
		if (tkkMenus != null)
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " TKK	 SIZE: " + tkkMenus.getRestaurants().size());
			schoolDailyMenus.add(tkkMenus);
		}
		
		// Tracking
		Date now = new Date(); 
		dateOfLastFetch = dateFormat.format(now);
		timeOfLastFetch = timeFormat.format(now);
		
		// debugging
//		if (schoolDailyMenus != null)
//		{
//			for (SchoolDailyMenu sdm : schoolDailyMenus)
//			{
//				if (sdm != null)
//				{
//					System.out.println("====================school name: " + sdm.getSchoolName());
//
//					ArrayList<Restaurant> rs = sdm.getRestaurants();
//					if (rs != null)
//					{
//						for (Restaurant r : rs)
//						{
//							System.out.println("Restaurant name : " + r.getName());
//							
//							ArrayList<MenuItem> is = r.getMenuList();
//							
//							if (is != null)
//							{
//								for (MenuItem i : is)
//								{
//									System.out.println(i.getName() + " -- " +  i.getType());
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + "DataCollector: fetching menu data...done. Took: " + (System.currentTimeMillis() - t1) + " ms.");
	}

	private static void resetData() 
	{
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " Cleaning old restaurant menu data...");
		
		
		// Re-set the local containers before fetching the menus
		hseDataProvider.resetRestaurantData();
		taikDataProvider.resetRestaurantData();
		tkkDataProvider.resetRestaurantData();
		
		// Re-set main container too
		schoolDailyMenus.clear();
		
		dateOfLastFetch = null;
		timeOfLastFetch = null;
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " Cleaning old restaurant menu data...done.");
	}
	
	private static Date getMorning4am()
	{
		// Get the Date corresponding to 04:00:00 am today
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, 4);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}
	
	public static ArrayList<SchoolDailyMenu> getSchoolDailyMenus() 
	{
		Date today = new Date();
		if (dateOfLastFetch != null && dateOfLastFetch.equals(dateFormat.format(today)))
		{
			// no need to fetch as data is fetched today
		}
		else
		{
			fetchAllDailyMenus();
		}
		
//		if (schoolDailyMenus != null)
//		{
//			for (SchoolDailyMenu sdm : schoolDailyMenus)
//			{
//				if (sdm != null)
//				{
//					System.out.println("====================school name: " + sdm.getSchoolName());
//
//					ArrayList<Restaurant> rs = sdm.getRestaurants();
//					if (rs != null)
//					{
//						for (Restaurant r : rs)
//						{
//							System.out.println("Restaurant name : " + r.getName());
//							
//							ArrayList<MenuItem> is = r.getMenuList();
//							
//							if (is != null)
//							{
//								for (MenuItem i : is)
//								{
//									System.out.println(i.getName() + " -- " +  i.getType());
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		
		return schoolDailyMenus;
	}
	
	class DataCollectorJob extends TimerTask 
	{
		public DataCollectorJob() 
		{
		}

		public void run() 
		{
			long timeOfStart = System.currentTimeMillis();
			
			System.out.println("==> " + dateTimeFormat.format(new Date()) + "*********************************** DataCollectorJob started ***********************************");

			try 
			{
				fetchAllDailyMenus();
				
				// reset all user selections
				userSelectionHandler.reset();
				
				System.out.println("==> " + dateTimeFormat.format(new Date()) + "*********************************** DataCollectorJob completed [" + (System.currentTimeMillis()- timeOfStart) + "] ***********************************");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
