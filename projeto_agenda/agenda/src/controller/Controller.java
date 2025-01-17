package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

/* � aqui que inclu�mos as requisi��es que o servidor ir� fazer atrav�s do comando 'urlPatterns', 
 * inclu� tamb�m o sinal de igual as chaves e '/main/'
 */

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

// LISTAR CONTATOS
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Criando um objeto que ir� receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

// NOVO CONTATO
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// setar as vari�veis JavaBeans
		contato.setNome(request.getParameter("nome")); // o objeto 'contato' est� recebendo o dado vindo do formul�rio e
														// armazenando no atributo 'nome' atrav�s do m�todo set
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// invocar o m�todo inserirContato passando o objeto contato
		dao.inserirContato(contato);

		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

// EDITAR CONTATO
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recebimento do id do contato que ser� editado
		String idcon = request.getParameter("idcon");

		// setar a vari�vel JavaBeans
		contato.setIdcon(idcon);

		// executar o m�todo selecionarContato (DAO)
		dao.selecionarContato(contato);

		// setar os atributos do formul�rio com o conte�do JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}
	
// EDITAR CONTATO
		protected void editarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
		// setar as vari�veis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		// executar o m�todo alterarContato
		dao.alterarContato(contato);
		
		// redirecionar para o documento agenda.jsp (atualizando as altera��es)
		response.sendRedirect("main");
			
		}


}