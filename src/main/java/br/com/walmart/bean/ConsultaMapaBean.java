package br.com.walmart.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.walmart.dao.GrafoDao;
import br.com.walmart.model.Cidade;
import br.com.walmart.model.Grafo;
import br.com.walmart.model.ResultadoConsulta;
import br.com.walmart.model.Rota;
import br.com.walmart.util.Dijkstra;

@Named
@ViewScoped
public class ConsultaMapaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cidade> caminhos = new LinkedList<Cidade>();
	private String origemNome;
	private String destinoNome;
	private List<ResultadoConsulta> resultado;
	private Grafo grafo;
	private BigDecimal custoTotal;
	private BigDecimal consumo;
	private BigDecimal precoCombustivel;
	private BigDecimal valorLitro;
	private List<Rota> rotas = new ArrayList<Rota>();
	private BigDecimal consumoSomado;
	private BigDecimal distanciaSomada;
	private String nomeGrafo;
	private boolean jaCalculado;
	@Inject
	private GrafoDao grafoDao;
	
	public void consulta(){
		resultado = new ArrayList<ResultadoConsulta>();
		distanciaSomada = BigDecimal.valueOf(0L);
		consumoSomado  = BigDecimal.valueOf(0L);
		Grafo grafo = grafoDao.buscaGrafoPorNome(nomeGrafo);
		
		if(grafo != null){
			
			Cidade cidadeOrigem = new Cidade(origemNome);
			Cidade cidadeDestino = new Cidade(destinoNome);
			
			Dijkstra dijkstra = new Dijkstra(grafo);
			dijkstra.inicia(cidadeOrigem);
			
			setCaminhos(dijkstra.getCaminho(cidadeDestino));
			
			for(int i = 0; i < caminhos.size(); i++){
				ResultadoConsulta resultadoConsulta = new ResultadoConsulta();
				resultadoConsulta.setCidadeOrigem(caminhos.get(i));
				if (i+1 < caminhos.size()){
					
					resultadoConsulta.setCidadeDestino(caminhos.get(i+1));
					resultadoConsulta.setDistancia(BigDecimal.valueOf(dijkstra.getDistancia(resultadoConsulta.getCidadeOrigem(), 
							resultadoConsulta.getCidadeDestino())));
					
					
					distanciaSomada = resultadoConsulta.getDistancia().add(distanciaSomada);
					consumoSomado = (resultadoConsulta.getDistancia())
							.divide(consumo,3, RoundingMode.UP )
							.add(getConsumoSomado()) ;
					resultadoConsulta.setConsumo(getConsumoSomado());
					resultadoConsulta.setDistancia(distanciaSomada);
					resultado.add(resultadoConsulta);
				}
			}
			
			custoTotal = consumoSomado.multiply(consumoSomado);
			custoTotal = custoTotal.setScale(2,RoundingMode.DOWN);
		}
		
	}


	public String getOrigemNome() {
		return origemNome;
	}

	public void setOrigemNome(String origemNome) {
		this.origemNome = origemNome;
	}

	public String getDestinoNome() {
		return destinoNome;
	}

	public void setDestinoNome(String destinoNome) {
		this.destinoNome = destinoNome;
	}

	public List<ResultadoConsulta> getResultado() {
		return resultado;
	}

	public void setResultado(List<ResultadoConsulta> resultado) {
		this.resultado = resultado;
	}


	public List<Cidade> getCaminhos() {
		return caminhos;
	}


	public void setCaminhos(List<Cidade> caminhos) {
		this.caminhos = caminhos;
	}


	public BigDecimal getValorLitro() {
		return valorLitro;
	}


	public void setValorLitro(BigDecimal valorLitro) {
		this.valorLitro = valorLitro;
	}
	
	private void adicionaRota(Cidade origem, Cidade destino, Integer distancia) {
	    Rota lane = new Rota(origem, destino, distancia);
	    rotas.add(lane);
	  }


	public BigDecimal getConsumo() {
		return consumo;
	}


	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}


	public BigDecimal getConsumoSomado() {
		return consumoSomado.setScale(2, RoundingMode.UP);
	}


	public void setConsumoSomado(BigDecimal consumoSomado) {
		this.consumoSomado = consumoSomado;
	}


	public BigDecimal getDistanciaSomada() {
		return distanciaSomada.setScale(2, RoundingMode.UNNECESSARY);
	}


	public void setDistanciaSomada(BigDecimal distanciaSomada) {
		this.distanciaSomada = distanciaSomada;
	}


	public String getNomeGrafo() {
		return nomeGrafo;
	}


	public void setNomeGrafo(String nomeGrafo) {
		this.nomeGrafo = nomeGrafo;
	}


	public BigDecimal getPrecoCombustivel() {
		return precoCombustivel;
	}


	public void setPrecoCombustivel(BigDecimal precoCombustivel) {
		this.precoCombustivel = precoCombustivel;
	}


	public BigDecimal getCustoTotal() {
		return custoTotal;
	}


	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}


	public boolean getJaCalculado() {
		if (this.distanciaSomada != null && this.custoTotal != null)
		return true;
		
		return false;
	}


	public void setJaCalculado(boolean jaCalculado) {
		this.jaCalculado = jaCalculado;
	}
	
	
}
