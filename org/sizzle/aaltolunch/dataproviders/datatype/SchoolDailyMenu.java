package org.sizzle.aaltolunch.dataproviders.datatype;

import java.util.ArrayList;
import java.util.Date;

public class SchoolDailyMenu 
{
	public enum SchoolName
	{
		TKK ("TKK", "School of Science and Technology"),
		TaiK ("TaiK", "School of Art and Design"),
		HSE ("HSE", "School of Economics");
		
		private final String code;
		private final String fullName;
		
		SchoolName (String code, String fullName)
		{
			this.code = code;
			this.fullName = fullName;
		}

		public String getCode() {
			return code;
		}

		public String getFullName() {
			return fullName;
		}
	}
	
	/** School name */
	private SchoolName m_schoolName;
	
	/** date */
	private Date m_date;
	
	/** List of Restaurants */
	private ArrayList<Restaurant> m_restaurants = new ArrayList<Restaurant>();
	
	/**
	 * @param schoolName
	 */
	public SchoolDailyMenu(SchoolName schoolName) 
	{
		// today's date
		m_date = new Date();
		m_schoolName = schoolName;
	}

	/**
	 * @return
	 */
	public SchoolName getSchoolName() 
	{
		return m_schoolName;
	}

	/**
	 * @return
	 */
	public Date getDate() 
	{
		return m_date;
	}

	public ArrayList<Restaurant> getRestaurants() {
		return m_restaurants;
	}
	
	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		m_restaurants = restaurants;
	}

	public void addRestaurant(Restaurant restaurant) {
		m_restaurants.add(restaurant);
	}
}
