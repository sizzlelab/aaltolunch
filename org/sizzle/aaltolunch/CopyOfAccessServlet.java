package org.sizzle.aaltolunch;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.sizzle.aaltolunch.asi.RestHandler;
import org.sizzle.aaltolunch.asi.datatype.ASISessionBean;

/**
 * 
 * @author Nalin Chaudhary
 */
public class CopyOfAccessServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		RestHandler handler = new RestHandler();
		
		ASISessionBean name = handler.loginAsUser(request);
		
		if (name != null)
		{
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Aalto Lunch service</title>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<h1 align=\"center\">Aalto Lunch service</h1>");
	        out.println("<p>Hello " + name + "</p>");
	        out.println("</body>");
	        out.println("</html>");
		}
		else
		{
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Aalto Lunch service</title>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<h1 align=\"center\">Aalto Lunch service</h1>");
	        out.println("<p>Login failed.</p>");
	        out.println("</body>");
	        out.println("</html>");
		}
    }
}
