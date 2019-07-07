package com.example.Vaadin7.view;

import javax.inject.Inject;

import com.example.Vaadin7.model.NavigationNames;
import com.example.Vaadin7.service.ProductService;
import com.example.Vaadin7.widget.ProductFrame;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;

@CDIView(NavigationNames.SHOP_VIEW)
@StyleSheet(NavigationNames.STYLESHEET)
public class ShopView extends CustomComponent implements View {

	private static final long serialVersionUID = -7480398079308043714L;
	
	@Inject
	ProductService prodSvc;

	@Override
	public void enter(ViewChangeEvent event) {
		GridLayout layout = new GridLayout(3,2);
		layout.setWidth("60%");
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.addStyleName("marginLeft");
		
		prodSvc.findAllProducts().forEach(product -> layout.addComponent(new ProductFrame(product)));
		
		setSizeFull();
		setCompositionRoot(layout);
	}

}
