package com.cga.weather_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class WeatherReportApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherReportApplication.class, args);
	}

}
