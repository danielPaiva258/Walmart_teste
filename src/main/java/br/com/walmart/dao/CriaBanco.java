package br.com.walmart.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriaBanco {

	public static void main (String args []){
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("walmart");
		factory.close();
	}
}
