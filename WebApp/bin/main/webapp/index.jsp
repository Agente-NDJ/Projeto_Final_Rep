<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en"
  ><head
    ><title>Projeto Final - Home</title
    ><meta property="og:title" content="Required Fruitful Spoonbill" /><meta
      name="viewport"
      content="width=device-width, initial-scale=1.0" /><meta
      charset="utf-8" /><meta
      property="twitter:card"
      content="summary_large_image" /><style data-tag="reset-style-sheet">
      html {  line-height: 1.15;}body {  margin: 0;}* {  box-sizing: border-box;  border-width: 0;  border-style: solid;}p,li,ul,pre,div,h1,h2,h3,h4,h5,h6,figure,blockquote,figcaption {  margin: 0;  padding: 0;}button {  background-color: transparent;}button,input,optgroup,select,textarea {  font-family: inherit;  font-size: 100%;  line-height: 1.15;  margin: 0;}button,select {  text-transform: none;}button,[type="button"],[type="reset"],[type="submit"] {  -webkit-appearance: button;}button::-moz-focus-inner,[type="button"]::-moz-focus-inner,[type="reset"]::-moz-focus-inner,[type="submit"]::-moz-focus-inner {  border-style: none;  padding: 0;}button:-moz-focus,[type="button"]:-moz-focus,[type="reset"]:-moz-focus,[type="submit"]:-moz-focus {  outline: 1px dotted ButtonText;}a {  color: inherit;  text-decoration: inherit;}input {  padding: 2px 4px;}img {  display: block;}html { scroll-behavior: smooth  }</style
    ><style data-tag="default-style-sheet">
      html {
        font-family: Inter;
        font-size: 16px;
      }

      body {
        font-weight: 400;
        font-style:normal;
        text-decoration: none;
        text-transform: none;
        letter-spacing: normal;
        line-height: 1.15;
        color: var(--dl-color-gray-black);
        background-color: var(--dl-color-gray-white);
        }

        /* Add this CSS for making buttons clickable */
        .home-button,
        .home-button1,
        .button {
            cursor: pointer;
        }
      </style
    ><link
      rel="stylesheet"
      href="https://unpkg.com/animate.css@4.1.1/animate.css" /><link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&amp;display=swap"
      data-tag="font" /><link
      rel="stylesheet"
      href="https://unpkg.com/@teleporthq/teleport-custom-scripts/dist/style.css" /></head
  ><body
    ><link rel="stylesheet" href="./style.css" /><div
      ><link href="./index.css" rel="stylesheet" /><div class="home-container"
        ><header data-thq="thq-navbar" class="home-navbar-interactive"
          ><div data-thq="thq-navbar-nav" class="home-desktop-menu"
            ><nav class="home-links"
              ><a class="home-text" href="index.jsp">Home</a
              ><a class="home-text1" href="index.jsp">Leaderboard</a
              ><a class="home-text2" href="index.jsp">Estarolas</a
              ><a class="home-text3" href="index.jsp">About us</a>
              </nav>
                <!-- Display username or login/register buttons -->
                <%
                    String username = (String) session.getAttribute("username");
                    if (username != null) {
                %>
                    <!-- Display the username if logged in -->
                    <div>Welcome, <%= username %>!</div>
                    <!-- Create a logout button -->
	                <form action="LogoutServlet" method="get">
	                    <button type="submit">Logout</button>
	                </form>
                <% } else { %>
                    <!-- Display login and register buttons if not logged in -->
                    <div class="home-buttons">
                        <a href="login.jsp">
                            <button class="home-login button">Login</button>
                        </a>
                        <a href="register.jsp">
                            <button class="home-register button">Register</button>
                        </a>
                    </div>
                <% } %>
            </div>
            <!-- Burger Menu -->
            <div class="home-burger-menu">
                <svg viewBox="0 0 1024 1024" class="home-icon">
                    <!-- Burger Menu Icon -->
                </svg>
            </div>
        </nav>
    </header>

    <!-- Main Content Section -->
    <div class="home-container">
        <!-- Your main content goes here -->
        <button type="button" class="home-button button">Player vs Player</button>
        <button type="button" class="home-button1 button">Player vs AI</button>
    </div>

    <!-- Include your scripts -->
    <script src="https://unpkg.com/@teleporthq/teleport-custom-scripts" defer></script>
</body>
</html>
