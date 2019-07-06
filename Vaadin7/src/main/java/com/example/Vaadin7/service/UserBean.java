package com.example.Vaadin7.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;

@RequestScoped
public class UserBean implements UserService {
	
	@Inject
	DataAccessService dbSvc;
	
	public String sayHello() {
		return "Hello";
	}
	
	public void addUser(UserEntity userEntity) {
		dbSvc.persist(userEntity);
	}

	@Override
	public List<UserEntity> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
