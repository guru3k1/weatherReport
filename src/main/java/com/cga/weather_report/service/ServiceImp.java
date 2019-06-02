package com.cga.weather_report.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private static final String OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE = "Optimal conditions of pressure and temperature";
	private static final int CLOCKWISE = -1;
	private static final int COUNTER_CLOCKWISE = 1;

	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet(1000, 5, COUNTER_CLOCKWISE);
	Planet sun = new Planet(0, 0, 0);
	
	@Override
	public String getWeatherReportByDay(int day, String planet) {
		String weather = "Normal";
		if (planetsAlignment.areAligned(ferengi.getCoordinates(day), betasoide.getCoordinates(day),
				vulcano.getCoordinates(day))) {
				weather = OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE;
			if(planetsAlignment.areAlignedWithSun(ferengi.getCoordinates(day), vulcano.getCoordinates(day))) {
				weather= "Drought period";
			}
		}
		else if(insideTriangle.itsRaining(ferengi.getCoordinates(day), betasoide.getCoordinates(day), vulcano.getCoordinates(day), sun.getCoordinates(day))) {
			weather = "Rainy";
		}
		return weather;
	}

	@Override
	public HashMap<String, Coordinates> getCoordenates(int day, String planet) {
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put("Ferengi", ferengi.getCoordinates(day));
		planets.put("Betasoide", betasoide.getCoordinates(day));
		planets.put("Vulcano", vulcano.getCoordinates(day));
		planets.put("Sol", sun.getCoordinates(day));
		return planets;
	}

	@Override
	public boolean getAlignment(int day, String planet) {
		return planetsAlignment.areAligned(ferengi.getCoordinates(day), betasoide.getCoordinates(day), vulcano.getCoordinates(day));
	}

	@Override
	public String getWeatherReportByWeatherType(String weather, String planet) {
		// TODO Auto-generated method stub
		return null;
	}

}
