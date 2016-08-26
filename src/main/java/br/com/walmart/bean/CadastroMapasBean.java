package br.com.walmart.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.walmart.dao.DAO;
import br.com.walmart.dao.GrafoDao;
import br.com.walmart.model.Cidade;
import br.com.walmart.model.Grafo;
import br.com.walmart.model.Rota;
import br.com.walmart.util.Dijkstra;

@Named
@ViewScoped
public class CadastroMapasBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Rota> rotas = new ArrayList<Rota>();
	private Grafo grafo = new Grafo();;
	private List<Cidade> cidades = new ArrayList<Cidade>();
	@Inject
	private GrafoDao dao;
	private String nomeGrafo;
	
	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}
	
	public void addLinhaTabela(){
		Cidade cidade = new Cidade("");
		Cidade cidade2 = new Cidade("");
		cidade.setGrafo(grafo);
		cidade2.setGrafo(grafo);
		cidades.add(cidade);
		cidades.add(cidade2);
		Rota rota = new Rota(cidade,cidade2,0);
		rota.setGrafo(grafo);
	    rotas.add(rota);
	}
	
	public void salvar (){
		Grafo grafoAux = dao.buscaGrafoPorNome(nomeGrafo);
		if(grafoAux == null){
			
			grafo.setNome(nomeGrafo);
			grafo.setCidades(cidades);
			grafo.setRotas(rotas);
			dao.adiciona(grafo);
			
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage("Mapa salvo com sucesso."));
			
			rotas = new ArrayList<Rota>();
			nomeGrafo = new String();
			grafo = new Grafo();
			cidades = new ArrayList<Cidade>();
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage("Já existe um grafo com esse nome."));
		}
	}

	public String getNomeGrafo() {
		return nomeGrafo;
	}

	public void setNomeGrafo(String nomeGrafo) {
		this.nomeGrafo = nomeGrafo;
	}	
}
