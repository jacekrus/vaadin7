package com.example.Vaadin7.service;

import java.util.Map;

import com.example.Vaadin7.model.ProductEntity;
import com.example.Vaadin7.utils.RemoveOption;

public interface ShoppingCartService {
	
	Map<String, Long> getProductsInCart();
	
	void addProductToCart(ProductEntity product);
	
	void removeProductFromCart(String productName, RemoveOption option);
	
	boolean isEmpty();

}
