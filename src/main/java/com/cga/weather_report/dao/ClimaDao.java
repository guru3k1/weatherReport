package com.cga.weather_report.dao;

import com.cga.weather_report.model.Clima;

public interface ClimaDao {

	Clima getClimaByDia(Long dia);
	
	void saveClima(Clima clima);
}
