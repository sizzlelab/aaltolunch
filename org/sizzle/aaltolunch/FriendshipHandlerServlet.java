package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.RestHandler;

public class FriendshipHandlerServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		
		String uName = (String) session.getAttribute("uName");
		String uPwd = (String) session.getAttribute("uPassword");
		String fid = request.getParameter("t1");
		String fname = request.getParameter("t2");
		String type = request.getParameter("t3");
		
		System.out.println("FriendshipHandlerServlet: uName  : " + uName);
		System.out.println("FriendshipHandlerServlet: fid    : " + fid);
		System.out.println("FriendshipHandlerServlet: fname  : " + fname);
		System.out.println("FriendshipHandlerServlet: type   : " + type);
		
		RestHandler handler = new RestHandler();
		
		String message = "success";
		try 
		{
			handler.handleFriendRequest(uName, uPwd, fid, type);
		} 
		catch (Exception e) 
		{
			message = e.getMessage();
		}
		
		session.setAttribute("handleFriendship.fname", fname);
		session.setAttribute("handleFriendship.type", type);
		session.setAttribute("handleFriendship.result", message);
		getServletContext().getRequestDispatcher("/pendingRequests.jsp?ix=0").forward(request, response);
    }
}
