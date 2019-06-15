package com.example.Vaadin7;

import javax.inject.Inject;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.ejt.vaadin.loginform.LoginForm.LoginEvent;
import com.ejt.vaadin.loginform.LoginForm.LoginListener;
import com.example.Vaadin7.model.NavigationNames;
import com.example.Vaadin7.service.AuthenticationService;
import com.example.Vaadin7.service.UserBean;
import com.example.Vaadin7.view.AdminView;
import com.example.Vaadin7.view.DefaultView;
import com.example.Vaadin7.view.WelcomeView;
import com.example.Vaadin7.view.PoolJavaSnookerView;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
@CDIUI("")
public class MainUI extends UI {
	
	private static final long serialVersionUID = 483656132564722441L;
	
	@Inject
	UserBean userBean;
	
	@Inject
	AuthenticationService authSvc;
	
    
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		HorizontalLayout mainLayout = new HorizontalLayout();
//    	setUpLoginView(mainLayout);
    	setUpMainView(mainLayout);
    	mainLayout.setSizeFull();
    	setSizeFull();
    	setContent(mainLayout);
    }

	@SuppressWarnings("serial")
	private void setUpLoginView(HorizontalLayout mainLayout) {
		mainLayout.removeAllComponents();
        DefaultVerticalLoginForm loginForm = new DefaultVerticalLoginForm();
        loginForm.addLoginListener(new LoginListener() {
			
			@Override
			public void onLogin(LoginEvent event) {
				boolean login = authSvc.login(event.getUserName(), event.getPassword());
				if(login) {
					setUpMainView(mainLayout);
					showLoginNotification("Welcome, " + event.getUserName(), Type.HUMANIZED_MESSAGE, Position.MIDDLE_CENTER);
				}
				else {
					showLoginNotification("Wrong username or password", Type.ERROR_MESSAGE, Position.BOTTOM_CENTER);
				}
			}
		});
        mainLayout.addComponent(loginForm);
        mainLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}
	
	private void setUpMainView(HorizontalLayout mainLayout) {
		mainLayout.removeAllComponents();
		Label title = new Label("Menu");
		title.addStyleName(ValoTheme.MENU_TITLE);
		
		Button button = new Button("Admin", e -> getNavigator().navigateTo(NavigationNames.ADMIN_VIEW));
		button.setDescription("Admin");
		button.setWidth("100%");
		Button button2 = new Button("Shop", e -> getNavigator().navigateTo(NavigationNames.POOL_JAVA_SNOOKER_VIEW));
		button2.setDescription("Shop");
		button2.setWidth("100%");
		
		VerticalLayout menuButtons = new VerticalLayout(button, button2);
		menuButtons.setSpacing(true);
		menuButtons.setMargin(true);
		VerticalLayout menu = new VerticalLayout(title, menuButtons);
		menu.addStyleName(ValoTheme.MENU_ROOT);
		
		CssLayout viewContainer = new CssLayout();
		
		mainLayout.addComponents(menu, viewContainer);
		mainLayout.setExpandRatio(menu, 1);
		mainLayout.setExpandRatio(viewContainer, 5);
		
		Navigator navigator = new Navigator(this, viewContainer);
		navigator.addView(NavigationNames.ADMIN_VIEW, AdminView.class);
		navigator.addView(NavigationNames.POOL_JAVA_SNOOKER_VIEW, PoolJavaSnookerView.class);
		navigator.addView("", DefaultView.class);
//		navigator.addView(NavigationNames.WELCOME, WelcomeView.class);
		navigator.navigateTo(NavigationNames.ADMIN_VIEW);
		getNavigator().navigateTo(NavigationNames.ADMIN_VIEW);
	}
	
	private void showLoginNotification(String text, Type type, Position position) {
		Notification notification = new Notification(text, type);
		notification.setDelayMsec(1000);
		notification.setPosition(position);
		notification.show(Page.getCurrent());
	}
    
}
