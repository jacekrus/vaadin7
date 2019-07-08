package com.example.Vaadin7;

import javax.inject.Inject;

import com.example.Vaadin7.service.AuthenticationService;
import com.example.Vaadin7.view.LoginView;
import com.example.Vaadin7.view.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@Theme("mytheme")
@CDIUI("")
public class MainUI extends UI {
	
	private static final long serialVersionUID = 483656132564722441L;
	
	@Inject
	private AuthenticationService authSvc;
	
	@Inject
	private CDIViewProvider viewProvider;
    
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		HorizontalLayout mainLayout = new HorizontalLayout();
		if(isSessionActive()) {
			setUpMainView(mainLayout);
		}
		else {
			setUpLoginView(mainLayout);
		}
    	mainLayout.setSizeFull();
    	setSizeFull();
    	setContent(mainLayout);
    }
	
	private void setUpLoginView(HorizontalLayout mainLayout) {
		mainLayout.removeAllComponents();
		LoginView loginView = new LoginView(authSvc, () -> setUpMainView(mainLayout));
        mainLayout.addComponent(loginView);
        mainLayout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);
	}
	
	private void setUpMainView(HorizontalLayout mainLayout) {
		mainLayout.removeAllComponents();
		MainView mainView = new MainView(viewProvider, mainLayout, () -> setUpLoginView(mainLayout));
		mainLayout.addComponent(mainView);
	}
	
	private boolean isSessionActive() {
		return VaadinSession.getCurrent().getAttribute("user") != null;
	}
	
}
