package com.cga.weather_report.utils;

import org.springframework.stereotype.Component;

import com.cga.weather_report.model.Coordinates;

@Component
public class InsideTriangle {
	public boolean itsRaining(Coordinates p1, Coordinates p2, Coordinates p3, Coordinates sun) {
		double p1p2p3 = findOrientation(p1,p2,p3);
		double p1p2sun = findOrientation(p1,p2,sun);
		double p2p3sun = findOrientation(p2, p3, sun);
		double p3p1sun = findOrientation(p3,p1,sun);
		boolean firstComp = isPositive(p1p2sun)==isPositive(p1p2p3);
		boolean secondComp = isPositive(p2p3sun)==isPositive(p1p2p3);
		boolean thirdComp = isPositive(p3p1sun)==isPositive(p1p2p3);
		return firstComp && secondComp && thirdComp;
	}
	
	public double findOrientation(Coordinates p1, Coordinates p2, Coordinates p3) {
		return (p1.getX()-p3.getX()) * (p2.getY()-p3.getY()) - (p1.getY()-p3.getY()) * (p2.getX()-p3.getX()); 
	}
	
	public boolean isPositive (double number) {
		return number >= 0;
	}
}
