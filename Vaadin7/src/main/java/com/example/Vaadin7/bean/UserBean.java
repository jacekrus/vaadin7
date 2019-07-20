package com.example.Vaadin7.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.DataAccessService;
import com.example.Vaadin7.service.UserService;

@RequestScoped
public class UserBean implements UserService {
	
	@Inject
	DataAccessService dbSvc;
	
	@PostConstruct
	private void constructionCallback() {
		System.out.println("UserBean CREATED");
	}
	
	@PreDestroy
	private void destructionCallback() {
		System.out.println("UserBean DESTROYED");
	}
	
	@Override
	public void addUser(UserEntity userEntity) {
		dbSvc.persist(userEntity);
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return dbSvc.findAllUsers();
	}

	@Override
	public void removeUser(UserEntity userEntity) {
		if(!userEntity.getName().equals("admin")) {
			dbSvc.remove(userEntity);
		}
	}
	@Override
	public UserEntity findUserByName(String username) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
