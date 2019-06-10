package com.cga.weather_report.utils;

import org.springframework.stereotype.Component;

import com.cga.weather_report.model.Coordinates;

/**
 * @author Cesar Amadori
 *
 */
@Component
public class InsideTriangle {
	
	/**
	 * <p><i>Given coordinates from the 3 planets and the sun, will verify 
	 * the positive or negative sign of each combination of triangles.<br>
	 * If all combinations are positive or all combinations are negative 
	 * dots are part of the triangle.</i></p> 
	 * 
	 * @param p1 Planet1
	 * @param p2 Planet2
	 * @param p3 Planet3
	 * @param sun Sun
	 * 
	 * @return If the sun is inside of the triangle.
	 */
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
	
	/**
	 * <p><i>Will calculate the orientation of the triangle</i></p>
	 * 
	 * @param p1 Planet1
	 * @param p2 Planet2
	 * @param p3 Planet3
	 * 
	 * @return The calculation of orientation of the triangle.
	 */
	public double findOrientation(Coordinates p1, Coordinates p2, Coordinates p3) {
		return (p1.getX()-p3.getX()) * (p2.getY()-p3.getY()) - (p1.getY()-p3.getY()) * (p2.getX()-p3.getX()); 
	}
	
	/**
	 * <p><i>Will return a boolean with the value if the number is positive.</i></p>
	 * 
	 * @param number Orientation of the triangle
	 * 
	 * @return Confirm if is positive.
	 */
	public boolean isPositive (double number) {
		return number >= 0;
	}
}
