package com.example.powerplantsystem.model;

import javax.persistence.*;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "battery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Battery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBattery;
	
	@Column(name = "nameBattery")
	private String nameBattery;
	
	@Column(name = "postcode")
	private Long postcode;
	
	@Column(name = "wattCapacity")
	private double wattCapacity;
}
