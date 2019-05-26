package com.cga.weatherReport.utils;

import org.springframework.stereotype.Component;

import com.cga.weatherReport.model.Coordinates;

@Component
public class InsideTriangle {
	public boolean isInside(Coordinates p1, Coordinates p2, Coordinates p3, Coordinates sun) {
		double p1p2p3 = findOrientation(p1,p2,p3);
		double p1p2sun = findOrientation(p1,p2,sun);
		double p2p3sun = findOrientation(p2, p3, sun);
		double p3p1sun = findOrientation(p3,p1,sun);
		boolean planetsWithSun = isPositive(p1p2sun) && isPositive(p2p3sun) && isPositive(p3p1sun);
		
		return isPositive(p1p2p3) == planetsWithSun;
	}
	
	private double findOrientation(Coordinates p1, Coordinates p2, Coordinates p3) {
		return (p1.getX()-p3.getX()) * (p2.getY()-p3.getY()) - (p1.getY()-p3.getY()) * (p2.getX()-p3.getX()); 
	}
	
	public static boolean isPositive (double number) {
		if (number >= 0) {
			return true;
		} else {
			return false;
		}
	}
}
