package com.cga.weather_report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cga.weather_report.dao.ClimaDao;
import com.cga.weather_report.model.Clima;

@Component
public class InitializeData implements ApplicationRunner{
	
	@Autowired
	private Service service;
	
	@Autowired
	private ClimaDao dao;

	private static final int INITIAL_DAYS = 3600;
	
	private static final Logger logger = LoggerFactory.getLogger(InitializeData.class);

	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Initialize data in DB");
		for (int j = 0; j < INITIAL_DAYS; j++) {
			dao.saveClima(new Clima(service.accurateWeatherCalc(j)));
		}
		logger.info("Days in database {}", dao.getDaysCount());
		logger.info("Database loaded.");
	}
	
	@Scheduled(cron="0 0 0 * * * ")
	public void addNewDayWeather() {
		logger.info("Adding a new weather every day at day start");
		int day = dao.getDaysCount();
		dao.saveClima(new Clima(service.accurateWeatherCalc(day+1)));
		logger.info("Weather added for the day {}",day);
	}
}
