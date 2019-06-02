package com.cga.weather_report.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomErrorHandlerTest {

	private CustomErrorHandler ceh;

	@Test
	public void testClass() {
		ceh = new CustomErrorHandler("Test ERROR");
		assertEquals("Test ERROR", ceh.getErrorMessage());
		ceh.setErrorMessage("Test ERROR Set");
		assertEquals("Test ERROR Set", ceh.getErrorMessage());
	}
}
