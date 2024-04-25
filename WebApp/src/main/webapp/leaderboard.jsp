<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    // Check if a user is already logged in
    String username = (String) session.getAttribute("username");
    if (username != null) {
        // Redirect the user to index.jsp if logged in
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en"
  ><head
    ><title>Projeto Final - Login</title
    ><meta
      property="og:title"
      content="Login - Required Fruitful Spoonbill" /><meta
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
	.login-button button{
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
      ><link href="./login.css" rel="stylesheet" /><div class="login-container"
        ><header data-thq="thq-navbar" class="login-navbar-interactive"
          ><div data-thq="thq-navbar-nav" class="login-desktop-menu"
            ><nav class="login-links"
              ><a class="login-text" href="index.jsp">Home</a
              ><a class="login-text1" href="leaderboard.jsp">Leaderboard</a
              ><a class="login-text2" href="index.jsp">Estarolas</a
              ><a class="login-text3" href="index.jsp">About us</a></nav
            ><div class="login-buttons">
            	<a href="login.jsp"
              		<button class="login-login button">Login</button/></a>
              	<a href="register.jsp"		
              		<button class="button">Register</button></a>
             </div>
            <div data-thq="thq-burger-menu" class="login-burger-menu"
            ><svg viewBox="0 0 1024 1024" class="login-icon">
              <path
                d="M128 554.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 298.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 810.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667z"
              ></path></svg></div
          ><div data-thq="thq-mobile-menu" class="login-mobile-menu"
            ><div class="login-nav"
              ><div class="login-top"
                ><img
                  alt="image"
                  src="https://presentation-website-assets.teleporthq.io/logos/logo.png"
                  class="login-logo" /><div
                  data-thq="thq-close-menu"
                  class="login-close-menu"
                   <svg viewBox="0 0 1024 1024" class="login-icon02">
                    <path
                      d="M810 274l-238 238 238 238-60 60-238-238-238 238-60-60 238-238-238-238 60-60 238 238 238-238z"
                    ></path></svg></div></div
              ><nav class="login-links1"
                ><span class="login-text4">About</span
                ><span class="login-text5">Features</span
                ><span class="login-text6">Pricing</span
                ><span class="login-text7">Team</span
                ><span class="login-text8">Blog</span></nav
              ><div class="login-buttons1"
                ><button class="login-login1 button">Login</button
                ><button class="button">Register</button></div
              ></div></header>
        
    </div>
    <script
      defer=""
      src="https://unpkg.com/@teleporthq/teleport-custom-scripts"
    ></script></body
></html>