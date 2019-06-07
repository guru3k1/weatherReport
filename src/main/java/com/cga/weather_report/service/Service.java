package com.cga.weather_report.service;

import java.util.HashMap;

import com.cga.weather_report.model.Clima;
import com.cga.weather_report.model.Coordinates;

public interface Service {
	String calculateWeatherByDay(int day);
	
	Clima getWeatherReportByDay(Long day);
	
	HashMap<String,Coordinates> getCoordenates(Long day);
	
	boolean getAlignment(Long day);

	HashMap<String, String> getReport();
	
	String accurateWeatherCalc (int day);
}
