package com.cga.weather_report.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cga.weather_report.dao.ClimaDao;
import com.cga.weather_report.model.Clima;
import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.model.Planet;
import com.cga.weather_report.utils.Alignment;
import com.cga.weather_report.utils.InsideTriangle;

/**
 * @author Cesar Amadori
 *
 */
@Component
public class ServiceImp implements Service {
	
	@Autowired
	private Alignment planetsAlignment;
	
	@Autowired
	private InsideTriangle insideTriangle;
	
	@Autowired
	private ClimaDao dao;
	
	private static final String NORMAL = "Normal";
	private static final String DROUGHT = "Sequía";
	private static final String RAINY = "Lluvioso";
	private static final String OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE = "Optimas condiciones de presión y temperatura.";
	private static final int CLOCKWISE = -1;
	private static final int COUNTER_CLOCKWISE = 1;
	private static final String FERENGI_PLANET = "Ferengi";
	private static final String BETASOIDE_PLANET = "Betasoide";
	private static final String VULCANO_PLANET = "Vulcano";
	private static final String SUN_STAR = "Sol";
	private static final int INSTANT = 1440;
	private static final Logger logger = LoggerFactory.getLogger(ServiceImp.class);

	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet(1000, 5, COUNTER_CLOCKWISE);
	Planet sunStar = new Planet(0, 0, 0);
	
	
	/**
	 * <p><i>Given an instant of the day will verify the accurate weather for that instant<br>
	 * as we have divided the day in 1440 instants (60 minutes in 24 hours).</i></p> 
	 * 
	 * @param instant 1 of 1440 instants of the day
	 * 
	 * <p><strong>Note:</strong><i> Our knowledge about the galaxy is not complete so <br>
	 * I have divided the day as we have at the earth with 24 hours and 60 minutes by hour.<br>
	 * I found this value useful to have more precision to calculate the weather.<br>
	 * Just to differentiate the relationship to the time of the earth,<br>
	 * is that we call that amount of time instant. </i></p>
	 * 
	 * @return The weather for the instant.
	 */
	public String calculateWeatherByInstant(int instant) {
		String weather = NORMAL;
		if (planetsAlignment.areAligned(ferengi.getCoordinates(instant), betasoide.getCoordinates(instant),
				vulcano.getCoordinates(instant))) {
				weather = OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE;
			if(planetsAlignment.areAlignedWithSun(ferengi.getCoordinates(instant), vulcano.getCoordinates(instant), betasoide.getCoordinates(instant))) {
				weather= DROUGHT;
			}
		}
		else if(insideTriangle.itsRaining(ferengi.getCoordinates(instant), betasoide.getCoordinates(instant), vulcano.getCoordinates(instant), sunStar.getCoordinates(instant))) {
			weather = RAINY;
		}
		return weather;
	}
	

	/**
	 * <p><i>Given an instant of the day will deliver the coordinates of all the planets and sun for that instant</i></p> 
	 * 
	 * @param instant 1 of 1440 instants of the day
	 * 
	 * <p><strong>Note:</strong><i> Our knowledge about the galaxy is not complete so <br>
	 * I have divided the day as we have at the earth with 24 hours and 60 minutes by hour.<br>
	 * I found this value useful to have more precision to calculate the weather.<br>
	 * Just to differentiate the relationship to the time of the earth,<br>
	 * is that we call that amount of time instant. </i></p>
	 * 
	 * @return A Map with name and the coordinates for all planets and sun.
	 */
	@Override
	public HashMap<String, Coordinates> getCoordenates(Long instant) {
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put(FERENGI_PLANET, ferengi.getCoordinates(instant.intValue()));
		planets.put(BETASOIDE_PLANET, betasoide.getCoordinates(instant.intValue()));
		planets.put(VULCANO_PLANET, vulcano.getCoordinates(instant.intValue()));
		planets.put(SUN_STAR, sunStar.getCoordinates(instant.intValue()));
		return planets;
	}

	/**
	 * <p><i>Given a specific day  will deliver the weather stored on DB for that day.</i></p> 
	 * 
	 * @param day Day
	 * 
	 * @return A Clima object with the weather of that day.
	 */
	@Override
	public Clima getWeatherReportByDay(Long day) {
		return dao.getClimaByDia(day);
	}

	/**
	 * <p><i>Generate a Map with the report of quantity of events with weather types </i></p> 
	 * 
	 * @return A Map with the weather of the last 10 years.
	 */
	@Override
	public HashMap<String, String> getReport() {
		HashMap<String, String> clima = new LinkedHashMap<>();
		clima.put("Tipo de Clima ", "Cantidad de Dias");
		clima.put(DROUGHT, String.valueOf(dao.getDaysByWeather(DROUGHT)));
		clima.put(RAINY, String.valueOf(dao.getDaysByWeather(RAINY)));
		clima.put(OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE, String.valueOf(dao.getDaysByWeather(OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE)));
		clima.put(NORMAL, String.valueOf(dao.getDaysByWeather(NORMAL)));
		return clima;
	}
	
	/**
	 * <p><i>Will calculate the more accurate weather for a specific day </i></p> 
	 * 
	 * @param day Day
	 * 
	 * @return A String with the weather of that day
	 */	
	public String accurateWeatherCalc (int day) {
		List<String> dayWeather = new ArrayList<>();
		String weather= "";
		int optimalConditions = 0;
		int drought = 0;
		int rainy = 0;
		int normal = 0;
		for (int i = day*INSTANT; i < (day+1)*INSTANT; i++) {
			dayWeather.add(calculateWeatherByInstant(i));
		}
	Map<String, Long> times = dayWeather.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (Map.Entry<String, Long> entry : times.entrySet()) {
			switch(entry.getKey()) {
			case NORMAL:
				normal = entry.getValue().intValue();
				break;
			case DROUGHT:
				 drought = entry.getValue().intValue();
				break;
			case RAINY:
				rainy = entry.getValue().intValue();
				break;
			case OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE:
				optimalConditions = entry.getValue().intValue();
				break;
			default:
				break;
			}
		}
		if(drought>0) {
			weather=DROUGHT;
		}else if(optimalConditions >0){
			weather=OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE;
		}else if(rainy > normal) {
			weather=RAINY;
		}else {
			weather=NORMAL;
		}
		return weather;
	}
	
}
