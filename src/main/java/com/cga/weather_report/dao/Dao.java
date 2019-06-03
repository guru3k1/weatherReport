package com.cga.weather_report.dao;

import java.util.HashMap;

public interface Dao {
	
	HashMap<String,String> getWeatherReportByPlanet(String planet);
	
	String getWeatherByDayAndPlanet(int day, String planet);
	
	void insertWeatherByDayAndPlanet(int day, String planet);
}
