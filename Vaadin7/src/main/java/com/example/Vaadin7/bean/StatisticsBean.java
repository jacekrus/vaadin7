package com.example.Vaadin7.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.example.Vaadin7.service.StatisticsService;
import com.vaadin.server.VaadinSession;

@Singleton
@Startup
public class StatisticsBean implements StatisticsService {
	
	private Map<String, Long> dbConnectionsPerUserMap;
	
	private Date creationDate;
	
	@PostConstruct
	private void init() {
		dbConnectionsPerUserMap = new HashMap<>();
		creationDate = new Date();
		System.out.println("StatiscicsBean CREATED");
	}
	
	@PreDestroy
	private void destructionCallback() {
		System.out.println("StatiscicsBean DESTROYED");
	}

	@Override
	public long getNumberOfDBConnectionsForCurrentUser() {
		Object username = VaadinSession.getCurrent().getAttribute("user");
		if(username == null || dbConnectionsPerUserMap.get(username) == null) {
			return 0;
		}
		return dbConnectionsPerUserMap.get(username);
	}

	@Override
	public long getNumberOfAllDBConnections() {
		return dbConnectionsPerUserMap.values().stream().mapToLong(Long::longValue).sum();
	}

	@Override
	public void incrementDBConnectionsNumber() {
		Object username = VaadinSession.getCurrent().getAttribute("user");
		if(username == null) {
			return;
		}
		
		if(dbConnectionsPerUserMap.get(username) == null) {
			dbConnectionsPerUserMap.put((String) username, 1L);
		}
		else {
			dbConnectionsPerUserMap.put((String) username, dbConnectionsPerUserMap.get(username) + 1);
		}
	}

	@Override
	public String getCreationDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return format.format(creationDate);
	}


}
