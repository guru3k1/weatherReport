package com.cga.weather_report.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cga.weather_report.dao.Dao;
import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.model.Planet;
import com.cga.weather_report.utils.Alignment;
import com.cga.weather_report.utils.InsideTriangle;

@Component
public class ServiceImp implements Service {
	
	@Autowired
	private Alignment planetsAlignment;
	
	@Autowired
	private InsideTriangle insideTriangle;
	
	@Autowired
	private Dao dao;
	
	private static final String OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE = "Optimal conditions of pressure and temperature";
	private static final int CLOCKWISE = -1;
	private static final int COUNTER_CLOCKWISE = 1;
	private static final String FERENGI_PLANET = "Ferengi";
	private static final String BETASOIDE_PLANET = "Betasoide";
	private static final String VULCANO_PLANET = "Vulcano";
	private static final Logger logger = LoggerFactory.getLogger(ServiceImp.class);

	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet(1000, 5, COUNTER_CLOCKWISE);
	Planet sunStar = new Planet(0, 0, 0);
	
	public String calculateWeatherByDayAndPlanet(int day, String planet) {
		int planetValue = validatePlanet(planet);

		String weather = "Normal";
		if (planetsAlignment.areAligned(ferengi.getCoordinates(day,planetValue), betasoide.getCoordinates(day,planetValue),
				vulcano.getCoordinates(day,planetValue))) {
				weather = OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE;
			if(planetsAlignment.areAlignedWithSun(ferengi.getCoordinates(day,planetValue), vulcano.getCoordinates(day,planetValue))) {
				weather= "Drought period";
			}
		}
		else if(insideTriangle.itsRaining(ferengi.getCoordinates(day,planetValue), betasoide.getCoordinates(day,planetValue), vulcano.getCoordinates(day,planetValue), sunStar.getCoordinates(day,planetValue))) {
			weather = "Rainy";
		}
		return weather;
	}
	
	
	@Override
	public String getWeatherReportByDay(int day, String planet) {
		return dao.getWeatherByDayAndPlanet(day, planet);
	}

	@Override
	public HashMap<String, Coordinates> getCoordenates(int day, String planet) {
		int planetValue = validatePlanet(planet);
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put(FERENGI_PLANET, ferengi.getCoordinates(day,planetValue));
		planets.put(BETASOIDE_PLANET, betasoide.getCoordinates(day,planetValue));
		planets.put(VULCANO_PLANET, vulcano.getCoordinates(day,planetValue));
		planets.put("Sol", sunStar.getCoordinates(day,planetValue));
		return planets;
	}

	@Override
	public boolean getAlignment(int day, String planet) {
		logger.debug("getAlignment method on Service called with day: {} and planet: {}",day,planet);
		int planetValue = validatePlanet(planet);
		
		return planetsAlignment.areAligned(ferengi.getCoordinates(day,planetValue), betasoide.getCoordinates(day,planetValue), vulcano.getCoordinates(day,planetValue));
	}


	@Override
	public HashMap<String, String> getReport(String planet) {
		return dao.getWeatherReportByPlanet(planet);
	}
	
	public int validatePlanet(String planet) {
		int planetValue=0;
		switch(planet) {
		case FERENGI_PLANET:
			planetValue = 1;
			break;
		case BETASOIDE_PLANET:
			planetValue = 3;
			break;
		case VULCANO_PLANET:
			planetValue = 5;
			break;
		default:
			break;
		}
		
		return planetValue;
	}

	
}
