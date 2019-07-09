package com.example.Vaadin7.widget;

import com.example.Vaadin7.service.ShoppingCartService;
import com.example.Vaadin7.utils.RemoveOption;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class CartItem extends HorizontalLayout {
	
	private static final long serialVersionUID = -1625975583745498375L;

	private ShoppingCartService cartSvc;
	
	private Label countLabel;
	
	public CartItem(String productName, Long count, ShoppingCartService cartSvc) {
		this.cartSvc = cartSvc;
		this.setMargin(true);
		this.setSpacing(true);
		
		Label name = new Label(productName);
		name.addStyleName("fontBold");
		this.addComponent(name);
		
		this.addComponent(new Label(" X "));
		
		this.countLabel = new Label(count.toString());
		this.addComponent(countLabel);
		
		HorizontalLayout buttonsContainer = new HorizontalLayout();
		Button removeWholeItemButton = new Button("x");
		removeWholeItemButton.addStyleName(ValoTheme.BUTTON_DANGER);
		removeWholeItemButton.setHeight("25px");
		removeWholeItemButton.setWidth("25px");
		removeWholeItemButton.addClickListener(e -> removeWholeItem(productName));
		
		Button removeSingleItemButton = new Button("-");
		removeSingleItemButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		removeSingleItemButton.setHeight("25px");
		removeSingleItemButton.setWidth("25px");
		removeSingleItemButton.addClickListener(e -> removeSingleItem(productName, count));
		buttonsContainer.addComponents(removeWholeItemButton, removeSingleItemButton);
		this.addComponent(buttonsContainer);
	}
	
	private void removeWholeItem(String productName) {
		cartSvc.removeProductFromCart(productName, RemoveOption.WHOLE);
		this.setVisible(false);
	}
	
	private void removeSingleItem(String productName, Long count) {
		cartSvc.removeProductFromCart(productName, RemoveOption.SINGLE);
		count--;
		countLabel = new Label(count.toString());
		this.setVisible(count > 0);
	}

}
