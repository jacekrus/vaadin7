package com.example.Vaadin7.view;

import com.example.Vaadin7.callback.LogoutCallback;
import com.example.Vaadin7.model.NavigationNames;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainView extends CustomComponent {
	
	private static final long serialVersionUID = 1464389254267017629L;

	private CDIViewProvider viewProvider;
	
	private LogoutCallback logoutCallback;
	
	private HorizontalLayout parentLayout;
	
	public MainView(CDIViewProvider viewProvider, HorizontalLayout parentLayout, LogoutCallback logoutCallback) {
		this.viewProvider = viewProvider;
		this.parentLayout = parentLayout;
		this.logoutCallback = logoutCallback;
		init();
	}
	
	public void setParentLayout(HorizontalLayout parentLayout) {
		this.parentLayout = parentLayout;
	}

	private void init() {
		Label title = new Label("Menu");
		title.addStyleName(ValoTheme.MENU_TITLE);
		
		Button adminButton = new Button("Admin", e -> UI.getCurrent().getNavigator().navigateTo(NavigationNames.ADMIN_VIEW));
		adminButton.setDescription("Admin");
		adminButton.setWidth("100%");
		Button shopButton = new Button("Shop", e -> UI.getCurrent().getNavigator().navigateTo(NavigationNames.SHOP_VIEW));
		shopButton.setDescription("Shop");
		shopButton.setWidth("100%");
		Button logoutButton = setUpLogoutButton();
		
		VerticalLayout menuButtons = new VerticalLayout();
		if("admin".equals(getLoggedInUsername())) {
			menuButtons.addComponent(adminButton);
		}
		menuButtons.addComponents(shopButton, logoutButton);
		menuButtons.setSpacing(true);
		menuButtons.setMargin(true);
		VerticalLayout menu = new VerticalLayout(title, menuButtons);
		menu.addStyleName(ValoTheme.MENU_ROOT);
		
		HorizontalLayout viewContainer = new HorizontalLayout();
		viewContainer.setSizeFull();
		viewContainer.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		parentLayout.addComponents(menu, viewContainer);
		parentLayout.setExpandRatio(menu, 1);
		parentLayout.setExpandRatio(viewContainer, 5);
		
		setUpNavigation(viewContainer);
	}
	
	private Button setUpLogoutButton() {
		Button logout = new Button();
		logout.addStyleName("logoutButton");
		logout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logout.setWidth("55px");
		logout.setHeight("55px");
		logout.setDescription("Logout");
		logout.addClickListener(e -> {
			 VaadinSession.getCurrent().setAttribute("user", null);
			 Page.getCurrent().setLocation("");
			 logoutCallback.onLogout();
		});
		return logout;
	}
	
	private void setUpNavigation(Layout container) {
		Navigator navigator = new Navigator(UI.getCurrent(), container);
		navigator.addProvider(viewProvider);
		navigator.navigateTo(NavigationNames.DEFAULT);
	}
	
	private String getLoggedInUsername() {
		return VaadinSession.getCurrent().getAttribute("user").toString();
	}

}
