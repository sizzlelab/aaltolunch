package org.sizzle.aaltolunch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TellMyIp extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
		// Creating response html
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Request details</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<p>Your ip is   : " + request.getRemoteAddr() + "</p>");
        out.println("<p>Your host is : " + request.getRemoteHost() + "</p>");
        
        out.println("<p><b>----- Request headers -----</b></p>");        
        
        for (Enumeration headerEnum = request.getHeaderNames(); headerEnum.hasMoreElements();) 
        {
        	String headerName = (String)headerEnum.nextElement();
        	out.println("<p>Header name: " + headerName + ", value: " + request.getHeader(headerName) + "</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
