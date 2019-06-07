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

@RestController
public class Controller {

	private static final String DAY_REQUIRED = "El dia es requerido";
	private static final String HIGHER_THAN_ZERO = "El dia debe ser 0 o superior";
	private static final int DAY_DB_DIFERENCE = 1;

	@Autowired
	private Service service;

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<CustomErrorHandler> badRequest() {
		return new ResponseEntity<>(new CustomErrorHandler("El valor de dia ingresado no es valido."),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<CustomErrorHandler> handleMissingParams() {
		return new ResponseEntity<>(
				new CustomErrorHandler(
						"La clave y el valor de dia no fue ingresado. Ingreselo de esta manera clima?dia=0"),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/", produces = "application/json")
	public String homeMesagge() {
		return "Bienvenido al reporte climatico de la galaxia. El sistema esta Activo";
	}

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
			return new ResponseEntity<>(new CustomErrorHandler("Clima no encontrado"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new CustomMessageHandler(weather.getClimaName()), HttpStatus.OK);
	}

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

	@GetMapping(value = "/informe", produces = "application/json")
	public ResponseEntity<?> getReport() {
		HashMap<String, String> report = service.getReport();
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
}
