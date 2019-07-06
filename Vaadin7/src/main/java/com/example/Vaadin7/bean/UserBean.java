package com.example.Vaadin7.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.DataAccessService;
import com.example.Vaadin7.service.UserService;

@RequestScoped
public class UserBean implements UserService {
	
	@Inject
	DataAccessService dbSvc;
	
	@Override
	public void addUser(UserEntity userEntity) {
		dbSvc.persist(userEntity);
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return dbSvc.findAllUsers();
	}
	
}
