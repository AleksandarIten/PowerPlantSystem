package com.example.powerplantsystem.service;

import java.util.List;

import com.example.powerplantsystem.dto.BatteryDto;
import com.example.powerplantsystem.model.Battery;

public interface BatteryService {
	List<Battery> findByRange(Long postcodeFrst,Long postcodeSecond);
	double averageWattCapacity(List<Battery> bRequestDtos);
	double totalWattCapacity(List<Battery> bRequestDtos);
	List<Battery> saveAll (List<Battery>  batteries);
}
