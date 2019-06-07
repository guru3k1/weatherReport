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
	private String climaName;

	public Clima(String climaName) {
		super();
		this.climaName = climaName;
	}

	public Clima() {
		super();
	}

	public Long getDia() {
		return dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public String getClimaName() {
		return climaName;
	}

	public void setClimaName(String climaName) {
		this.climaName = climaName;
	}
	
	

}
