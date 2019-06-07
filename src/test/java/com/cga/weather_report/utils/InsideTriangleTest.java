package com.cga.weather_report.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.model.Planet;

public class InsideTriangleTest extends InsideTriangle{

	@Mock
	Planet ferengiMock;
	
	@Mock
	Planet betasoideMock;
	
	@Mock
	Planet vulcanoMock;
	
	@Mock
	Planet sunStarMock;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void setup() {
		setupFerengi();
		setupBetasoide();
		setupVulcano();
		setupSunStar();
	}
	
	@Test
	public void isPositiveTheNumber() {
		boolean answer = isPositive(5);
		assertTrue(answer);
		
		answer = isPositive(-5);
		assertFalse(answer);
	}
	
	
	@Test
	public void findOrientationOfTriangle() {
		double actual = findOrientation(ferengiMock.getCoordinates(72), betasoideMock.getCoordinates(72), vulcanoMock.getCoordinates(72));
		double expected = 2543901.7154509;
		assertEquals(expected, actual, .01);
	}
	
	
	@Test
	public void isRainingInFerengiTest() {
		boolean rainingOk = itsRaining(ferengiMock.getCoordinates(72), betasoideMock.getCoordinates(72), vulcanoMock.getCoordinates(72), sunStarMock.getCoordinates(72));
		assertTrue(rainingOk);
		
	}
	
	private void setupFerengi() {
		when(ferengiMock.getCoordinates(72))
		.thenReturn(new Coordinates(154.5085, -475.5282));
		when(ferengiMock.getCoordinates(73))
		.thenReturn(new Coordinates(-0.5085, 475.5282));
		when(ferengiMock.getCoordinates(0))
		.thenReturn(new Coordinates(500, 0));
	}
	
	private void setupBetasoide() {
		when(betasoideMock.getCoordinates(72))
		.thenReturn(new Coordinates(-1618.0340, 1175.5705));
		when(betasoideMock.getCoordinates(72))
		.thenReturn(new Coordinates(1500.0340, 1176.5705));
	}
	
	private void setupVulcano() {
		when(vulcanoMock.getCoordinates(72))
		.thenReturn(new Coordinates(1000, -2.4493));
		when(vulcanoMock.getCoordinates(72))
		.thenReturn(new Coordinates(-1000, -2.4493));
	}
	
	private void setupSunStar() {
		when(sunStarMock.getCoordinates(72))
		.thenReturn(new Coordinates(0, 0));
		when(sunStarMock.getCoordinates(73))
		.thenReturn(new Coordinates(-0.1, -0.1));
	}
	
}
