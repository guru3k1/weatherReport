package com.cga.weather_report.utils;

/**
 * @author Cesar Amadori
 *
 */
public class CustomErrorHandler {
	/**
	 * Property of the class to create error messages
	 */
	private String errorMessage;
	
	public CustomErrorHandler(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
}
}
