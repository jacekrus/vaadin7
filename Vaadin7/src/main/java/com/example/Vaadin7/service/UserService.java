package com.example.Vaadin7.service;

import java.util.List;

import com.example.Vaadin7.model.UserEntity;

public interface UserService {
	
	void addUser(UserEntity userEntity);
	
	void removeUser(UserEntity userEntity);
	
	List<UserEntity> findAllUsers(); 
	
	UserEntity findUserByName(String username);
	
}
