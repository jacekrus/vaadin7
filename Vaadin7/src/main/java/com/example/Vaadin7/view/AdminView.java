package com.example.Vaadin7.view;

import java.util.List;

import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.service.UserService;
import com.example.Vaadin7.utils.NavigationNames;
import com.example.Vaadin7.widget.AddUserDialog;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(NavigationNames.ADMIN_VIEW)
public class AdminView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userSvc;
	
	private List<UserEntity> users;
	
	private Button removeUserButton;
	
	private Grid usersGrid;
	
	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addStyleName("marginLeft");
        
        this.usersGrid = setUpUsersGrid();
        
        VerticalLayout gridContainer = new VerticalLayout();
        gridContainer.setSpacing(true);
        
        HorizontalLayout buttonsContainer = new HorizontalLayout();
        buttonsContainer.setSpacing(true);
        Button addUserButton = new Button("Add user");
        addUserButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addUserButton.addClickListener(e -> showAddUserDialog());

        this.removeUserButton = new Button("Remove user");
        removeUserButton.addStyleName(ValoTheme.BUTTON_DANGER);
        removeUserButton.setEnabled(false);
//        removeUserButton.addClickListener(e -> removeUser((Integer) usersGrid.getSelectedRow()));
        removeUserButton.addClickListener(e -> Notification.show("Not implemented yet"));
        
        buttonsContainer.addComponents(addUserButton, removeUserButton);
        
        gridContainer.addComponents(buttonsContainer, usersGrid);
        layout.addComponent(gridContainer);
        
        setSizeFull();
        setCompositionRoot(layout);
	}
	
	private Grid setUpUsersGrid() {
		Grid usersGrid = new Grid();
		usersGrid.addColumn("ID", Long.class);
		usersGrid.addColumn("Login", String.class);
		usersGrid.addColumn("Password", String.class);
        usersGrid.setSelectionMode(SelectionMode.SINGLE);
        usersGrid.addSelectionListener(new SelectionListener() {
        	
			private static final long serialVersionUID = 864122493588754309L;

			@Override
			public void select(SelectionEvent event) {
				Integer selectedUserId = (Integer) usersGrid.getSelectedRow();
				if(selectedUserId == null || selectedUserId == 1) {
					removeUserButton.setEnabled(false);
				}
				else {
					removeUserButton.setEnabled(true);
				}
			}
		});
		users = userSvc.findAllUsers();
		users.forEach(user -> usersGrid.addRow(user.getId(), user.getName(), user.getPassword()));
		return usersGrid;
	}
	
	private void showAddUserDialog() {
		AddUserDialog addUserDialog = new AddUserDialog(userSvc, users);
		UI.getCurrent().addWindow(addUserDialog);
		addUserDialog.focus();
	}
	
//	private void removeUser(Integer userId) {
//		if (userId != null) {
//			Long id = new Long(userId.toString());
//			Optional<UserEntity> findFirst = users.stream()
//												  .filter(e -> e.getId().equals(id))
//												  .findFirst();
//			findFirst.ifPresent(usr -> {
//				userSvc.removeUser(usr);
//				users.removeIf(e -> e.getId().equals(id));
//				usersGrid.recalculateColumnWidths();
//				usersGrid.clearSortOrder();
//				usersGrid.refreshAllRows();
//			});
//		}
//	}
	
}
