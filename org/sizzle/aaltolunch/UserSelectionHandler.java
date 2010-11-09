package org.sizzle.aaltolunch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

/**
 * Class is created singleton for keeping only one copy of the user selections 
 * properties object.
 * @author Nalin Chaudhary
 */
public class UserSelectionHandler 
{
	private static Properties m_properties = null;
	
	private static final String USER_SELECTION_PROPERTIES = "user-selection.properties";
	
	private final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private final static SimpleDateFormat aFrmat = new SimpleDateFormat("EEE, d MMM yyyy", new DateFormatSymbols(Locale.ENGLISH));
	
	public UserSelectionHandler()
	{
	}
	
	public void init()
	{
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: init() called...");
		
		// Read properties file.
		m_properties = new Properties();
		try
		{ 
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: init() loading properties...");
			
//			m_properties.load(new FileInputStream(USER_SELECTION_PROPERTIES));
			m_properties.loadFromXML(new FileInputStream(USER_SELECTION_PROPERTIES));
			
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: init() loading properties...done. [Size: " + m_properties.size() + "]");
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " ERROR: UserSelectionHandler: user selection property file is not existing.");
			
			File propFile = new File(USER_SELECTION_PROPERTIES);
			try 
			{
				propFile.createNewFile();
				System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: user selection property file created.");
			} catch (IOException e){
				e.printStackTrace();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
	}
	
	/**
	 * @param uid
	 * @param selectedRestaurant
	 * @param hour
	 * @param min
	 */
	public synchronized void writeToCollection(String uid, String selectedRestaurant,
			String hour, String min) 
	{
		System.out.println("UserSelectionHandler: uid: " + uid + ", place: " + selectedRestaurant);
		
		selectedRestaurant = handleSpecialcharecters(selectedRestaurant);
		
//		m_properties.put(uid, selectedRestaurant + "@" + hour + ":" + min);	commented on 20th Sep 2010
		m_properties.put(uid, selectedRestaurant + "@" + hour + ":" + min + "#" + aFrmat.format(new Date()));

		try 
		{
//			m_properties.store(new FileOutputStream(USER_SELECTION_PROPERTIES), null);
			m_properties.storeToXML(new FileOutputStream(USER_SELECTION_PROPERTIES), null, "ISO-8859-9");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String handleSpecialcharecters(String selectedRestaurant) 
	{
		// hack
		if (selectedRestaurant.startsWith("Sm") && selectedRestaurant.endsWith("kki"))
		{
			selectedRestaurant = "Smökki";
		}
		
		if (selectedRestaurant.startsWith("T") && selectedRestaurant.contains("ff"))
		{
			selectedRestaurant = "Täffä";
		}
		
		return selectedRestaurant;
	}

	public synchronized void removeFromCollection(String uid) 
	{
		System.out.println("UserSelectionHandler:removeFromCollection uid: " + uid);
		m_properties.remove(uid);

		try 
		{
			m_properties.storeToXML(new FileOutputStream(USER_SELECTION_PROPERTIES), null, "ISO-8859-9");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Properties getSelections()
	{
		return m_properties;
	}
	
	public synchronized void reset() 
	{
		// Take backup of the user selections made in the last day ("user-selection.properties")
		try 
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: user selection backup... [Size: " + m_properties.size() + "]");
			
			m_properties.storeToXML(new FileOutputStream("user-selection" + getDateOneDayBefore() + ".properties"), null, "ISO-8859-9");
			
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: user selection backup done");
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: backup process failed. " + e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: backup process failed. " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: reset called... [Size: " + m_properties.size() + "]");
		
		m_properties.clear();

		System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: cache cleaned. [Size: " + m_properties.size() + "]");
		
		try
		{
//			m_properties.store(new FileOutputStream(USER_SELECTION_PROPERTIES), null);
			m_properties.storeToXML(new FileOutputStream(USER_SELECTION_PROPERTIES), null, "ISO-8859-9");
			
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: reset done. [Size: " + m_properties.size() + "]");
		} 
		catch (FileNotFoundException e1) {
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: reset failed. " + e1.getMessage());
			e1.printStackTrace();
		} 
		catch (IOException e2) {
			System.out.println("==> " + dateTimeFormat.format(new Date()) + " UserSelectionHandler: reset failed. " + e2.getMessage());
			e2.printStackTrace();
		}
	}
	
	private String getDateOneDayBefore()
	{
		String ret = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		
		ret = sdf.format(c.getTime());
		
		return ret;
	}
}
