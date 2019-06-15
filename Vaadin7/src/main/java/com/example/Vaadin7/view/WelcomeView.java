package com.example.Vaadin7.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@CDIView("welcome")
public class WelcomeView extends CustomComponent implements View {

	private static final long serialVersionUID = 2872061476700246009L;

	@Override
	public void enter(ViewChangeEvent event) {
		HorizontalLayout layout = new HorizontalLayout();
		Label label  = new Label("Default");
		layout.addComponent(label);
		setCompositionRoot(layout);
	}

}
