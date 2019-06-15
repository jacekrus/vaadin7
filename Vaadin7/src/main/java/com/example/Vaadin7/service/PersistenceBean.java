package com.example.Vaadin7.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersistenceBean implements PersistenceService {
	
	@PersistenceContext(unitName = "vaadin7")
	EntityManager em;

	@Override
	public void persist(Object obj) {
		em.persist(obj);
	}

}
