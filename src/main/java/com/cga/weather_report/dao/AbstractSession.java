package com.cga.weather_report.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cesar Amadori
 *
 */
public abstract class AbstractSession {
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * <p><i>Get the DB session</i></p> 
	 * 
	 * @return The current session for get access to de DB
	 */
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
}
