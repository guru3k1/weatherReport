package com.cga.weather_report.utils;

import org.springframework.stereotype.Component;

import com.cga.weather_report.model.Coordinates;

@Component
public class Alignment {
	
	public boolean areAligned(Coordinates p1, Coordinates p2, Coordinates p3) {
		boolean perfectMatch = (p3.getY()-p1.getY()) == getSlope(p1, p2)*(p3.getX()-p1.getX());
		boolean adjustedMatch = (p3.getY()-p1.getY()) - getSlope(p1, p2)*(p3.getX()-p1.getX()) < 1;

		return perfectMatch || adjustedMatch;
	}

	public boolean areAlignedWithSun(Coordinates p1, Coordinates p2) {
		return 	(0-p1.getY()) == getSlope(p1, p2)*(0-p1.getX());
	}
	
	public double getSlope(Coordinates p1, Coordinates p2) {
		return (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
	}
}
