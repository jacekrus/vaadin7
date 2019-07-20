package com.example.Vaadin7.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.example.Vaadin7.model.ProductEntity;
import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.DataAccessService;
import com.example.Vaadin7.service.StatisticsService;

@Stateless
public class DataAccessBean implements DataAccessService {
	
	@PersistenceContext(unitName = "vaadin7")
	private EntityManager em;
	
	@Inject
	private StatisticsService statSvc;

	@Override
	public void persist(Object obj) {
		em.persist(obj);
		statSvc.incrementDBConnectionsNumber();
	}

	@Override
	public List<UserEntity> findAllUsers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		Root<UserEntity> root = cq.from(UserEntity.class);
		CriteriaQuery<UserEntity> query = cq.select(root);
		TypedQuery<UserEntity> createQuery = em.createQuery(query);
		statSvc.incrementDBConnectionsNumber();
		return createQuery.getResultList();
	}

	@Override
	public List<ProductEntity> findAllProducts() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> root = cq.from(ProductEntity.class);
		CriteriaQuery<ProductEntity> query = cq.select(root);
		TypedQuery<ProductEntity> createQuery = em.createQuery(query);
		statSvc.incrementDBConnectionsNumber();
		return createQuery.getResultList();
	}

	@Override
	public void remove(Object obj) {
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		statSvc.incrementDBConnectionsNumber();
	}

}
