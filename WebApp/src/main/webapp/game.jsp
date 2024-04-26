<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projeto Final - Game</title>
    <link rel="stylesheet" href="game.css">
    <script src="script.js" defer></script>
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
                    String username = (String) session.getAttribute("username");
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

    <div class="board" id="board">
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
	    <div class="cell" data-cell></div>
  </div>
    <div class="winning-message" id="winningMessage">
    	<div data-winning-message-text></div>
    	<button id="exitButton">Exit</button>
    </div>
</body>
</html>
