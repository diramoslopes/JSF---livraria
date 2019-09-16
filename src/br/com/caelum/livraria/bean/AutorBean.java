package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.ForwardView;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	
	private Integer autorId;

	public Autor getAutor() {
		return autor;
	}

	public RedirectView gravar() { // ForwardView � redirecionamento do lado do servidor // outra class criada � o RedirectView que � do lado do cliente
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if(this.autor.getId() == null) {
		new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(autor);
		}
		
		this.autor = new Autor();
		
		//return "livro?faces-redirect=true"; // ap�s salvar um autor � redirecionado para pagina livro.xhtml
		//return new ForwardView("livro");
		return new RedirectView("livro");
	}
	
	public void carregarAutor(Autor autor) {
		System.out.println("carregando autor" + autor.getNome());
		//this.livro = livro;
		this.autor = autor;
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId(){
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
}
