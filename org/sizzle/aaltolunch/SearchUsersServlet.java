package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.RestHandler;
import org.sizzle.aaltolunch.utils.SearchResult;

/**
 * 
 * @author Nalin Chaudhary
 */
public class SearchUsersServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		String searchString = request.getParameter("t1");
		System.out.println("SearchUsersServlet: searchString: " + searchString);
		RestHandler handler = new RestHandler();
		SearchResult result = handler.searchUsers(request);
		
		session.setAttribute("search.results", result.getUsers());
		session.setAttribute("page", result.getPbean().getPage());
		session.setAttribute("size", result.getPbean().getSize());
		session.setAttribute("perPage", result.getPbean().getPerPage());
		session.setAttribute("searchString", searchString);
		
		getServletContext().getRequestDispatcher("/settings.jsp").forward(request, response);
    }
}
