//package servlet;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//
//import server.JogoGalo;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@WebServlet("/PlayServlet")
//public class PlayServlet extends HttpServlet {
//    private JogoGalo ticTacToe; // Assuming JogoGalo is your game logic class
//    
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        ticTacToe = new JogoGalo(); // Initialize TicTacToe instance
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        
//        // Retrieve player's ID from session or generate a new ID if not available
//        int playerId = (int) session.getAttribute("id");
//        if (session.getAttribute("id") == null ) {
//        	response.sendRedirect("index.jsp");
//        }
//        
//        // Retrieve player's move from request parameters
//        int row = Integer.parseInt(request.getParameter("row"));
//        int col = Integer.parseInt(request.getParameter("col"));
//        
//        // Store or update player's move in session
//        // You can store moves in a list or any data structure depending on your game logic
//        List<int[]> playerMoves = (List<int[]>) session.getAttribute("playerMoves");
//        if (playerMoves == null) {
//            playerMoves = new ArrayList<>();
//        }
//        playerMoves.add(new int[]{row, col});
//        session.setAttribute("playerMoves", playerMoves);
//        
//        // Process player's move and update game state
//        // Implement your game logic here
//        
//        // Send updated game state to the client
//        String gameState = constructGameState(); // Implement this method to construct game state
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        out.println(gameState);
//        out.close();
//    }
//
//
//    private int[] processMove(String move) {
//        // Parse player's move (e.g., "row,column") and return as an array
//        String[] coordinates = move.split(",");
//        int row = Integer.parseInt(coordinates[0]);
//        int col = Integer.parseInt(coordinates[1]);
//        return new int[]{row, col};
//    }
//
//    private String constructGameState() {
//        // Implement this method to construct and return game state as JSON or string
//    }
//}
