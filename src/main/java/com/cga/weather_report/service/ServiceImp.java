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
	private static int precision = 1440;
	private static final Logger logger = LoggerFactory.getLogger(ServiceImp.class);

	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet(1000, 5, COUNTER_CLOCKWISE);
	Planet sunStar = new Planet(0, 0, 0);
	
	public String calculateWeatherByDay(int day) {
		String weather = NORMAL;
		if (planetsAlignment.areAligned(ferengi.getCoordinates(day), betasoide.getCoordinates(day),
				vulcano.getCoordinates(day))) {
				weather = OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE;
			if(planetsAlignment.areAlignedWithSun(ferengi.getCoordinates(day), vulcano.getCoordinates(day), betasoide.getCoordinates(day))) {
				weather= DROUGHT;
			}
		}
		else if(insideTriangle.itsRaining(ferengi.getCoordinates(day), betasoide.getCoordinates(day), vulcano.getCoordinates(day), sunStar.getCoordinates(day))) {
			weather = RAINY;
		}
		return weather;
	}
	

	@Override
	public HashMap<String, Coordinates> getCoordenates(Long day) {
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put(FERENGI_PLANET, ferengi.getCoordinates(day.intValue()));
		planets.put(BETASOIDE_PLANET, betasoide.getCoordinates(day.intValue()));
		planets.put(VULCANO_PLANET, vulcano.getCoordinates(day.intValue()));
		planets.put(SUN_STAR, sunStar.getCoordinates(day.intValue()));
		return planets;
	}

	@Override
	public boolean getAlignment(Long day) {
		logger.debug("getAlignment method on Service called with day: {}",day);
		
		return planetsAlignment.areAligned(ferengi.getCoordinates(day.intValue()), betasoide.getCoordinates(day.intValue()), vulcano.getCoordinates(day.intValue()));
	}


	@Override
	public Clima getWeatherReportByDay(Long day) {
		return dao.getClimaByDia(day);
	}


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
	
	
	public String accurateWeatherCalc (int day) {
		List<String> dayWeather = new ArrayList<>();
		String weather= "";
		int optimalConditions = 0;
		int drought = 0;
		int rainy = 0;
		int normal = 0;
		for (int i = day*1440; i < (day+1)*precision; i++) {
			dayWeather.add(calculateWeatherByDay(i));
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
