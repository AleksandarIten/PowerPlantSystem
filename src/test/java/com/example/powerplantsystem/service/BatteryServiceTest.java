package com.example.powerplantsystem.service;

import com.example.powerplantsystem.dto.BatteryDto;
import com.example.powerplantsystem.model.Battery;
import com.example.powerplantsystem.repository.BatteryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BatteryServiceTest {
    @Mock
    private BatteryRepository batteryRepository;

    private BatteryServiceImp batteryService;

    private AutoCloseable autoCloseable;


    Battery battery1 = Battery.builder().
            idBattery(1L).
            nameBattery("TestBattery1").
            postcode(21000L).
            wattCapacity(800.0).build();

    Battery battery2 = Battery.builder().
            idBattery(2L).
            nameBattery("TestBattery2").
            postcode(22000L).
            wattCapacity(500.0).build();

    Battery battery3 = Battery.builder().
            idBattery(3L).
            nameBattery("TestBattery3").
            postcode(23000L).
            wattCapacity(500.0).build();

    Battery battery4 = Battery.builder().
            idBattery(4L).
            nameBattery("TestBattery4").
            postcode(24000L).
            wattCapacity(1100.0).build();

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        batteryService = new BatteryServiceImp(batteryRepository);
    }

    @AfterEach
    void teardown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void saveBatteriesTest(){
        List<Battery> batteries = List.of(battery1, battery2, battery3, battery4);
        batteryService.saveAll(batteries);
        verify(batteryRepository).saveAll(batteries);
    }

    @Test
    void findByRangeBatteriesTest(){
        Long fromPostcode = 21000L;
        Long toPostcode = 23000L;
        batteryService.findByRange(fromPostcode, toPostcode);
        verify(batteryRepository).findByRange(fromPostcode, toPostcode);
    }

    @Test
    void totalWattCapacityTest() {
        List<Battery> batteries = List.of(battery1, battery2, battery3, battery4);

        double totalCapacity = batteryService.totalWattCapacity(batteries);
        assertEquals(2900, totalCapacity);
    }

    @Test
    void averageWattCapacityTest() {
        List<Battery> batteries = List.of(battery1, battery2, battery3, battery4);

        double avgCapacity = batteryService.averageWattCapacity(batteries);
        assertEquals(725, avgCapacity);
    }

    @Test
    void totalCapacityWithEmptyList() {
        List<Battery> batteries = new ArrayList<>();
        double totalCapaciy = batteryService.totalWattCapacity(batteries);
        assertEquals(0.0, totalCapaciy);
    }
}
