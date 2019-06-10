package com.cga.weather_report.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cga.weather_report.model.Clima;

/**
 * @author Cesar Amadori
 *
 */
@Repository
@Transactional
public class ClimaDaoImp extends AbstractSession implements ClimaDao{
	/**
	 * <p><i>Will call the DB to retrieve the stored Clima Object on the DB for a specific day</i></p> 
	 * 
	 * @param dia Day
	 * 
	 * @return A Clima object with the weather of a specific day
	 */
	@Override
	public Clima getClimaByDia(Long dia) {
		return getSession().get(Clima.class, dia);
	}

	/**
	 * <p><i>Will persist into the DB a Clima Object</i></p> 
	 * 
	 * @param clima Clima object
	 * 
	 */
	@Override
	public void saveClima(Clima clima) {
		getSession().persist(clima);
		
	}

	/**
	 * <p><i>Will call the DB to retrieve quantity of days stored</i></p>
	 * 
	 * @return Quantity of days stored in the DB
	 */
	@Override
	public int getDaysCount() {
		return getSession().createQuery("from Clima").getResultList().size();
	}

	/**
	 * <p><i>Will call the DB to retrieve quantity of days stored with a specific weather</i></p>
	 * 
	 * @param weather Weather
	 * 
	 * @return Quantity of days stored in the DB with a specific weather
	 */
	@Override
	public int getDaysByWeather(String weather) {
		return getSession().createSQLQuery("select * from Clima where clima = :weather")
				.setParameter("weather", weather)
				.list().size();
	}


	
}
