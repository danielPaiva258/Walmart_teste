package br.com.walmart.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.walmart.model.Cidade;
import br.com.walmart.model.Grafo;
import br.com.walmart.model.Rota;

public class Dijkstra {

	private List<Rota> rotas;
	private List<Cidade> cidades;
	private Map<Cidade, Cidade> caminho;
	private Map<Cidade, Integer> distancia;
	private Set<Cidade> nosCalculados;
	private Set<Cidade> nosNaoCalculados;
	private Set<Cidade> nosCalculadosComoDestino;
	
	
	public Dijkstra (Grafo grafo){
		this.cidades = grafo.getCidades();
		this.rotas = grafo.getRotas();
		
	}
	
	public void inicia(Cidade origem){
		// instancia os mapas auxiliares com os tipos
		nosCalculados = new HashSet<Cidade>();
		nosCalculadosComoDestino = new HashSet<Cidade>();
		nosNaoCalculados = new HashSet<Cidade>();
		distancia = new HashMap<Cidade, Integer>();
		caminho = new HashMap<Cidade, Cidade>();
		//passo 1 do dijikstra , inicia origem com distancia 0
		distancia.put(origem, 0);
		//sinaliza que origem ainda nao tem caminho minimo para seus vizinhos
		nosNaoCalculados.add(origem);
		
		//Enquanto houver nos que nao foram calculados o caminho minimo
		while (nosNaoCalculados.size() > 0){
			//pega a cidade mais proximo de cada cidade presente no set nosNaoCalculados
			Cidade cidade = getNoMaisProximo(nosNaoCalculados);
			//sinaliza q menor caminho para essa cidade ja foi calculado
			nosCalculados.add(cidade);
			nosNaoCalculados.remove(cidade);
			
			encontraMenorCaminho(cidade);
		}
		
	}

	private void encontraMenorCaminho(Cidade cidade) {
		//pega todas as cidades vizinhas de acordo com as rotas passadas
		List<Cidade> cidadesVizinhas = getVizinhos(cidade);
		
		//Calcula a cidade vizinha mais proxima de acordo com o passo 3 do dijkstra e se a cidade ainda nao tem distancia
		//calculada é colocada a distancia infinito(Integer.MAX_VALUE) de acordo com o passo 1, adminitindo-se a pior estimativa
		//mapeia no Map distancia o valor em km da cidade de origem para o nó calculado como menor caminho, o salva como caminho.
		//e o coloca como nó nao calculado para repetir o processo com esse novo vizinho
		for(Cidade destino : cidadesVizinhas){
			if(getMenorDistancia(destino) > getMenorDistancia(cidade) + getDistancia(cidade, destino)){
				distancia.put(destino, getMenorDistancia(cidade) + getDistancia(cidade, destino));
				caminho.put(destino, cidade);
				nosNaoCalculados.add(destino);
			}
		}
		
	}

	private List<Cidade> getVizinhos(Cidade cidade) {
		List<Cidade> vizinhas = new ArrayList<Cidade>();
		
		//percorre lista de rotas disponiveis e se a cidade de origem for a cidade que busca vizinhos
		//e a distancia entre a origem e o viznho nao foi calculado sinaliza essa cidade como vizinho
		for( Rota rota: this.rotas){
			if (rota.getOrigem().equals(cidade) && !nosCalculados.contains(rota.getDestino())){
				vizinhas.add(rota.getDestino());
			}
			
		}
		
		return vizinhas;
	}

	public int getDistancia(Cidade cidade, Cidade destino) {
		//confere mapa de rotas e pega a distancia da rota entre as cidades vizinhas passadas no parametro
		for (Rota rota : this.rotas){
			if(rota.getOrigem().equals(cidade) && rota.getDestino().equals(destino)){
				return rota.getDistancia();
			}
		}
		return Integer.MAX_VALUE;
	}

	private Cidade getNoMaisProximo(Set<Cidade> nosParaSeremCalculados) {
		Cidade cidadeMaisProxima = null;
		
		for (Cidade cidade : nosParaSeremCalculados){
			//se ainda nao houver cidadeMaisProxima adiciona a primeira do set(qualquer)
			if (cidadeMaisProxima == null) {
				cidadeMaisProxima = cidade;
			}
			// se ja houver uma cidade calculada, calcula a distancia da proxima cidade do set de nosNaoCalculados e
			//se a distancia for menor então foi encontrado uma nova cidadeMaisProxima
			else if(getMenorDistancia(cidade) < getMenorDistancia(cidadeMaisProxima)){
				cidadeMaisProxima = cidade;
			}
		}
		return cidadeMaisProxima;
	}

	private int getMenorDistancia(Cidade destino) {
		//pega a distancia entre o no destino e a origem se ja houver sido cadastrado 
		Integer distancia = this.distancia.get(destino);
		
		//se distancia é nula, então não há conexão entre as cidades
		if(distancia == null) {
			return Integer.MAX_VALUE;
		}
		return distancia;
	}
	
	public LinkedList<Cidade> getCaminho(Cidade destino){
		LinkedList<Cidade> caminho = new LinkedList<Cidade>();
		
		Cidade cidadeAux = destino;
		//se nao existe conexao entre as cidades
		if(this.caminho.get(cidadeAux) == null){
			return null;
		}
		
		caminho.add(cidadeAux);
		
		while (this.caminho.get(cidadeAux) != null){
			cidadeAux = this.caminho.get(cidadeAux);
			caminho.add(cidadeAux);
		}
		
		Collections.reverse(caminho);
		return caminho;
	}
}
