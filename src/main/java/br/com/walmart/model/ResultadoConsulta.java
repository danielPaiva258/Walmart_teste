package br.com.walmart.model;

import java.math.BigDecimal;

public class ResultadoConsulta {

	private Cidade cidadeOrigem;
	private Cidade cidadeDestino;
	private BigDecimal distancia;
	private BigDecimal consumo;
	
	public Cidade getCidadeOrigem() {
		return cidadeOrigem;
	}
	public void setCidadeOrigem(Cidade cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;
	}
	public Cidade getCidadeDestino() {
		return cidadeDestino;
	}
	public void setCidadeDestino(Cidade cidadeDestino) {
		this.cidadeDestino = cidadeDestino;
	}
	public BigDecimal getDistancia() {
		return distancia;
	}
	public void setDistancia(BigDecimal distancia) {
		this.distancia = distancia;
	}
	public BigDecimal getConsumo() {
		return consumo;
	}
	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}
}
