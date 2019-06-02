package com.cga.weather_report.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

public class Planet {
	private int radius;
	private int angularSpeed;
	private int direction;
	
	public Coordinates getCoordinates(@Min(0) int day, int planet) {
		return new Coordinates(getX(day, planet),getY(day, planet));
	}
	
	public double getY (int day, int planet) {
		return BigDecimal.valueOf(this.radius * Math.sin(Math.toRadians(this.angularSpeed*(Double.valueOf(day)/planet)*direction))).doubleValue();
	}
	
	public double getX (int day,int planet) {
		return BigDecimal.valueOf(this.radius * Math.cos(Math.toRadians(this.angularSpeed*(Double.valueOf(day)/planet)*direction))).doubleValue();
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
