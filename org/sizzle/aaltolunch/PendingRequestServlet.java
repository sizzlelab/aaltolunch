package org.sizzle.aaltolunch;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.RestHandler;
import org.sizzle.aaltolunch.asi.datatype.ASIUserBean;

public class PendingRequestServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		String uName = (String) session.getAttribute("uName");
		String uPwd = (String) session.getAttribute("uPassword");
		
		RestHandler handler = new RestHandler();
		List<ASIUserBean> users = handler.fetchPendingRequests(uName, uPwd);
		
		if (users != null)
		{
			System.out.println("PendingRequestServlet: number of users: " + users.size());
			Collections.sort(users);
		}

		session.setAttribute("spr.results", users);
//		session.setAttribute("spr.index", "0");
		getServletContext().getRequestDispatcher("/pendingRequests.jsp?ix=0").forward(request, response);
    }
}
