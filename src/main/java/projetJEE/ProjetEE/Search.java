package projetJEE.ProjetEE;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/search")
public class Search extends HttpServlet {
	
    private static final long serialVersionUID = 8345942227030538441L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = request.getParameter("query");
    	response.sendRedirect("https://google.fr/search?q=" + query);
    }
}
