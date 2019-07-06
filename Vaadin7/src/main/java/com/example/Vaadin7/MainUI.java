package com.example.Vaadin7;

import javax.inject.Inject;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.ejt.vaadin.loginform.LoginForm.LoginEvent;
import com.ejt.vaadin.loginform.LoginForm.LoginListener;
import com.example.Vaadin7.model.NavigationNames;
import com.example.Vaadin7.service.AuthenticationService;
import com.example.Vaadin7.service.UserService;
import com.example.Vaadin7.view.AdminView;
import com.example.Vaadin7.view.DefaultView;
import com.example.Vaadin7.view.ShopView;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
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
	UserService userBean;
	
	@Inject
	AuthenticationService authSvc;
	
    
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
					VaadinSession.getCurrent().setAttribute("user", event.getUserName());
					showLoginNotification("Login successful!", Type.HUMANIZED_MESSAGE, Position.BOTTOM_CENTER);
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
		
		Button adminButton = new Button("Admin", e -> getNavigator().navigateTo(NavigationNames.ADMIN_VIEW));
		adminButton.setDescription("Admin");
		adminButton.setWidth("100%");
		Button shopButton = new Button("Shop", e -> getNavigator().navigateTo(NavigationNames.SHOP_VIEW));
		shopButton.setDescription("Shop");
		shopButton.setWidth("100%");
		Button logout = setUpLogoutButton(mainLayout);
		
		VerticalLayout menuButtons = new VerticalLayout(adminButton, shopButton, logout);
		menuButtons.setSpacing(true);
		menuButtons.setMargin(true);
		VerticalLayout menu = new VerticalLayout(title, menuButtons);
		menu.addStyleName(ValoTheme.MENU_ROOT);
		
		CssLayout viewContainer = new CssLayout();
		viewContainer.setSizeFull();
		
		mainLayout.addComponents(menu, viewContainer);
		mainLayout.setExpandRatio(menu, 1);
		mainLayout.setExpandRatio(viewContainer, 5);
		
		setUpNavigation(viewContainer);
	}
	
	private void showLoginNotification(String text, Type type, Position position) {
		Notification notification = new Notification(text, type);
		notification.setDelayMsec(500);
		notification.setPosition(position);
		notification.show(Page.getCurrent());
	}
	
	private void setUpNavigation(Layout container) {
		Navigator navigator = new Navigator(this, container);
		navigator.addView(NavigationNames.ADMIN_VIEW, AdminView.class);
		navigator.addView(NavigationNames.SHOP_VIEW, ShopView.class);
		navigator.addView(NavigationNames.DEFAULT, DefaultView.class);
		navigator.navigateTo(NavigationNames.DEFAULT);
		getNavigator().navigateTo(NavigationNames.DEFAULT);
	}
	
	private Button setUpLogoutButton(HorizontalLayout layout) {
		Button logout = new Button();
		logout.addStyleName("logoutButton");
		logout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logout.setWidth("55px");
		logout.setHeight("55px");
		logout.setDescription("Logout");
		logout.addClickListener(e -> {
			 VaadinSession.getCurrent().setAttribute("user", null);
			 Page.getCurrent().setLocation("");
			 setUpLoginView(layout);
		});
		return logout;
	}
	
	public static boolean isSessionActive() {
		return VaadinSession.getCurrent().getAttribute("user") != null;
	}
    
}
