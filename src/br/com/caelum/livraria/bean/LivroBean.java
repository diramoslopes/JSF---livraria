package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", 
					new FacesMessage("Livro deve ter pelo menos um autor"));
			return;
		} 
		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
			this.livro = new Livro();
		} else { 
			new DAO<Livro>(Livro.class).atualiza(livro);
		}
		
		this.livro = new Livro();
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("escrito por: " + autor.getNome());
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void comecaComDigitoUm(FacesContext faceContext, UIComponent uiComponent, Object value) throws ValidatorException {
		String valor = value.toString();
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}
	}
	
	public List<Livro> getLivros(){
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public RedirectView formAutor() {
		System.out.println("chamando o formulario do autor");
//		return "autor";
		return new RedirectView("autor"); // o retorno dele será um objeto do tipoi RedirectView que por sua vez ja chama o metodo toString()
										 // exemplo RedirectView rw;  rw.toString(); o toString() por sua vez ja esta redirecionando para outra pagina
	}
	
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		new DAO<Livro>(Livro.class).remove(livro);
		this.getLivros().remove(livro);
	}
	
	public void removerAutor(Autor autor) {
		System.out.println("remover autor " + autor.getNome());
		new DAO<Autor>(Autor.class).remove(autor); //remover do banco
		this.getAutores().remove(autor); // remover da lista
	} // antes de remover um autor tem que remover o livro
	
	public void carregar(Livro livro) {
		System.out.println("carregando livro" + livro.getTitulo());
		this.livro = livro;
	}
	
	public void removeAutorDoLivro(Autor autor){
		this.livro.removeAutor(autor);
	}	
	
	public void carregarLivroPelaId(){
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(livroId);
	}

}