package main.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.database.Configura;
import main.java.database.Manipula;
import main.java.server.Login;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int id = -1;
        Manipula dados = new Manipula(new Configura());

        ResultSet rs = dados.getResultado("SELECT id FROM ProjetoFinal.jogador"
                + " WHERE username = '" + username + "';");

        try {
            while(rs!=null && rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        dados.desligar();

        
        HttpSession session=request.getSession();

        if(username != null && password != null && Login.login(username, password) && id >= 0) {
            // Store username in session
            session.setAttribute("username", username);
            session.setAttribute("id", id);
            // Forward to the home page
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            // If login fails, forward back to the login page
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
