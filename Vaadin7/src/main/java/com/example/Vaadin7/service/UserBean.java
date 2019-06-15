package com.example.Vaadin7.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;

@RequestScoped
public class UserBean {
	
	@Inject
	PersistenceService persistenceSvc;
	
	public String sayHello() {
		return "Hello";
	}
	
	public void addUser(UserEntity userEntity) {
		persistenceSvc.persist(userEntity);
	}
	
}
