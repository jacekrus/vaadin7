package com.example.Vaadin7.widget;

import com.example.Vaadin7.model.ProductEntity;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ProductFrame extends VerticalLayout {

	private static final long serialVersionUID = 7255583790582103624L;
	
	public ProductFrame(ProductEntity product) {
		this.setSpacing(true);
		this.setWidth("136px");
		this.addStyleName("productFrame");
		this.setDescription(product.getName());
		
		Embedded productImg = new Embedded(null, new ThemeResource("img/" + product.getMiniatureImg()));
		
		Button cartButton = new Button();
		cartButton.addStyleName("cartImg");
		cartButton.setWidth("132px");
		cartButton.setHeight("37px");
		
		Label productName = new Label(product.getName());
		productName.addStyleName("fontBold");
		Label price = new Label(product.getPrice().toString());
		VerticalLayout nameAndPriceContainer = new VerticalLayout(productName, price);
		
		this.addComponents(productImg, nameAndPriceContainer, cartButton);
	}

}
