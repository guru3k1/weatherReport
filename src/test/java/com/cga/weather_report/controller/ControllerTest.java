package com.cga.weather_report.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.springframework.test.web.servlet.MockMvc;

import com.cga.weather_report.model.Clima;
import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.service.Service;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private Service serviceMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		setupService();
	}
	
	
	@Test
	public void rootTest() throws Exception {
		String uri = "/";

			mvc.perform(get(uri)
				      .contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk())
				      .andExpect(content().string("Bienvenido al reporte climatico de la galaxia. El sistema esta Activo"));
		
	}
	
	@Test
	public void wrongValueTest () throws Exception {
		String uri = "/clima?dia=a";
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest())
			      .andExpect(content().json("{'errorMessage':'El valor de dia ingresado no es valido.'}"));
	}
	
	@Test
	public void badKeyTest () throws Exception {
		String uri = "/clima?d";
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest())
			      .andExpect(content().json("{'errorMessage':'La clave y el valor de dia no fue ingresado. Ingreselo de esta manera clima?dia=0'}"));
	}
	
	@Test
	public void nullDayTest () throws Exception {
		String uri = "/clima?dia=";
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isConflict())
			      .andExpect(content().json("{'errorMessage':'El dia es requerido'}"));
		
		uri = "/coordenadas?dia=";
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isConflict())
			      .andExpect(content().json("{'errorMessage':'El dia es requerido'}"));
	}
	
	@Test
	public void lowerThanZeroDayTest () throws Exception {
		String uri = "/clima?dia=-1";
		mvc.perform(get(uri)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(content().json("{'errorMessage':'El dia debe ser 0 o superior'}"));
		
		uri = "/coordenadas?dia=-1";
		mvc.perform(get(uri)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(content().json("{'errorMessage':'El dia debe ser 0 o superior'}"));
		
		
	}
	
	@Test
	public void validWeatherTest () throws Exception {
		String uri = "/clima?dia=0";
		
		
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().json("{'message':'Drought period'}"));
	}
	
	@Test
	public void validCoordenatesTest () throws Exception {
		String uri = "/coordenadas?dia=0";
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().json("{"
			      		+ "'Sol':{'x':0.0,'y':0.0},"
			      		+ "'Betasoide':{'x': 1000,'y':0},"
			      		+ "'Vulcano':{'x':2000.0,'y':0},"
			      		+ "'Ferengi':{'x':500,'y':0}}"));
	}
	
	
	private void setupService() {
		HashMap<String, Coordinates> coordinatesMap = new HashMap<>();
		coordinatesMap.put("Ferengi", new Coordinates(500,0));
		coordinatesMap.put("Betasoide", new Coordinates(1000,0));
		coordinatesMap.put("Vulcano", new Coordinates(2000,0));
		coordinatesMap.put("Sol", new Coordinates(0,0));
		
		when(serviceMock.getWeatherReportByDay(Long.valueOf(0)))
		.thenReturn(new Clima("Drought period"));
		when(serviceMock.getCoordenates(Long.valueOf(0)))
		.thenReturn(coordinatesMap);		
	}
	
	
}
