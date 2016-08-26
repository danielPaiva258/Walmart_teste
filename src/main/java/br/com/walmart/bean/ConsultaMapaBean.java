package br.com.walmart.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	private boolean excluiRota;
	private List<ResultadoConsulta> resultado;
	private Grafo grafo;
	private List<Cidade> cidadesExcluidas = new ArrayList<Cidade>();
	private List<Rota> rotasExcluidas = new ArrayList<Rota>();
	private BigDecimal custoTotal;
	private BigDecimal consumo;
	private BigDecimal precoCombustivel;
	private List<Rota> rotas = new ArrayList<Rota>();
	private BigDecimal consumoSomado;
	private BigDecimal distanciaSomada;
	private String nomeGrafo;
	private boolean jaCalculado;
	private List<Cidade> cidadesQueDevemEstarNoCaminho = new ArrayList<Cidade>();
	private boolean cidadeVisitar;
	@Inject
	private GrafoDao grafoDao;
	
	public void consulta(){
		resultado = new ArrayList<ResultadoConsulta>();
		distanciaSomada = BigDecimal.valueOf(0L);
		consumoSomado  = BigDecimal.valueOf(0L);
		Grafo grafo = grafoDao.buscaGrafoPorNome(nomeGrafo);
		
		if(grafo != null){
			
			if(!rotasExcluidas.isEmpty()){
				List<Rota> rotasAux = new ArrayList<Rota>();
				for(Rota rota : rotasExcluidas){
					for(Rota rotaGrafo : grafo.getRotas()){
						if(rota.getOrigem().getNome().equals(rotaGrafo.getOrigem().getNome()) && 
								rota.getDestino().getNome().equals(rotaGrafo.getDestino().getNome())){
							rotasAux.add(rotaGrafo);
						}
						if(rota.getOrigem().getNome().equals(rotaGrafo.getDestino().getNome()) && 
								rota.getDestino().getNome().equals(rotaGrafo.getOrigem().getNome())){
							rotasAux.add(rotaGrafo);
						}
					}
				}
				
				grafo.getRotas().removeAll(rotasAux);
			}
			List<Cidade> cidadesDestino = new ArrayList<Cidade>();
			Cidade cidadeOrigemAtual = new Cidade(origemNome);
			
			if (!cidadesQueDevemEstarNoCaminho.isEmpty()){
				for(Cidade cidade : cidadesQueDevemEstarNoCaminho){
					if (!cidade.getNome().isEmpty())
						cidadesDestino.add(cidade);
				}
			}
			cidadesDestino.add(new Cidade(destinoNome));
			for(Cidade cidadeDestinoAtual : cidadesDestino){
				
			
			Cidade cidadeDestino = cidadeDestinoAtual;
			
			Grafo grafoAux = new Grafo(grafo.getCidades(),grafo.getRotas());
			Dijkstra dijkstra = new Dijkstra(grafoAux);
			dijkstra.inicia(cidadeOrigemAtual);
			cidadeOrigemAtual = cidadeDestino;
			setCaminhos(dijkstra.getCaminho(cidadeDestino));
			
			if(getCaminhos() != null){
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
				
				custoTotal = consumoSomado.multiply(precoCombustivel);
				custoTotal = custoTotal.setScale(2,RoundingMode.DOWN);
				jaCalculado = true;
			}
			else{
				FacesContext.getCurrentInstance().addMessage("messages", 
						new FacesMessage("Nenhum caminho encontrado"));
				jaCalculado = false;
				resultado = new ArrayList<ResultadoConsulta>();
			}
		}
			cidadesDestino = new ArrayList<Cidade>();
		}
		else{
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage("Mapa não encontrado."));
		}
	}
	
	public void addLinhaTabela(){
		Cidade cidade = new Cidade("");
		Cidade cidade2 = new Cidade("");
		Rota rota = new Rota(cidade,cidade2,0);
		rotasExcluidas.add(rota);
	}
	
	public void addLinhaTabelaCidades(){
		Cidade cidade = new Cidade("");
		cidadesQueDevemEstarNoCaminho.add(cidade);
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
		return jaCalculado;
	}


	public void setJaCalculado(boolean jaCalculado) {
		this.jaCalculado = jaCalculado;
	}

	public List<Cidade> getCidadesExcluidas() {
		return cidadesExcluidas;
	}

	public void setCidadesExcluidas(List<Cidade> cidadesExcluidas) {
		this.cidadesExcluidas = cidadesExcluidas;
	}

	public List<Rota> getRotasExcluidas() {
		return rotasExcluidas;
	}

	public void setRotasExcluidas(List<Rota> rotasExcluidas) {
		this.rotasExcluidas = rotasExcluidas;
	}

	public boolean getExcluiRota() {
		return excluiRota;
	}

	public void setExcluiRota(boolean excluiRota) {
		this.excluiRota = excluiRota;
	}

	public List<Cidade> getCidadesQueDevemEstarNoCaminho() {
		return cidadesQueDevemEstarNoCaminho;
	}

	public void setCidadesQueDevemEstarNoCaminho(List<Cidade> cidadesQueDevemEstarNoCaminho) {
		this.cidadesQueDevemEstarNoCaminho = cidadesQueDevemEstarNoCaminho;
	}

	public boolean getCidadeVisitar() {
		return cidadeVisitar;
	}

	public void setCidadeVisitar(boolean cidadeVisitar) {
		this.cidadeVisitar = cidadeVisitar;
	}

}
