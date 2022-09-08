package com.example.powerplantsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.powerplantsystem.model.Battery;
import com.example.powerplantsystem.repository.BatteryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BatteryServiceImp implements BatteryService{
	
	private BatteryRepository batteryRepository;

	@Override
	public List<Battery> findByRange(Long fromPostcode,Long toPostcode) {
		List<Battery> batteries = batteryRepository.findByRange(fromPostcode, toPostcode);
		return batteries;
	}
	
	@Override
	public double averageWattCapacity(List<Battery> bRequestDtos) {
		return bRequestDtos.stream().collect(Collectors.averagingDouble(Battery::getWattCapacity ));
	}
	
	@Override
	public double totalWattCapacity(List<Battery> batteryDtos) {
		return batteryDtos.stream().mapToDouble(Battery::getWattCapacity).sum();
	}
	
	@Override
	public List<Battery> saveAll(List<Battery> batteries) {
		return batteryRepository.saveAll(batteries);
	}
}
