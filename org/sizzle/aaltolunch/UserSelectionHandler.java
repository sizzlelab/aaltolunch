package org.sizzle.aaltolunch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Nalin Chaudhary
 */
public class UserSelectionHandler 
{
	private static final String USER_SELECTION_PROPERTIES = "user-selection.properties";
	
	private Properties m_properties = null; 

	public UserSelectionHandler()
	{
	}
	
	public void init()
	{
		System.out.println("UserSelectionHandler: init() called.");
		
		// Read properties file.
		m_properties = new Properties();
		try
		{ 
			System.out.println("UserSelectionHandler: init() loading properties.");
//			m_properties.load(new FileInputStream(USER_SELECTION_PROPERTIES));
			m_properties.loadFromXML(new FileInputStream(USER_SELECTION_PROPERTIES));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("UserSelectionHandler: user selection property file is not existing.");
			
			File propFile = new File(USER_SELECTION_PROPERTIES);
			try 
			{
				propFile.createNewFile();
				System.out.println("UserSelectionHandler: user selection property file created.");
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
		m_properties.put(uid, selectedRestaurant + "@" + hour + ":" + min);

		try 
		{
//			m_properties.store(new FileOutputStream(USER_SELECTION_PROPERTIES), null);
			m_properties.storeToXML(new FileOutputStream(USER_SELECTION_PROPERTIES), null, "ISO-8859-1");
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
		m_properties.clear();

		try 
		{
//			m_properties.store(new FileOutputStream(USER_SELECTION_PROPERTIES), null);
			m_properties.storeToXML(new FileOutputStream(USER_SELECTION_PROPERTIES), null, "ISO-8859-1");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
