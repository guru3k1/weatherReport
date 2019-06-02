package com.cga.weather_report.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.service.Service;
import com.cga.weather_report.utils.CustomErrorHandler;
import com.cga.weather_report.utils.CustomMessageHandler;

@RestController
public class Controller {
	private static final String FERENGI = "Ferengi";
	private static final String BETASOIDE = "Betasoide";
	private static final String VULCANO = "Vulcano";
	private static final String INVALID_PLANET = "El planeta no es valido";
	private static final String DAY_REQUIRED = "El dia es requerido";
	private static final String HIGHER_THAN_ZERO = "El dia debe ser 0 o superior";
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	
	@Autowired
	private Service service;
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<CustomErrorHandler> badRequest() {
		return new ResponseEntity<>(new CustomErrorHandler("El valor de dia ingresado no es valido."), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<CustomErrorHandler> handleMissingParams() {
		return new ResponseEntity<>(new CustomErrorHandler("La clave y el valor de dia no fue ingresado. Ingreselo de esta manera clima?dia=0"), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public String homeMesagge() {
		return "Bienvenido al reporte climatico de la galaxia. El sistema esta Activo";
	}


	@GetMapping(value = "/{planeta}/clima", produces = "application/json")
	public ResponseEntity<?> getWeatherByDay(@PathVariable("planeta") String planet, @RequestParam(value="dia", required=true) Integer day) {
		String rightPlanet= validatePlanet(planet);
		if(rightPlanet == null) {
			return new ResponseEntity<>(new CustomErrorHandler(INVALID_PLANET), HttpStatus.CONFLICT);
		}
		
		if(day == null) {
			return new ResponseEntity<>(new CustomErrorHandler(DAY_REQUIRED), HttpStatus.CONFLICT);
		}
		if(day < 0 ) {
			return new ResponseEntity<>(new CustomErrorHandler(HIGHER_THAN_ZERO), HttpStatus.NOT_FOUND);
		}
		
		String weather = service.getWeatherReportByDay(day,rightPlanet);
		return new ResponseEntity<>(new CustomMessageHandler(weather), HttpStatus.OK);
	}
	
	@GetMapping(value="/{planeta}/coordenadas", produces = "application/json")
	public ResponseEntity<?> getCoordenates(@PathVariable("planeta") String planet,@RequestParam(value="dia", required=true) Integer day) {
		String rightPlanet= validatePlanet(planet);
		if(rightPlanet == null) {
			return new ResponseEntity<>(new CustomErrorHandler(INVALID_PLANET), HttpStatus.CONFLICT);
		}
		
		if(day == null) {
			return new ResponseEntity<>(new CustomErrorHandler(DAY_REQUIRED), HttpStatus.CONFLICT);
		}
		if(day < 0 ) {
			return new ResponseEntity<>(new CustomErrorHandler(HIGHER_THAN_ZERO), HttpStatus.NOT_FOUND);
		}
		HashMap<String,Coordinates> planets = service.getCoordenates(day, rightPlanet);
		return new ResponseEntity<>(planets, HttpStatus.OK);
	}
	
	@GetMapping(value="/{planeta}/alineacion", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getAlignment(@PathVariable("planeta") String planet,@RequestParam(value="dia", required=true) Integer day) {
		logger.debug("Inside controller getAlignment method");
		
		String rightPlanet= validatePlanet(planet);
		if(rightPlanet == null) {
			return new ResponseEntity<>(new CustomErrorHandler(INVALID_PLANET), HttpStatus.CONFLICT);
		}
		
		if(day == null) {
			new ResponseEntity<CustomErrorHandler>(new CustomErrorHandler(DAY_REQUIRED), HttpStatus.CONFLICT);
		}
		if(day < 0 ) {
			new ResponseEntity<CustomErrorHandler>(new CustomErrorHandler(HIGHER_THAN_ZERO), HttpStatus.NOT_FOUND);
		}

		boolean alignmentResponse = service.getAlignment(day, rightPlanet);
		String response = "";
		logger.debug("Aligment method response: {}", alignmentResponse);
		if(alignmentResponse) {
			response = "Para los habitantes del planeta "+planet+" en el dia "+day+" los planetas estan alineados";
		} else {
			response = "Para los habitantes del planeta "+planet+" en el dia "+day+" los planetas no estan alineados";
		}
		return new ResponseEntity<>(new CustomMessageHandler(response), HttpStatus.OK);
	}
	
	public String validatePlanet(String planet) {
		switch(planet) {
		case FERENGI:
		case BETASOIDE:
		case VULCANO:
			return planet;
		default:
			planet = null;
	}
		return planet;
	}
}
