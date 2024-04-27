<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projeto Final - Home</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
    <!-- Navbar -->
    <nav>
        <div>
            <a href="index.jsp">Home</a>
            <a href="MatchHistory">Match History</a>
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

    <!-- Main Content Section: Centered buttons -->
    <div class="buttons-container">
    <a href="PlayerQueueServlet">
    	<button type="button" id="playerVsPlayerButton" class="button">Player vs Player</button>
    </a>
    <button type="button" class="button">Player vs AI</button>
</div>
    
<!-- Waiting message -->
<div id="waitingMessage" style="display: none; text-align: center;">
    <p>Waiting for another player to join...</p>
</div>

<script>
    document.getElementById("playerVsPlayerButton").addEventListener("click", function() {
        // Show waiting message
        document.getElementById("waitingMessage").style.display = "block";
        
        // Redirect to PlayerQueueServlet
        window.location.href = "PlayerQueueServlet";
    });
    

    // Check if the game has started (check if redirected to game.jsp)
    // If the game starts, hide the waiting message
    if (window.location.pathname.endsWith("game.jsp")) {
        document.getElementById("waitingMessage").style.display = "none";
    }
</script>
</body>
</html>