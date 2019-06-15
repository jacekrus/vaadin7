package com.example.Vaadin7.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.Vaadin7.model.UserEntity;

@Stateless
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
	
	private boolean authenticateUser(String password, UserEntity user) {
		return user.getPassword().equals(password);
	}
	
	public String encryptPassword(String password) {
		return doEncrypt(password);
	}
	
	private String doEncrypt(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = password.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

}
