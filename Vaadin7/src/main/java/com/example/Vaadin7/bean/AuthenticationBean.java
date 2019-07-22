package com.example.Vaadin7.bean;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.AuthenticationService;

@RequestScoped
public class AuthenticationBean implements AuthenticationService {
	
	@PersistenceContext(unitName = "vaadin7")
	EntityManager em;

	@Override
	public boolean login(String userName, String password) {
		TypedQuery<UserEntity> query = em.createQuery("select ue from UserEntity ue where ue.name = :userName", UserEntity.class).setParameter("userName", userName);
		try {
			UserEntity result = query.getSingleResult();
			return result == null ? false : authenticateUser(password, result);
		}
		catch (NoResultException  nre) {
			return false;
		}
	}
	
	@Override
	public String encryptPassword(String password) {
		return password;
	}
	
	private boolean authenticateUser(String password, UserEntity user) {
		return user.getPassword().equals(password);
	}

}
