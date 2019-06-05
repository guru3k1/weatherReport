package com.cga.weather_report.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cga.weather_report.dao.ClimaDao;
import com.cga.weather_report.model.Clima;

@Component
public class InitializeData implements ApplicationRunner{
	
	@Autowired
	private Service service;
	
	@Autowired
	private ClimaDao dao;

	private static final String NORMAL = "Normal";
	private static final String DROUGHT = "Sequía";
	private static final String RAINY = "Lluvioso";
	private static final String OPTIMAL_CONDITIONS_OF_PRESSURE_AND_TEMPERATURE = "Optimas condiciones de presión y temperatura.";
	
	private static final Logger logger = LoggerFactory.getLogger(InitializeData.class);
	private int precision = 1440;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Initialize data in DB");
		List<String> dayWeather = new ArrayList<>();
		String weather= "";
		for (int j = 0; j < 3600; j++) {
			int optimalConditions = 0;
			int drought = 0;
			int rainy = 0;
			int normal = 0;
			for (int i = j*1440; i < (j+1)*precision; i++) {
				dayWeather.add(service.calculateWeatherByDay(i));
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
			dayWeather = new ArrayList<>();
			times = null;
			dao.saveClima(new Clima(weather));
		}
		logger.info("Database loaded.");
	}
}
