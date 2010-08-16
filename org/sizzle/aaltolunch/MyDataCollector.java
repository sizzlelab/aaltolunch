package org.sizzle.aaltolunch;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 
 * @author Nalin Chaudhary
 */
public class MyDataCollector  extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		
		// Set the data collection job
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DataCollectorJob(), getMorning4am(), ONCE_PER_DAY);
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
	

	private static void methodToExecute() 
	{
		// TODO you can have logic here
	}
	
	class DataCollectorJob extends TimerTask 
	{
		public DataCollectorJob() 
		{
		}

		public void run() 
		{
			System.out.println(new Date());

			try 
			{
				// TODO you can implement following method or call any public method from other class.
				methodToExecute();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
