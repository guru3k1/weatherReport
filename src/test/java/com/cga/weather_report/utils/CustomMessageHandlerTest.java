package com.cga.weather_report.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomMessageHandlerTest {
	
	private CustomMessageHandler cmh;
	
	@Test
	public void testConstructor() {
		cmh = new CustomMessageHandler("Test");
		assertEquals("Test", cmh.getMessage());
		cmh.setMessage("Test Set");
		assertEquals("Test Set", cmh.getMessage());
	}
	

}
