package com.example.Vaadin7.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.example.Vaadin7.model.ProductEntity;
import com.example.Vaadin7.service.DataAccessService;
import com.example.Vaadin7.service.ProductService;

@RequestScoped
public class ProductBean implements ProductService {
	
	@Inject
	DataAccessService dbSvc;

	@Override
	public List<ProductEntity> findAllProducts() {
		return dbSvc.findAllProducts();
	}

}
