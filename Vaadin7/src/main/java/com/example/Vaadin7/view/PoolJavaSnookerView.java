package com.example.Vaadin7.view;

import javax.swing.plaf.IconUIResource;

import com.google.gwt.user.client.ui.Image;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.cdi.CDIView;
import com.vaadin.client.ui.Icon;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.button.ButtonServerRpc;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("poolJavaSnookerView")
//@StyleSheet("vaadin://vaadin7styles_v3.css")
@StyleSheet("vaadin://vaadin7styles_v9.css")
//@Theme("mytheme")
public class PoolJavaSnookerView extends CustomComponent implements View {

	private static final long serialVersionUID = -7480398079308043714L;

	@Override
	public void enter(ViewChangeEvent event) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		Button fruits = new Button("Fruits");
		fruits.setStyleName(ValoTheme.BUTTON_LINK);
		fruits.setIcon(new ExternalResource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQa3WqZhCJxRHtCehsZH8omg-DEb9JMS3k8d84PRso6KgOwEgb5"));
		fruits.addClickListener(e -> Notification.show("ble"));
		Label label = new Label("THE WORLD'S LARGEST CAR EQUIPMENT SITE");
		label.addStyleName("specialFont");
		ThemeResource icon = new ThemeResource("img/Cart.PNG");
		Button button = new Button();
		Embedded embedded = new Embedded(null, icon);
		button.addStyleName("testImg");
		button.setWidth("132px");
		button.setHeight("37px");
//		label.setIcon(new ExternalResource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQa3WqZhCJxRHtCehsZH8omg-DEb9JMS3k8d84PRso6KgOwEgb5"));
		layout.addComponent(label);
		layout.addComponent(button);
		layout.addComponent(embedded);
//		layout.addLayoutClickListener(e -> Notification.show("ble"));
		setCompositionRoot(layout);
	}

}
