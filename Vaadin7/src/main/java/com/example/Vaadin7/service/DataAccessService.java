package com.example.Vaadin7.service;

import java.util.List;

import com.example.Vaadin7.model.ProductEntity;
import com.example.Vaadin7.model.UserEntity;

public interface DataAccessService {
	
	void persist(Object obj);
	
	void remove(Object obj);
	
	List<UserEntity> findAllUsers();
	
	List<ProductEntity> findAllProducts();

}
