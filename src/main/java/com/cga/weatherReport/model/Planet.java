package com.cga.weatherReport.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Planet {
	private int radius;
	private int angularSpeed;
	private int direction;
	
	public Coordinates getCoordinates(int day) {
		return new Coordinates(getX(day),getY(day));
	}
	
	public double getY (int day) {
		return new BigDecimal(this.radius * Math.sin(Math.toRadians(this.angularSpeed*day*direction))).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public double getX (int day) {
		return new BigDecimal(this.radius * Math.cos(Math.toRadians(this.angularSpeed*day*direction))).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public Planet(int radius, int angularSpeed, int direction) {
		super();
		this.radius = radius;
		this.angularSpeed = angularSpeed;
		this.direction = direction;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public int getAngularSpeed() {
		return angularSpeed;
	}
	public void setAngularSpeed(int angularSpeed) {
		this.angularSpeed = angularSpeed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
}
