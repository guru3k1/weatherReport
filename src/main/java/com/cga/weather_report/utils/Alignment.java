package com.cga.weather_report.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cga.weather_report.model.Coordinates;

/**
 * @author Cesar Amadori
 *
 */
@Component
public class Alignment {
	private static final Logger logger = LoggerFactory.getLogger(Alignment.class);
	
	/**
	 * 
	 */
	private final static int toleranceInKm = 20; 
	
	/**
	 * <p><i>Given coordinates from the 3 planets will verify <br>
	 * if the planets are aligned based on the slope created by two planets and <br>
	 * the third planet as a dot inside the straight line </i></p> 
	 * 
	 * @param p1 Planet1
	 * @param p2 Planet2
	 * @param p3 Planet3
	 * 
	 * <p><strong>Note:</strong><i> About tolerance of the approximation to the perfect alignment. <br>
	 * As we don't have the size of any of the planets, <br>
	 * i gave more tolerance to include the planet mass.</i></p>
	 * 
	 * @return If the three planets are aligned.
	 */
	public boolean areAligned(Coordinates p1, Coordinates p2, Coordinates p3) {
		logger.debug("Inside method areAligned");
		boolean perfectMatch = (p3.getY()-p1.getY()) == getSlope(p1, p2)*(p3.getX()-p1.getX());
		boolean adjustedMatch = Math.abs((p3.getY()-p1.getY()) - getSlope(p1, p2)*(p3.getX()-p1.getX())) < toleranceInKm;
		return perfectMatch || adjustedMatch;
	}
	
	/**
	 * <p><i>Given coordinates from the 3 planets will verify <br>
	 * if the planets are aligned with the sun based on the equality of two slopes<br>
	 * the first created between two planets and the second created with the third planet and the sun</i></p> 
	 * 
	 * @param p1 Planet1
	 * @param p2 Planet2
	 * @param p3 Planet3
	 * 
	 * <p><strong>Note:</strong><i> About tolerance of the approximation to the perfect alignment. <br>
	 * As we don't have the size of any of the planets, <br>
	 * i gave more tolerance to include the planet mass.</i></p>
	 * 
	 * @return If the three planets are aligned with the sun.
	 */
	public boolean areAlignedWithSun(Coordinates p1, Coordinates p2, Coordinates p3) {
		return 	Math.abs(((p2.getY()-p1.getY())*(p3.getX()-0)) - ((p2.getX()-p1.getX())*(p3.getY()-0))) < toleranceInKm;
	}
	
	/**
	 * <p><i>Given coordinates from the 2 planets will calculate the slope of the straight line.</i></p> 
	 * 
	 * @param p1 Planet1
	 * @param p2 Planet2
	 * 
	 * 
	 * @return The value of the slope.
	 */
	public double getSlope(Coordinates p1, Coordinates p2) {
		return (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
	}
}
