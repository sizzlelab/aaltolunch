package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sizzle.aaltolunch.asi.RestHandler;
import org.sizzle.aaltolunch.asi.datatype.ASISessionBean;

/**
 * @author Nalin Chaudhary
 */
public class AccessServlet extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		
		if (session.getAttribute("loginResult") != null)
		{
			session.removeAttribute("loginResult");
		}
		
		RestHandler handler = new RestHandler();
		ASISessionBean sBean = handler.loginAsUser(request);

		if (sBean != null)
		{
			String uid = sBean.getUserId();
			String appId = sBean.getAppId();
			
			// remove the session attributes (in case those are still existing)
			session.removeAttribute("uid");
			
			// TODO required to remove on logout form AaltoLunch
			session.setAttribute("uid", uid);
			session.setAttribute("uName", request.getParameter("t1"));
			session.setAttribute("uPassword", request.getParameter("t2"));
			session.setAttribute("appId", appId);
			
			getServletContext().getRequestDispatcher("/mainPage.jsp").forward(request, response);
			
//	        response.setContentType("text/html");
//	        PrintWriter out = response.getWriter();
//	        out.println("<html>");
//	        out.println("<head>");
//	        out.println("<title>Aalto Lunch service</title>");
//	        out.println("</head>");
//	        out.println("<body>");
//	        out.println("<h1 align=\"center\">Aalto Lunch service</h1>");
//	        out.println("<p>Hello " + uBean.getUserNameInfo().getUnstructured() + "</p>");
//	        out.println("<p>" + uBean.getUserAvtarInfo().getUserAvtarLink().getHref() + "</p>");
//	        
//	        List<ASIUserBean> friends = handler.fetchLoggedInUserFriends(uid);
//	        if (friends != null && friends.size() > 0)
//	        {
//	        	for (ASIUserBean b : friends)
//	        	{
//	        		out.println("<p>Friend: " + b.getUserNameInfo().getUnstructured() + "</p>");
//	        	}
//	        }
//	        
//	        
//	        out.println("</body>");
//	        out.println("</html>");
		}
		else
		{
			session.setAttribute("loginResult", "failed");
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			
//	        response.setContentType("text/html");
//	        PrintWriter out = response.getWriter();
//	        out.println("<html>");
//	        out.println("<head>");
//	        out.println("<title>Aalto Lunch service</title>");
//	        out.println("</head>");
//	        out.println("<body>");
//	        out.println("<h1 align=\"center\">Aalto Lunch service</h1>");
//	        out.println("<p>Login failed.</p>");
//	        out.println("</body>");
//	        out.println("</html>");
		}
    }
}
