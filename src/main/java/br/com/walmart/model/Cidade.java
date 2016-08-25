package br.com.walmart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cidade {

	@Id @GeneratedValue
	private Integer id;
	private String nome;
	@ManyToOne
	private Grafo grafo;

	public Cidade(){
		
	}
	public Cidade(String nome){
		this.nome = nome;
		
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	  public int hashCode() {
	    final int prime = 7;
	    int result = 1;
	    result = prime * result + ((nome == null) ? 0 : nome.hashCode());
	    return result;
	  }
	
	@Override
	public boolean equals(Object obj) {
		Cidade cidade = (Cidade) obj;
		
		return this.nome.equals(cidade.getNome());
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Grafo getGrafo() {
		return grafo;
	}
	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}
}
