package com.cga.weather_report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BETASOIDES")
public class Betasoide {
	
	@Id
    @GeneratedValue
    @Column(name = "DIA", nullable = false)
	private int dia;
	
    @Column(name = "CLIMA", length = 100, nullable = false)
	private String clima;

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	
    
}
