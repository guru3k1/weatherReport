package com.cga.weather_report.service;

import java.util.HashMap;

import com.cga.weather_report.model.Clima;
import com.cga.weather_report.model.Coordinates;

/**
 * @author Cesar Amadori
 *
 */
public interface Service {
	String calculateWeatherByInstant(int instant);
	
	Clima getWeatherReportByDay(Long day);
	
	HashMap<String,Coordinates> getCoordenates(Long instant);

	HashMap<String, String> getReport();
	
	String accurateWeatherCalc (int day);
}
