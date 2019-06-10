package com.cga.weather_report.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cga.weather_report.model.Clima;
import com.cga.weather_report.model.Coordinates;
import com.cga.weather_report.service.Service;
import com.cga.weather_report.utils.CustomErrorHandler;
import com.cga.weather_report.utils.CustomMessageHandler;

/**
 * @author Cesar Amadori
 *
 */

@RestController
public class Controller {
	private static final String INVALID_ENTRY = "La clave y el valor de dia no fue ingresado. Ingreselo de esta manera clima?dia=0";
	private static final String INVALID_DAY = "El valor de dia ingresado no es valido.";
	private static final String DAY_REQUIRED = "El dia es requerido";
	private static final String WEATHER_NOT_FOUND = "Clima no encontrado";
	private static final String HIGHER_THAN_ZERO = "El dia debe ser 0 o superior";
	private static final String WELCOME_MESSAGE = "Bienvenido al reporte climatico de la galaxia. El sistema esta Activo";
	
	private static final int DAY_DB_DIFERENCE = 1;

	@Autowired
	private Service service;

	/**
	 *
	 * @return Error message for not valid day entry
	 */
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<CustomErrorHandler> badRequest() {
		return new ResponseEntity<>(new CustomErrorHandler(INVALID_DAY),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<CustomErrorHandler> handleMissingParams() {
		return new ResponseEntity<>(
				new CustomErrorHandler(INVALID_ENTRY),
				HttpStatus.BAD_REQUEST);
	}
	/**
	 * <p><i>Verifies the system is up and running</i></p> 
	 * 
	 * @return A welcome message
	 */
	@GetMapping(value = "/", produces = "application/json")
	public String homeMesagge() {
		return WELCOME_MESSAGE;
	}

	/**
	 * <p><i>Application end point to get weather by day </i></p> 
	 * 
	 * @param day Day
	 * 
	 * @return A message with the weather of the day.
	 */
	@GetMapping(value = "/clima", produces = "application/json")
	public ResponseEntity<?> getWeatherByDay(@RequestParam(value = "dia", required = true) Long day) {

		if (day == null) {
			return new ResponseEntity<>(new CustomErrorHandler(DAY_REQUIRED), HttpStatus.CONFLICT);
		}
		if (day < 0) {
			return new ResponseEntity<>(new CustomErrorHandler(HIGHER_THAN_ZERO), HttpStatus.NOT_FOUND);
		}

		Clima weather = service.getWeatherReportByDay(day + DAY_DB_DIFERENCE);
		if (weather == null) {
			return new ResponseEntity<>(new CustomErrorHandler(WEATHER_NOT_FOUND), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new CustomMessageHandler(weather.getClimaName()), HttpStatus.OK);
	}

	/**
	 * <p><i>Application end point to get worlds and sun coordinates by day </i></p> 
	 * 
	 * @param day Day
	 * 
	 * @return A Json with the coordinates of the worlds and the sun in a specific day.
	 */
	@GetMapping(value = "/coordenadas", produces = "application/json")
	public ResponseEntity<?> getCoordenates(@RequestParam(value = "dia", required = true) Long day) {

		if (day == null) {
			return new ResponseEntity<>(new CustomErrorHandler(DAY_REQUIRED), HttpStatus.CONFLICT);
		}
		if (day < 0) {
			return new ResponseEntity<>(new CustomErrorHandler(HIGHER_THAN_ZERO), HttpStatus.NOT_FOUND);
		}
		HashMap<String, Coordinates> planets = service.getCoordenates(day);
		return new ResponseEntity<>(planets, HttpStatus.OK);
	}

	/**
	 * <p><i>Application end point to get the weather report of all the days since the application is running </i></p> 
	 * 
	 * @return A Map with the quantity of days of each weather type since the application is running.
	 */
	@GetMapping(value = "/informe", produces = "application/json")
	public ResponseEntity<?> getReport() {
		HashMap<String, String> report = service.getReport();
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
}
