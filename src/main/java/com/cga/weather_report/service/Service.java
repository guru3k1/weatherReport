package com.cga.weather_report.service;

import java.util.HashMap;

import com.cga.weather_report.model.Coordinates;

public interface Service {
	
	String getWeatherReportByDay(int day, String planet);
	
	HashMap<String,Coordinates> getCoordenates(int day, String planet);
	
	boolean getAlignment(int day, String planet);
	
	String getWeatherReportByWeatherType(String weather, String planet);
	
}
