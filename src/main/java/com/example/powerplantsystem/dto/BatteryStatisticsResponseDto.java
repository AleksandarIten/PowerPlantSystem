package com.example.powerplantsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryStatisticsResponseDto {
	List<BatteryDto> batteryDtoList;
	double totalCapacity;
	double avergeWattCapacity;
}
