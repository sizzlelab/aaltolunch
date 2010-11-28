package org.sizzle.aaltolunch.utils;

import java.io.Serializable;
import java.util.List;

import org.sizzle.aaltolunch.asi.datatype.ASISearchedUserBean;
import org.sizzle.aaltolunch.asi.datatype.PaginationBean;

/**
 * @author Nalin Chaudhary
 */
public class SearchResult implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private List<ASISearchedUserBean> users = null;
	private PaginationBean pbean = null;
	
//	public SearchResult(List<ASIUserBean> users, PaginationBean pbean) {
	public SearchResult(List<ASISearchedUserBean> users, PaginationBean pbean) {
		super();
		this.users = users;
		this.pbean = pbean;
	}

	public List<ASISearchedUserBean> getUsers() {
		return users;
	}

	public PaginationBean getPbean() {
		return pbean;
	}
}

