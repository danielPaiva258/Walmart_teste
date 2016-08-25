package br.com.walmart.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;

import br.com.walmart.model.Cidade;

public class DAO <T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	
	EntityManager manager = new JPAUtil().getEntityManager();
	
	public DAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public void adiciona(T t){
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		
	}
	
	public void remove (T t){
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.remove(manager.merge(t));
		manager.getTransaction().commit();
	}
	
	public void atualiza (T t){
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();
		
	}
	
	
	public List<T> listaTodos(){
		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		
		List<T> lista = manager.createQuery(query).getResultList();
		
		return lista;
	}
	
	public T buscaPorId(Integer id){
		T t = manager.find(classe, id);
		
		return t;
	}

	public void adicionaLista(List<T> list) {
		if (list != null){
			EntityManager manager = new JPAUtil().getEntityManager();
			manager.getTransaction().begin();
			for (Object object : list){
				manager.persist(object);
			}
			manager.getTransaction().commit();
		}
		
	}
}
