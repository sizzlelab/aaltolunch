package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		
		session.removeAttribute("uid");
		session.removeAttribute("uName");
		session.removeAttribute("uPassword");
		session.removeAttribute("appId");
			
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
