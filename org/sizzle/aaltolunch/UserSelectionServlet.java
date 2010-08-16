package org.sizzle.aaltolunch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TODO logout
 * @author Nalin Chaudhary
 */
public class UserSelectionServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private UserSelectionHandler userSelectionHandler = null;
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		
		userSelectionHandler = new UserSelectionHandler();
		userSelectionHandler.init();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		HttpSession session = request.getSession(true);
		
		String uid = (String)session.getAttribute("uid");
		String selectedRestaurant = request.getParameter("sel");
		String hour = request.getParameter("hour");
		String min = request.getParameter("min");
		
		if ((uid != null) && (selectedRestaurant != null))
		{
			/* Nalin Chaudhary
			 * Finding the ASI appdata collection inconvenient for updating the value for user selection.
			 * So, using a property file for data collection. Best solution is to have the data in DB. 
			 */
//			RestHandler handler = new RestHandler();
//			ASISessionBean sBean = handler.loginAsApplication();
//			String appId = sBean.getAppId();
//			handler.putDataInCollection(appId, uid, selectedRestaurant, hour + ":" + min);
			
			// write to property file against the uid (unique for each user)
			userSelectionHandler.writeToCollection(uid, selectedRestaurant, hour, min);
			
			getServletContext().getRequestDispatcher("/mainPage.jsp").forward(request, response);
		}
		else
		{
			session.setAttribute("result", "failed");
			getServletContext().getRequestDispatcher("/campus.jsp").forward(request, response);
		}
    }
}
