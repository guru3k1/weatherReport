package com.cga.weather_report.utils;

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

public class AligmentTest extends Alignment{

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
	public void areAlignedTest() {
		boolean actual = areAligned(ferengiMock.getCoordinates(0, 1), betasoideMock.getCoordinates(0, 1), vulcanoMock.getCoordinates(0, 1));
		assertTrue(actual);
		boolean actualFalse = areAligned(ferengiMock.getCoordinates(72, 1), betasoideMock.getCoordinates(72, 1), vulcanoMock.getCoordinates(72, 1));
		assertFalse(actualFalse);
	}
	
	@Test
	public void areAlignedWithSunTest() {
		boolean actual = areAlignedWithSun(ferengiMock.getCoordinates(0, 1), betasoideMock.getCoordinates(0, 1));
		assertTrue(actual);
		actual = areAlignedWithSun(ferengiMock.getCoordinates(72, 1), betasoideMock.getCoordinates(0, 1));
		assertFalse(actual);
	}
	
	private void setupFerengi() {
		when(ferengiMock.getCoordinates(72, 1))
		.thenReturn(new Coordinates(154.5085, -475.5282));
		when(ferengiMock.getCoordinates(73, 1))
		.thenReturn(new Coordinates(-0.5085, 475.5282));
		when(ferengiMock.getCoordinates(0, 1))
		.thenReturn(new Coordinates(500, 0));
	}
	
	private void setupBetasoide() {
		when(betasoideMock.getCoordinates(72, 1))
		.thenReturn(new Coordinates(-1618.0340, 1175.5705));
		when(betasoideMock.getCoordinates(72, 3))
		.thenReturn(new Coordinates(1500.0340, 1176.5705));
		when(betasoideMock.getCoordinates(0, 1))
		.thenReturn(new Coordinates(1000, 0));
	}
	
	private void setupVulcano() {
		when(vulcanoMock.getCoordinates(72, 1))
		.thenReturn(new Coordinates(1000, -2.4493));
		when(vulcanoMock.getCoordinates(72, 5))
		.thenReturn(new Coordinates(-1000, -2.4493));
		when(vulcanoMock.getCoordinates(0, 1))
		.thenReturn(new Coordinates(2000, 0));
	}
	
	private void setupSunStar() {
		when(sunStarMock.getCoordinates(72, 1))
		.thenReturn(new Coordinates(0, 0));
		when(sunStarMock.getCoordinates(73, 0))
		.thenReturn(new Coordinates(-0.1, -0.1));
	}
	
}
