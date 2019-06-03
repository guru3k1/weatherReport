package com.cga.weather_report.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTests {

	@Autowired
	private Service service;
	
	@Test
	public void validatePlanetTest() {
		int actual = service.validatePlanet("Ferengi");
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	
}
