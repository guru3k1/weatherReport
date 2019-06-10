package com.cga.weather_report.utils;

/**
 * @author Cesar Amadori
 *
 */
public class CustomMessageHandler {
	/**
	 * Property of the class to create messages
	 */
	private String message;

	public CustomMessageHandler(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
