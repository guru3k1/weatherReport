package com.cga.weatherReport;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cga.weatherReport.model.Coordinates;
import com.cga.weatherReport.model.Planet;


@SpringBootApplication
@RestController
public class WeatherReportApplication {
	private final static int CLOCKWISE = -1;
	private final static int COUNTER_CLOCKWISE = 1;
	
	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet( 1000, 5, COUNTER_CLOCKWISE);
	Planet sun = new Planet( 0, 0, 0);
	public static void main(String[] args) {
		SpringApplication.run(WeatherReportApplication.class, args);
	}

	@RequestMapping(value="/")
	public String homeMesagge() {
		return "Thanks to acces our service.";
	}
	
	@RequestMapping(value="/clima")
	public String firstApp() {
		return "Working on Geting data from database.";
	}
	
	@RequestMapping(value="/coordenadas/{dia}")
	@ResponseBody
	public ResponseEntity<HashMap<String,Coordinates>> getCoordenates(@PathVariable int dia) {
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put("ferengi", ferengi.getCoordinates(dia));
		planets.put("betasoide", betasoide.getCoordinates(dia));
		planets.put("vulcano", vulcano.getCoordinates(dia));
		planets.put("sun", sun.getCoordinates(dia));
		return new ResponseEntity<HashMap<String, Coordinates>>(planets, HttpStatus.OK);
	}
	
}

