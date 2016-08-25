package br.com.walmart.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Grafo {
	@Id @GeneratedValue
	private Integer id;
	
	@OneToMany(cascade={CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
	private  List<Rota> rotas;
	private String nome;
	@OneToMany(cascade={CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
	private  List<Cidade> cidades;
	
	public Grafo(){
		
	}
	
	public Grafo (List<Cidade> cidades , List<Rota> rotas){
		this.setRotas(rotas);
		this.setCidades(cidades);
		List<Rota> rotasReversas = new ArrayList<Rota>();
		boolean existeRotaReversa = false;
		
		
		for(Rota rota : rotas){
			for(Rota rotaAux : rotas){
				if(rota.getDestino() == rotaAux.getOrigem() && rotaAux.getDestino() == rota.getOrigem()){
					existeRotaReversa=true;
				}
			}
			if(existeRotaReversa == false){
				rotasReversas.add(new Rota(rota.getDestino(), rota.getOrigem(), rota.getDistancia()));
			}
		}
		
		rotas.addAll(rotasReversas);
	}
	
	
	
	public List<Rota> getRotas() {
		return rotas;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}

}
