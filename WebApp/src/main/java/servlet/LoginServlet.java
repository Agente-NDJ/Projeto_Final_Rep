package servlet;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Configura;
import database.Manipula;
import server.Login;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Manipula dados = new Manipula(new Configura());
    	
        String username = request.getParameter("username");
        
        String password = request.getParameter("password");
        
        System.out.print(username);
        System.out.print(password);
        
        int id = getIdFromPlayer(dados, username);

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
    
    private int getIdFromPlayer(Manipula dados, String username) {

    	int id = -1;
    	
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
        return id;
    }
}