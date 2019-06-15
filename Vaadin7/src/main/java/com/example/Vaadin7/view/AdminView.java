package com.example.Vaadin7.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

@CDIView("adminView")
public class AdminView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        ProgressBar bar = new ProgressBar();
        layout.addComponent(bar);
        setSizeFull();
        setCompositionRoot(layout);
	}

}
