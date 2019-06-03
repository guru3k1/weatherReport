package com.cga.weather_report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cga.weather_report.dao.FerengiDao;
import com.cga.weather_report.entity.Ferengi;

@Component
public class InitializeData implements ApplicationRunner{
	
	@Autowired
	private Service service;
	
	@Autowired
	private FerengiDao ferengiDao;
	
	private static final Logger logger = LoggerFactory.getLogger(InitializeData.class);
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Ejecutado al inicio");
		for (int i = 0; i < 3600; i++) {
			ferengiDao.save(new Ferengi(service.calculateWeatherByDayAndPlanet(i, "Ferengi")));
			
		}
	}

}
