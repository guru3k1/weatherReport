package com.cga.weather_report.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cga.weather_report.model.Clima;

@Repository
@Transactional
public class ClimaDaoImp extends AbstractSession implements ClimaDao{

	@Override
	public Clima getClimaByDia(Long dia) {
		getSession();
		return getSession().get(Clima.class, dia);
	}

	@Override
	public void saveClima(Clima clima) {
		getSession().persist(clima);
		
	}

	
}
