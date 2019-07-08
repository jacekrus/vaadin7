package com.example.Vaadin7.view;

import javax.inject.Inject;

import com.example.Vaadin7.model.NavigationNames;
import com.example.Vaadin7.service.ProductService;
import com.example.Vaadin7.widget.ProductFrame;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@CDIView(NavigationNames.SHOP_VIEW)
@StyleSheet(NavigationNames.STYLESHEET)
public class ShopView extends CustomComponent implements View {

	private static final long serialVersionUID = -7480398079308043714L;
	
	@Inject
	private ProductService prodSvc;

	@Override
	public void enter(ViewChangeEvent event) {
		AbsoluteLayout layout = new AbsoluteLayout();
		layout.setSizeFull();
		
		GridLayout productsGrid = new GridLayout(3,2);
		productsGrid.setSpacing(true);
		productsGrid.setMargin(true);
		productsGrid.setWidth("80%");
		
		Button cartButton = setUpShoppingCartButton();
		
		prodSvc.findAllProducts().forEach(product -> productsGrid.addComponent(new ProductFrame(product)));
		
		layout.addComponent(productsGrid, "left: 150px; top: 50px");
		layout.addComponent(cartButton, "right: 20px; top: 20px");
		
		setSizeFull();
		setCompositionRoot(layout);
	}
	
	private Button setUpShoppingCartButton() {
		Button cartButton = new Button();
		cartButton.addStyleName("cartIcon");
		cartButton.setWidth("60px");
		cartButton.setHeight("60px");
		cartButton.addClickListener(e -> showCartContent());
		return cartButton;
	}
	
	private void showCartContent() {
		VerticalLayout dialogContent = new VerticalLayout();
		dialogContent.setMargin(true);
		dialogContent.setSpacing(true);
		dialogContent.addComponent(new Label("TEST"));
		
		Window cartDialog = new Window();
		cartDialog.setContent(dialogContent);
		cartDialog.center();
		cartDialog.setResizable(false);
		cartDialog.setModal(true);
		
		UI.getCurrent().addWindow(cartDialog);
		cartDialog.focus();
	}

}
