package com.cga.weather_report.service;

import java.util.HashMap;

import com.cga.weather_report.model.Coordinates;

public interface Service {
	String calculateWeatherByDayAndPlanet(int day, String planet);
	
	String getWeatherReportByDay(int day, String planet);
	
	HashMap<String,Coordinates> getCoordenates(int day, String planet);
	
	boolean getAlignment(int day, String planet);
	
	int validatePlanet(String planet);
	
	HashMap<String, String> getReport(String planet);
}
