package com.example.Vaadin7.view;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.example.Vaadin7.callback.LoginCallback;
import com.example.Vaadin7.service.AuthenticationService;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class LoginView extends DefaultVerticalLoginForm {

	private static final long serialVersionUID = -7781949587317897538L;
	
	private AuthenticationService authSvc;
	
	private LoginCallback loginCallback;
	
	public LoginView(AuthenticationService authSvc, LoginCallback loginCallback) {
		this.authSvc = authSvc;
		this.loginCallback = loginCallback;
		init();
	}
	
	@SuppressWarnings("serial")
	private void init() {
        this.addLoginListener(new LoginListener() {
			
			@Override
			public void onLogin(LoginEvent event) {
				boolean userAuthenticated = authSvc.login(event.getUserName(), event.getPassword());
				if(userAuthenticated) {
					VaadinSession.getCurrent().setAttribute("user", event.getUserName());
					loginCallback.onLogin();
					showLoginNotification("Login successful!", Type.HUMANIZED_MESSAGE, Position.BOTTOM_CENTER);
				}
				else {
					showLoginNotification("Wrong username or password", Type.ERROR_MESSAGE, Position.BOTTOM_CENTER);
				}
			}
		});
	}

	private void showLoginNotification(String text, Type type, Position position) {
		Notification notification = new Notification(text, type);
		notification.setDelayMsec(500);
		notification.setPosition(position);
		notification.show(Page.getCurrent());
	}

}
