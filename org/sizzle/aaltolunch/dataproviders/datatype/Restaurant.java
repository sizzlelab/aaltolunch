package org.sizzle.aaltolunch.dataproviders.datatype;

import java.util.ArrayList;

public class Restaurant
{
	private String name;
	private String url;
	private ArrayList<MenuItem> menuList;

	public Restaurant(String name, String url) 
	{
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
	
	public ArrayList<MenuItem> getMenuList() {
		return menuList;
	}

	public void addMenu(MenuItem menuItem) {
		this.menuList.add(menuItem);
	}

	public void setMenuList(ArrayList<MenuItem> menuList) {
		this.menuList = menuList;
	}	
}
