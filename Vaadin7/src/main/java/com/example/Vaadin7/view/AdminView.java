package com.example.Vaadin7.view;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.example.Vaadin7.model.UserEntity;
import com.example.Vaadin7.notifications.AbstractNotification;
import com.example.Vaadin7.notifications.Broadcaster;
import com.example.Vaadin7.notifications.Broadcaster.BroadcastListener;
import com.example.Vaadin7.notifications.UserAddedNotification;
import com.example.Vaadin7.notifications.UserChangedNotification;
import com.example.Vaadin7.notifications.UserChangedNotification.UserChangedNotificationVisitor;
import com.example.Vaadin7.notifications.UserDeletedNotification;
import com.example.Vaadin7.service.UserService;
import com.example.Vaadin7.utils.NavigationNames;
import com.example.Vaadin7.widget.AddUserDialog;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView(NavigationNames.ADMIN_VIEW)
public class AdminView extends CustomComponent implements View, BroadcastListener {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userSvc;
	
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
        removeUserButton.addClickListener(e -> userSvc.removeUser((UserEntity) usersGrid.getSelectedRow()));
        
        buttonsContainer.addComponents(addUserButton, removeUserButton);
        
        gridContainer.addComponents(buttonsContainer, usersGrid);
        layout.addComponent(gridContainer);
        
        setSizeFull();
        setCompositionRoot(layout);
	}
	
	@Override
	public void attach() {
		super.attach();
		Broadcaster.register(this);
	}
	
	@Override
	public void detach() {
		super.detach();
		Broadcaster.unregister(this);
	}
	
	@Override
	public void receiveBroadcast(AbstractNotification notification) {
		if (notification instanceof UserChangedNotification) {
			getUI().access(() -> { 
				((UserChangedNotification) notification).accept(new UserChangedNotificationVisitor<Void>() {

					@Override
					public Void visit(UserAddedNotification notification) {
						UserEntity user = new UserEntity();
						user.setId(notification.getUserId());
						user.setName(notification.getName());
						user.setPassword(notification.getPassword());
						usersGrid.getContainerDataSource().addItem(user);
						return null;
					}

					@Override
					public Void visit(UserDeletedNotification notification) {
						Optional<?> user = usersGrid.getContainerDataSource().getItemIds().stream()
								.filter(ue -> ((UserEntity) ue).getId().equals(notification.getUserId())).findFirst();
						user.ifPresent(usr -> usersGrid.getContainerDataSource().removeItem(usr));
						return null;
					}
				});
				usersGrid.refreshAllRows();
			});
		}
	}
	
	private Grid setUpUsersGrid() {
		List<UserEntity> users = userSvc.findAllUsers();
		BeanItemContainer<UserEntity> usersContainer = new BeanItemContainer<UserEntity>(UserEntity.class, users);
		Grid usersGrid = new Grid(usersContainer);
        usersGrid.setSelectionMode(SelectionMode.SINGLE);
        usersGrid.addSelectionListener(new SelectionListener() {
        	
			private static final long serialVersionUID = 864122493588754309L;

			@Override
			public void select(SelectionEvent event) {
				UserEntity selectedUser = (UserEntity) usersGrid.getSelectedRow();
				if(selectedUser == null || selectedUser.getId() == 1) {
					removeUserButton.setEnabled(false);
				}
				else {
					removeUserButton.setEnabled(true);
				}
			}
		});
		return usersGrid;
	}
	
	private void showAddUserDialog() {
		@SuppressWarnings("unchecked")
		AddUserDialog addUserDialog = new AddUserDialog(userSvc, (Collection<UserEntity>) usersGrid.getContainerDataSource().getItemIds());
		UI.getCurrent().addWindow(addUserDialog);
		addUserDialog.focus();
	}
	
}
