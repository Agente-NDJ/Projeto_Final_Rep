package servlet;

import database.Configura;
import database.Manipula;
import server.Login;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Manipula dados = new Manipula(new Configura());

        ResultSet rs = dados.getResultado("SELECT id, nome FROM ProjetoFinal.jogador"
                + " WHERE username = '" + username + "';");
        
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        
        int id = -1;
        String nome = "";

        try {
            while(rs!=null && rs.next()) {
               id = rs.getInt("id");
               nome = rs.getString("nome");
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        dados.desligar();

        
        HttpSession session=request.getSession();
        
        System.out.println("id: " + id);

        if(username != null && password != null && Login.login(username, password)) {
            // Store username in session
            session.setAttribute("username", username);
            session.setAttribute("nome", nome);
            session.setAttribute("id", id);
            // Forward to the home page
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            // If login fails, forward back to the login page
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}