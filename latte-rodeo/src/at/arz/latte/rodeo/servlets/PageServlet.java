package at.arz.latte.rodeo.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("*.html")
public class PageServlet
		extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		// response.setHeader("Pragma", "no-cache");
		// response.setHeader("Expires", "0");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/page.jsp");
		dispatcher.forward(request, response);
	}

}
