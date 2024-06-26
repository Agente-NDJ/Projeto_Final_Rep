package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Configura;
import database.Manipula;


@WebServlet("/MatchHistory")
public class MatchHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		if (session == null || session.getAttribute("id") == null || session.getAttribute("nome") == null) {
	        // If session doesn't exist or if username and ID are not in the session, redirect to index.jsp
	        PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('You need to login first to check the match history');");
            out.println("window.location.href = 'login.jsp';");
            out.println("</script>");
            return; // End the method here to prevent further execution
	    }
		
		Manipula dados = new Manipula(new Configura());
		
		int user_id = (int) session.getAttribute("id");
		String user_nome = (String) session.getAttribute("nome");
		
		ResultSet rsMatch = dados.getResultado("SELECT id, jogo_id, jogador2_id, ia_id, tipo_oponente, vencedor, data " + 
												"FROM ProjetoFinal.historico_jogos WHERE jogador1_id='" + user_id + "';");
		
		/*
		int match_id = -1;
		int jogo_id = -1;
		int jogador2_id = -1;
		int ia_id = -1;
		String tipo_oponente = "";
		String vencedor = "";
		*/
		
        ArrayList<Integer> match_ids = new ArrayList<Integer>();
        ArrayList<Integer> game_ids = new ArrayList<Integer>();
        ArrayList<Integer> jogador2_ids = new ArrayList<Integer>();
        ArrayList<Integer> ia_ids = new ArrayList<Integer>();
        
        ArrayList<String> tipo_oponentes = new ArrayList<String>();
        ArrayList<String> vencedores = new ArrayList<String>();
        ArrayList<String> datas = new ArrayList<String>();
		

		try {
			while(rsMatch!=null && rsMatch.next()) {
				/*
				match_id = rsMatch.getInt("id");
				jogo_id = rsMatch.getInt("jogo_id");
				jogador2_id = rsMatch.getInt("jogador2_id");
				ia_id = rsMatch.getInt("ia_id");
				tipo_oponente = rsMatch.getString("tipo_oponente");
				vencedor = rsMatch.getString("vencedor");
				*/
				
				match_ids.add(rsMatch.getInt("id"));
				game_ids.add(rsMatch.getInt("jogo_id"));
				jogador2_ids.add(rsMatch.getInt("jogador2_id"));
				ia_ids.add(rsMatch.getInt("ia_id"));
				tipo_oponentes.add(rsMatch.getString("tipo_oponente"));
				vencedores.add(rsMatch.getString("vencedor"));
				datas.add(rsMatch.getString("data"));
				
				// Ter em atenção para o jogador2 ou ia que é null em cada jogo (no jsp, apenas mostrar o que for diferente de null
            }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> game_names = new ArrayList<String>();
		//ArrayList<String> jogador2_names = new ArrayList<String>();
		//ArrayList<String> ia_niveis = new ArrayList<String>();
		ArrayList<String> oponentes_names = new ArrayList<String>();
		ArrayList<String> vencedores_names = new ArrayList<String>();
		
		// Ciclo que percorre todos as partidas e armazena informações sobre os nomes dos jogos, oponentes, níveis de ia 
		if(match_ids.size() > 0) {
			

			for(int i=0; i < match_ids.size(); i++) {
				
				
				// GAME NAME
	    		ResultSet rsGame = dados.getResultado("SELECT nome " + 
	    				"FROM ProjetoFinal.jogo WHERE id='" + game_ids.get(i) + "';");
	    		
				try {
					while(rsGame!=null && rsGame.next()) {
						game_names.add(rsGame.getString("nome"));
		            }

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// VS PLAYER (PLAYER NAME)
				if(tipo_oponentes.get(i).equals("jogador")) {
					
		    		ResultSet rsJg2 = dados.getResultado("SELECT nome " + 
		    				"FROM ProjetoFinal.jogador WHERE id='" + jogador2_ids.get(i) + "';");
		    		
					try {
						while(rsJg2!=null && rsJg2.next()) {
							
							String jogador2_nome = rsJg2.getString("nome");
							//jogador2_names.add(jogador2_nome);
							//ia_niveis.add(null);
							oponentes_names.add(jogador2_nome);
							
							// WINNER (player 1 or player 2)
							if(vencedores.get(i).equals("1")) {
								vencedores_names.add(user_nome);
							}
							
							else {
								vencedores_names.add(jogador2_nome);
							}
			            }

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
				// VS AI (AI LEVEL)
				else if(tipo_oponentes.get(i).equals("ia")) {
		    		
					ResultSet rsAI = dados.getResultado("SELECT nivel " + 
		    				"FROM ProjetoFinal.ia WHERE id='" + ia_ids.get(i) + "';");
		    		
		    		
					try {
						while(rsAI!=null && rsAI.next()) {
							
							String nivel = rsAI.getString("nivel");
							//ia_niveis.add(nivel);
							//jogador2_names.add(null);
							oponentes_names.add(nivel);
							
							// WINNER (player 1 or AI)
							if(vencedores.get(i).equals("1")) {
								vencedores_names.add(user_nome);
							}
							
							else {
								vencedores_names.add(nivel);
							}
			            }
						
						

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
			}
		}
		
		request.setAttribute("match_ids", match_ids);
		request.setAttribute("game_names", game_names);
//		request.setAttribute("jogador2_names", jogador2_names);
//		request.setAttribute("ia_niveis", ia_niveis);
		request.setAttribute("oponentes_names", oponentes_names);
//		request.setAttribute("tipo_oponentes", tipo_oponentes);
		request.setAttribute("vencedores_names", vencedores_names);
		request.setAttribute("datas", datas);

		dados.desligar();
		response.setContentType("text/html; charset=ISO-8859-1");
		getServletContext().getRequestDispatcher("/leaderboard.jsp").forward(request, response);
	
	}
}