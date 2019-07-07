package com.example.Vaadin7.view;

import java.util.List;

import javax.inject.Inject;

import com.example.Vaadin7.model.NavigationNames;
import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.UserService;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(NavigationNames.ADMIN_VIEW)
public class AdminView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UserService userSvc;
	
	private List<UserEntity> users;

	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("ADMINVIEW");
        layout.setSizeFull();

        VerticalLayout gridContainer = new VerticalLayout();
        gridContainer.setSpacing(true);
        Button addUserButton = new Button("Add user");
        addUserButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addUserButton.addClickListener(e -> showAddUserDialog(gridContainer));
        Grid usersGrid = setUpUsersGrid();
        gridContainer.addComponents(addUserButton, usersGrid);

        layout.addComponent(gridContainer);
        layout.setSpacing(true);
        layout.setMargin(true);
        
        setSizeFull();
        setCompositionRoot(layout);
	}
	
	private Grid setUpUsersGrid() {
		Grid usersGrid = new Grid();
		usersGrid.addColumn("ID", Long.class);
		usersGrid.addColumn("Login", String.class);
		usersGrid.addColumn("Password", String.class);
		users = userSvc.findAllUsers();
		users.forEach(user -> usersGrid.addRow(user.getId(), user.getName(), user.getPassword()));
		return usersGrid;
	}
	
	private void showAddUserDialog(VerticalLayout layout) {
		VerticalLayout dialogContent = new VerticalLayout();
		dialogContent.setMargin(true);
		dialogContent.setSpacing(true);

		PopupView dialog = new PopupView("", dialogContent);
		
		TextField usernameField = new TextField("Username: ");
		dialogContent.addComponent(usernameField);
		
		TextField passwordField = new TextField("Password: ");
		dialogContent.addComponent(passwordField);
		
		Button saveButton = new Button("Save");
		saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		saveButton.addClickListener(e -> {
			if(!usernameField.isEmpty() && !passwordField.isEmpty()) {
				UserEntity newUser = new UserEntity();
				newUser.setName(usernameField.getValue());
				newUser.setPassword(passwordField.getValue());
				if(!isUserAlreadyExists(usernameField.getValue())) {
					userSvc.addUser(newUser);
				}
			}
			dialog.setPopupVisible(false);
		});
		dialogContent.addComponent(saveButton);
		
		layout.addComponent(dialog);
		dialog.setPopupVisible(true);
	}
	
	private boolean isUserAlreadyExists(String username) {
		return users.stream().anyMatch(user -> user.getName().equals(username));
	}
	
}
