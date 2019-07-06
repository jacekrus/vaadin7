package com.example.Vaadin7.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.DataAccessService;

@Stateless
public class DataAccessBean implements DataAccessService {
	
	@PersistenceContext(unitName = "vaadin7")
	EntityManager em;

	@Override
	public void persist(Object obj) {
		em.persist(obj);
	}

	@Override
	public List<UserEntity> findAllUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> root = cq.from(UserEntity.class);
		CriteriaQuery<UserEntity> query = cq.select(root);
		TypedQuery<UserEntity> createQuery = em.createQuery(query);
		return createQuery.getResultList();
	}

}
