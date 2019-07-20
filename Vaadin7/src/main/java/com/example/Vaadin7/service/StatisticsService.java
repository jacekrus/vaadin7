package com.example.Vaadin7.service;

public interface StatisticsService {
	
	long getNumberOfDBConnectionsForCurrentUser();
	
	long getNumberOfAllDBConnections();
	
	void incrementDBConnectionsNumber();
	
	String getCreationDate();

}
