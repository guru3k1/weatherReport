package com.cga.weather_report.dao;

import com.cga.weather_report.model.Clima;

/**
 * @author Cesar Amadori
 *
 */
public interface ClimaDao {

	Clima getClimaByDia(Long dia);
	
	void saveClima(Clima clima);
	
	int getDaysCount();
	
	int getDaysByWeather(String weather);
}
