<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList" import="javax.servlet.http.HttpSession"%>
    
        <%
    // Check if a user is already logged in
    String username = (String) session.getAttribute("username");
	
    if (username == null || username.isEmpty()) {
        // Redirect the user to index.jsp if logged in
        response.sendRedirect("index.jsp");
    }
    
    else{
        System.out.println("Username session: " + username);
    }
    
    
	ArrayList<String> match_ids = (ArrayList<String>) request.getAttribute("match_ids");
    ArrayList<String> games = (ArrayList<String>) request.getAttribute("game_names");
	ArrayList<String> opponents = (ArrayList<String>) request.getAttribute("oponentes_names");
	ArrayList<String> winners = (ArrayList<String>) request.getAttribute("vencedores_names");
	ArrayList<String> dates = (ArrayList<String>) request.getAttribute("datas");
	
	
	System.out.println("match_ids len: " + match_ids.size());
	System.out.println("match_ids len: " + games.size());
	System.out.println("match_ids len: " + opponents.size());
	System.out.println("match_ids len: " + winners.size());
	System.out.println("match_ids len: " + dates.size());
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Projeto Final - Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./leaderboard.css">
</head>
<body>
        <!-- Navbar -->
    <nav>
        <div>
            <a href="index.jsp">Home</a>
            <a href="MatchHistory">Leaderboard</a>
            <a href="index.jsp">Estarolas</a>
            <a href="index.jsp">About us</a>
        </div>
        <div>
            <!-- Display username or login/register buttons -->
                <%
                    username = (String) session.getAttribute("username");
                    if (username != null) {
                %>
                    <!-- Display the username if logged in -->
                    <a> Welcome, <%= username %>!</a>
                    <!-- Create a logout button -->
                    <a href="LogoutServlet">
                    	<button class="login-button button">Logout</button>
                    </a>
                <% } else { %>
                    <!-- Display login and register buttons if not logged in -->
                    <div class="home-buttons">
                        <a href="login.jsp">
                            <button class="login-button button">Login</button>
                        </a>
                        <a href="register.jsp">
                            <button class="register-button button">Register</button>
                        </a>
                    </div>
                <% } %>
        </div>
    </nav>

<div class="table-container">
    	<h1>Match History</h1>
    	<table class="match-table">
            <tr>
                <th>Game</th>
                <th>Opponent</th>
                <th>Winner</th>
                <th>Date</th>
            </tr>
            <% if (match_ids != null && !match_ids.isEmpty()) {
                for (int i = 0; i < match_ids.size(); i++) { %>
                    <tr>
                        <td><%= games.get(i) %></td>
                        <td><%= opponents.get(i) %></td>
                        <td><%= winners.get(i) %></td>
                        <td><%= dates.get(i) %></td>
                    </tr>
                <% }
            } %>
        </table>
    	</div>
</body>
</html>
