package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.RestHandler;

/**
 * 
 * @author Nalin Chaudhary
 */
public class FriendshipRequestServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		
		String uName = (String) session.getAttribute("uName");
		String uPwd = (String) session.getAttribute("uPassword");
		String fid = request.getParameter("t1");
		String fname = request.getParameter("t2");
		
		System.out.println("FriendshipRequestServlet: uName  : " + uName);
		System.out.println("FriendshipRequestServlet: fid    : " + fid);
		System.out.println("FriendshipRequestServlet: fname  : " + fname);
		
		RestHandler handler = new RestHandler();
		
		String message = "success";
		try 
		{
			handler.askFriendship(uName, uPwd, fid);
		} 
		catch (Exception e) 
		{
			message = e.getMessage();
		}
		
		session.setAttribute("askFriendship.fname", fname);
		session.setAttribute("askFriendship.result", message);
		getServletContext().getRequestDispatcher("/settings.jsp").forward(request, response);
    }
}
