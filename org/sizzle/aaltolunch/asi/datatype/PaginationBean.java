package org.sizzle.aaltolunch.asi.datatype;

import java.io.Serializable;

/**
 * @author Nalin Chaudhary
 */
public class PaginationBean implements Serializable 
{
	private String page;
	private String size;
	private String per_page;
	
	public PaginationBean(String page, String size, String perPage) 
	{
		this.page = page;
		this.size = size;
		this.per_page = perPage;
	}

	public String getPage() {
		return page;
	}

	public String getSize() {
		return size;
	}

	public String getPerPage() {
		return per_page;
	}
}
