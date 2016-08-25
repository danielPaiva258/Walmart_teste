package br.com.walmart.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.walmart.model.Cidade;
import br.com.walmart.model.Grafo;
import br.com.walmart.model.Rota;

public class teste {

	private List<Cidade> cidades;
	  private List<Rota> rotas;

	  public void testExcute() {
	    cidades = new ArrayList<Cidade>();
	    rotas = new ArrayList<Rota>();

	    Cidade A = new Cidade("A");
	    Cidade B = new Cidade("B");
	    Cidade C = new Cidade("C");
//	    Cidade D = new Cidade("D");
//	    Cidade E = new Cidade("E");
	    
	    adicionaRota(A, B,100);
	    adicionaRota(B, C,15);
	    adicionaRota(C, A,20);
//	    adicionaRota(C, D,30);
//	    adicionaRota(B, E,50);
//	    adicionaRota(D, E,30);

	    cidades.add(A);
	    cidades.add(B);
	    cidades.add(C);
	    Grafo grafo = new Grafo(cidades,rotas);
	    Dijkstra dijkstra = new Dijkstra(grafo);
	    
	    
	    dijkstra.inicia(A);
	    LinkedList<Cidade> path = dijkstra.getCaminho(B);
	    
	    
	    for (Cidade cidade : path) {
	      System.out.print(cidade.getNome() + "->");
	    }
	    
	    
	  }

	  private void adicionaRota(Cidade origem, Cidade destino, Integer distancia) {
	    Rota lane = new Rota(origem, destino, distancia);
	    rotas.add(lane);
	  }
	  
	  public static void main (String [] args){
		  teste testes = new teste();
		  testes.testExcute();
	  }

}
