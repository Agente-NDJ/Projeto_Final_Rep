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

import database.BCrypt;
import database.Configura;
import database.Manipula;


@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Manipula dados = new Manipula(new Configura());
		
		String nome = request.getParameter("nome");
		
		String username = request.getParameter("username");
		
		String password = request.getParameter("password");
			
		String erro = "";
		request.setAttribute("erro", erro);
		
		System.out.println("ENTROU");
		
		try {
			if(usernameExists(dados, username)) {
				erro = "Já existe um utilizador com o nome de utilizador introduzido";
				request.setAttribute("erro", erro);
				getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);

			}
			
			else if(nome.trim().equals("") || username.trim().equals("") || password.trim().equals("")) {
				erro = "Os dados introduzidos devem conter caractéres diferentes de espaços.";
				request.setAttribute("erro", erro);
				getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
			}
				
			else {
				
				String hash_password = BCrypt.hashpw(password, BCrypt.gensalt());
				
				System.out.println("nome: " + nome);
				System.out.println("username: " + username);
				System.out.println("password: " + password);
				System.out.println("hash: " + hash_password);
					
				dados.xDirectiva("insert into jogador(nome, username, hash_password) values" +
						"('"+nome+"', '"+username+"', '"+hash_password+"');");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession session=request.getSession();
		int id = getIdFromPlayer(dados, username);
		
		dados.desligar();
		session.setAttribute("username", username);
        session.setAttribute("id", id);
		response.setContentType("text/html; charset=ISO-8859-1");		
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
    private boolean usernameExists(Manipula dados, String username) throws SQLException {
  
        ResultSet rs = dados.getResultado("SELECT id FROM jogador WHERE username = '"+username+"';");
        
        if(rs != null && rs.next()) {
        	return true;
        }
        
        return false;
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
