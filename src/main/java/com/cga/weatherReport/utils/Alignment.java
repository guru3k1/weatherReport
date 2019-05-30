package com.cga.weatherReport.utils;

import org.springframework.stereotype.Component;

import com.cga.weatherReport.model.Coordinates;

@Component
public class Alignment {
	
	public boolean areAligned(Coordinates p1, Coordinates p2, Coordinates p3) {
		return 	(p3.getY()-p1.getY()) == getSlope(p1, p2)*(p3.getX()-p1.getX());
	}

	public boolean areAlignedWithSun(Coordinates p1, Coordinates p2) {
		return 	(0-p1.getY()) == getSlope(p1, p2)*(0-p1.getX());
	}
	
	public double getSlope(Coordinates p1, Coordinates p2) {
		return (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
	}
}
