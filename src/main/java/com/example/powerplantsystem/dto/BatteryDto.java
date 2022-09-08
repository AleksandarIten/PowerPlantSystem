package com.example.powerplantsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryDto {
	private String nameBattery;
	private Long postcode;
	private double wattCapacity;
}
