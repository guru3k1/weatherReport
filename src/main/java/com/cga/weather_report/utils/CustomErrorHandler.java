package com.cga.weather_report.utils;

public class CustomErrorHandler {
	
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
