package com.example.powerplantsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.powerplantsystem.dto.BatteryStatisticsResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.powerplantsystem.dto.BatteryDto;
import com.example.powerplantsystem.model.Battery;
import com.example.powerplantsystem.service.BatteryServiceImp;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class BatteryController {

	private BatteryServiceImp batteryServiceImp;
	
	private ModelMapper mapper;

	@PostMapping(path = "/saveBatteries",
			consumes = "application/json", 
			produces = "application/json")
	public ResponseEntity<List<BatteryDto>> saveBatteries(@RequestBody List<BatteryDto> batteryDtos) {
		List<Battery> batteries = convertDtoListToBatteryList(batteryDtos);

		final List<Battery> savedBatteries = batteryServiceImp.saveAll(batteries);
		final List<BatteryDto> savedBatteriesDto = convertBatteryListToDtoList(savedBatteries);
		return new ResponseEntity<>(savedBatteriesDto, HttpStatus.CREATED);
	}
	@GetMapping("/getStatistic/{fromPostcode}/{toPostcode}")
	public ResponseEntity<BatteryStatisticsResponseDto> getStatistic(@PathVariable Long fromPostcode, @PathVariable Long toPostcode){
		List<Battery> batteriesInRange = batteryServiceImp.findByRange(fromPostcode,toPostcode);
		double avgWattCapacity = batteryServiceImp.averageWattCapacity(batteriesInRange);
		double totalWattCapacity = batteryServiceImp.totalWattCapacity(batteriesInRange);
		BatteryStatisticsResponseDto batteryStatisticsResponseDto = new BatteryStatisticsResponseDto(convertBatteryListToDtoList(batteriesInRange),totalWattCapacity,avgWattCapacity);
		return new ResponseEntity<>(batteryStatisticsResponseDto, HttpStatus.OK);
	}

	public List<BatteryDto> convertBatteryListToDtoList(List<Battery> batteries){
		return batteries.stream()
				.map(battery -> mapper.map(battery, BatteryDto.class))
				.collect(Collectors.toList());
	}
	public List<Battery> convertDtoListToBatteryList(List<BatteryDto> batteryDtos){
		return batteryDtos.stream()
				.map(battery -> mapper.map(battery, Battery.class))
				.collect(Collectors.toList());
	}
}
