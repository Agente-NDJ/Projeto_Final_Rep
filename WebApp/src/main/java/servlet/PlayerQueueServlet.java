package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/PlayerQueueServlet")
public class PlayerQueueServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static String player1 = "";
    private static String player2 = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = (Integer) request.getSession().getAttribute("id");

        if (id == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("Player ID: " + id); // Log player ID for debugging

        
        if(player1.isEmpty()) {
        	player1 = String.valueOf(id);
        	
	        try {
	            // Introduce a delay of 5 seconds (5000 milliseconds)
	            Thread.sleep(5000); // Adjust the sleep time as needed
	        } catch (InterruptedException e) {
	            e.printStackTrace(); // Handle interrupted exception
	        }
        }
        
        else {
        	player2 = String.valueOf(id);
        }
        
        if(!player1.isEmpty() && !player2.isEmpty()) {
        	request.getRequestDispatcher("/game.jsp").forward(request, response);
        }
        
        else {
        	player1 ="";
        	request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    

}