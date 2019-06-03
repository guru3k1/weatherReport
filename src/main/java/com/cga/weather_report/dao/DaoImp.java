package com.cga.weather_report.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DaoImp implements Dao {

	@Override
	public HashMap<String, String> getWeatherReportByPlanet(String planet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWeatherByDayAndPlanet(int day, String planet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertWeatherByDayAndPlanet(int day, String planet) {
		// TODO Auto-generated method stub
		
	}

}
