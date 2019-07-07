package com.example.Vaadin7.service;

import java.util.List;

import com.example.Vaadin7.model.ProductEntity;

public interface ProductService {
	
	List<ProductEntity> findAllProducts();

}
