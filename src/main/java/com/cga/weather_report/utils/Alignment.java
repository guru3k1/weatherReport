package com.cga.weather_report.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cga.weather_report.model.Coordinates;

@Component
public class Alignment {
	private static final Logger logger = LoggerFactory.getLogger(Alignment.class);
	
	private final static int toleranceInKm = 10; 
	
	public boolean areAligned(Coordinates p1, Coordinates p2, Coordinates p3) {
		logger.debug("Inside method areAligned");
		boolean perfectMatch = (p3.getY()-p1.getY()) == getSlope(p1, p2)*(p3.getX()-p1.getX());
		boolean adjustedMatch = Math.abs((p3.getY()-p1.getY()) - getSlope(p1, p2)*(p3.getX()-p1.getX())) < toleranceInKm;
		return perfectMatch || adjustedMatch;
	}

	public boolean areAlignedWithSun(Coordinates p1, Coordinates p2, Coordinates p3) {
		return 	Math.abs(((p2.getY()-p1.getY())*(p3.getX()-0)) - ((p2.getX()-p1.getX())*(p3.getY()-0))) < toleranceInKm;
	}
	
	public double getSlope(Coordinates p1, Coordinates p2) {
		return (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
	}
}
