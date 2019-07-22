package com.example.Vaadin7.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.example.Vaadin7.event.UserAddedEvent;
import com.example.Vaadin7.event.UserDeletedEvent;
import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.AuthenticationService;
import com.example.Vaadin7.service.DataAccessService;
import com.example.Vaadin7.service.UserService;

@RequestScoped
public class UserBean implements UserService {
	
	@Inject
	DataAccessService dbSvc;
	
	@Inject
	private Event<UserAddedEvent> userAddedEvent;
	
	@Inject
	private Event<UserDeletedEvent> userDeletedEvent;
	
	@Inject
	private Instance<AuthenticationService> authSvc;
	
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
		userEntity.setPassword(authSvc.get().encryptPassword(userEntity.getPassword()));
		dbSvc.persist(userEntity);
		userAddedEvent.fire(new UserAddedEvent(userEntity.getId(), userEntity.getName(), userEntity.getPassword()));
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return dbSvc.findAllUsers();
	}

	@Override
	public void removeUser(UserEntity userEntity) {
		if(!userEntity.getName().equals("admin")) {
			dbSvc.remove(userEntity);
			userDeletedEvent.fire(new UserDeletedEvent(userEntity.getId()));
		}
	}
	
	@Override
	public UserEntity findUserByName(String username) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	
}
