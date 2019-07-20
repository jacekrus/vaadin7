package com.example.Vaadin7.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

import com.example.Vaadin7.model.ProductEntity;
import com.example.Vaadin7.service.ShoppingCartService;
import com.example.Vaadin7.utils.RemoveOption;
import com.example.Vaadin7.utils.RemoveOption.RemoveOptionVisitor;

@SessionScoped
public class ShoppingCartBean implements ShoppingCartService, Serializable {

	private static final long serialVersionUID = -7322158913457105616L;
	
	private Map<String, Long> productsInCart;
	
	@PostConstruct
	private void init() {
		productsInCart = new HashMap<>();
		System.out.println("ShoppingCartBean CREATED");
	}
	
	@PreDestroy
	private void destructionCallback() {
		System.out.println("ShoppingCartBean DESTROYED");
	}
	
	@Override
	public Map<String, Long> getProductsInCart() {
		return productsInCart;
	}
	
	@Override
	public void addProductToCart(ProductEntity product) {
		if(productsInCart.get(product.getName()) == null) {
			productsInCart.put(product.getName(), 1L);
		}
		else {
			productsInCart.put(product.getName(), (productsInCart.get(product.getName()) + 1));
		}
	}

	@Override
	public void removeProductFromCart(String productName, RemoveOption option) {
		if(productsInCart.containsKey(productName)) { 
			option.accept(new RemoveOptionVisitor<Void>() {
				@Override
				public Void visitWhole() {
					productsInCart.remove(productName);
					return null;
				}

				@Override
				public Void visitSingle() {
					Long newCount = productsInCart.get(productName) - 1;
					if(newCount > 0) {
						productsInCart.put(productName, newCount);
					}
					else {
						productsInCart.remove(productName);
					}
					return null;
				}
			});
		}
	}

	@Override
	public boolean isEmpty() {
		return productsInCart.isEmpty();
	}
	
}
