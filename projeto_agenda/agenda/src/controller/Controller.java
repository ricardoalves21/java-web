package controller;

import java.io.IOException;
import java.util.ArrayList;

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

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert" })
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
		} else {
			response.sendRedirect("index.html");
		}
	}

// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objeto que ir� receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
	}

//Novo contatos
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// setar as vari�veis JavaBeans
		contato.setNome(request.getParameter("nome")); //o objeto 'contato' est� recebendo o dado vindo do formul�rio e armazenando no atributo 'nome' atrav�s do m�todo set
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));	
		
		// invocar o m�todo inserirContato passando o objeto contato
		dao.inserirContato(contato);
		
		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
		
	}

}