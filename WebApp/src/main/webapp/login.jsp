<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    // Check if a user is already logged in
    String username = (String) session.getAttribute("username");
    if (username != null) {
        // Redirect the user to index.jsp if logged in
        response.sendRedirect("index.jsp");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projeto Final - Login</title>
    <link rel="stylesheet" href="login.css">
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
            <!-- Display login/register buttons -->
            <a href="login.jsp"><button class="login-button button">Login</button></a>
            <a href="register.jsp"><button class="register-button button">Register</button></a>
        </div>
    </nav>

    <!-- Centered form container -->
    <div class="form-container">
        <form action="LoginServlet" method="POST" class="login-form">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" class="login-input">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" class="login-input">
            <button type="submit" class="login-button button">Login</button>
        </form>
    </div>
</body>
</html>
