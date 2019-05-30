package com.cga.weatherReport.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cga.weatherReport.model.Coordinates;
import com.cga.weatherReport.model.Planet;
import com.cga.weatherReport.utils.Alignment;
import com.cga.weatherReport.utils.InsideTriangle;
@RestController
public class Controller {

	private final static int CLOCKWISE = -1;
	private final static int COUNTER_CLOCKWISE = 1;
	
	@Autowired
	private Alignment planetsAlignment;
	
	@Autowired
	private InsideTriangle insideTriangle;
	
	
	Planet ferengi = new Planet(500, 1, CLOCKWISE);
	Planet betasoide = new Planet(2000, 3, CLOCKWISE);
	Planet vulcano = new Planet( 1000, 5, COUNTER_CLOCKWISE);
	Planet sun = new Planet( 0, 0, 0);
	
	@RequestMapping(value="/")
	public String homeMesagge() {
		return "Thanks to acces our service.";
	}
	
	@RequestMapping(value="/weather/day/{day}")
	public ResponseEntity<String> getWeatherByDay(@PathVariable int day){
		String weather= "Normal";
		if(planetsAlignment.areAligned(ferengi.getCoordinates(day), betasoide.getCoordinates(day), vulcano.getCoordinates(day))){
			weather= "Optimal conditions of pressure and temperature";
			if(planetsAlignment.areAlignedWithSun(ferengi.getCoordinates(day), vulcano.getCoordinates(day))) {
				weather= "Drought period";
			}
		}
		else if(InsideTriangle.itsRaining(ferengi.getCoordinates(day), betasoide.getCoordinates(day), vulcano.getCoordinates(day), sun.getCoordinates(day))) {
			weather = "Rainy";
		}
		return new ResponseEntity<>(weather, HttpStatus.OK);
	}
	
	@RequestMapping(value="/coordenadas/{day}")
	@ResponseBody
	public ResponseEntity<HashMap<String,Coordinates>> getCoordenates(@PathVariable int day) {
		HashMap<String,Coordinates> planets = new HashMap<>();
		planets.put("ferengi", ferengi.getCoordinates(day));
		planets.put("betasoide", betasoide.getCoordinates(day));
		planets.put("vulcano", vulcano.getCoordinates(day));
		planets.put("sun", sun.getCoordinates(day));
		return new ResponseEntity<HashMap<String, Coordinates>>(planets, HttpStatus.OK);
	}
	
	@RequestMapping(value="/alineacion/{dia}")
	@ResponseBody
	public String getAligment(@PathVariable int dia) {
		boolean alignmentResponse = planetsAlignment.areAligned(ferengi.getCoordinates(dia), betasoide.getCoordinates(dia), vulcano.getCoordinates(dia));
		if(alignmentResponse) {
			return "Los planetas estan alineados";
		} else {
			return "Los planetas no estan alineados";
		}
	}
	
}
