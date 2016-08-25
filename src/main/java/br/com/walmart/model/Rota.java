package br.com.walmart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Rota {

	@Id @GeneratedValue
	private Integer id;
	@OneToOne()
	private Cidade origem;
	@OneToOne()
	private Cidade destino;
	@ManyToOne
	private Grafo grafo;
	private Integer distancia;
	
	public Rota(){
		
	}
	
	public Rota (Cidade origem , Cidade destino, Integer distancia){
		this.setOrigem(origem);
		this.setDestino(destino);
		this.setDistancia(distancia);
		
	}



	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}


	public Cidade getOrigem() {
		return origem;
	}


	public void setOrigem(Cidade origem) {
		this.origem = origem;
	}



	public Cidade getDestino() {
		return destino;
	}



	public void setDestino(Cidade destino) {
		this.destino = destino;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}


}
