package com.cga.weather_report.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import com.cga.weather_report.service.Service;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
    private MockMvc mvc;
	@Mock
	private Service serviceMock;

	@Before
	public void setup() {
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
	public void invalidWolrdTest () throws Exception {
		String uri = "/ferengi/clima?dia=0";
		
		
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isConflict())
			      .andExpect(content().json("{'errorMessage':'El planeta no es valido'}"));
	}
	
	@Test
	public void nullDayTest () throws Exception {
		String uri = "/Ferengi/clima?dia=";
		
		
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isConflict())
			      .andExpect(content().json("{'errorMessage':'El dia es requerido'}"));
	}
	
	@Test
	public void nlowerThanZeroDayTest () throws Exception {
		String uri = "/Ferengi/clima?dia=-1";
		
		
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound())
			      .andExpect(content().json("{'errorMessage':'El dia debe ser 0 o superior'}"));
	}
	
	@Test
	public void validClimaTest () throws Exception {
		String uri = "/Ferengi/clima?dia=0";
		
		
		mvc.perform(get(uri)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().json("{'message':'Drought period'}"));
	}
	
	
	private void setupService() {
		when(serviceMock.getWeatherReportByDay(1, "Ferengi"))
		.thenReturn("Drought period");
		
	}
}
