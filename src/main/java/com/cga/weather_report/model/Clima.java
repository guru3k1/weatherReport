package com.cga.weather_report.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Clima")
@Embeddable
public class Clima {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIA", nullable = false)
	private Long dia;
	
    @Column(name = "CLIMA", length = 100, nullable = false)
	private String clima;

   

	public Clima() {
		super();
	}

	public Clima(String clima) {
		super();
		this.clima = clima;
	}

	public Long getDia() {
		return dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}
	
}
