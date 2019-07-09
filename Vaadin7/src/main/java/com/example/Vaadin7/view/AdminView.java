package com.example.Vaadin7.view;

import java.util.List;

import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.UserService;
import com.example.Vaadin7.utils.NavigationNames;
import com.example.Vaadin7.widget.AddUserDialog;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(NavigationNames.ADMIN_VIEW)
public class AdminView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userSvc;
	
	private List<UserEntity> users;

	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addStyleName("marginLeft");
        
        VerticalLayout gridContainer = new VerticalLayout();
        gridContainer.setSpacing(true);
        Button addUserButton = new Button("Add user");
        addUserButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addUserButton.addClickListener(e -> showAddUserDialog());
        Grid usersGrid = setUpUsersGrid();
        gridContainer.addComponents(addUserButton, usersGrid);

        layout.addComponent(gridContainer);
        
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
	
	private void showAddUserDialog() {
		AddUserDialog addUserDialog = new AddUserDialog(userSvc, users);
		UI.getCurrent().addWindow(addUserDialog);
		addUserDialog.focus();
	}
	
}
