package br.com.walmart.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.walmart.model.Grafo;

public class GrafoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DAO<Grafo> dao;

	@PostConstruct
	void init() {
		dao = new DAO<>(Grafo.class);
	}

	public Grafo buscaGrafoPorNome(String nome) {

		EntityManager em = new JPAUtil().getEntityManager();

		TypedQuery<Grafo> query = em.createQuery("select g from Grafo g " + " where g.nome = :pNome", Grafo.class);

		query.setParameter("pNome", nome);
		try{
			Grafo resultado = query.getSingleResult();
			return resultado;
		}catch (NoResultException nre){
			return null;
		}
	}

	public void adiciona(Grafo t) {
		dao.adiciona(t);
	}

	public void remove(Grafo t) {
		dao.remove(t);
	}

	public void atualiza(Grafo t) {
		dao.atualiza(t);
	}

	public List<Grafo> listaTodos() {
		return dao.listaTodos();
	}

	public Grafo buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
}
