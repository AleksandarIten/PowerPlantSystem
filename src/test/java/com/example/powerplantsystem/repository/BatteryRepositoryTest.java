package com.example.powerplantsystem.repository;

import com.example.powerplantsystem.model.Battery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BatteryRepositoryTest {
    @Autowired
    private BatteryRepository batteryRepository;

    Battery battery1 = Battery.builder()
            .idBattery(1l)
            .nameBattery("TestBattery1")
            .postcode(21000L)
            .wattCapacity(800.0)
            .build();
    Battery battery2 = Battery.builder()
            .idBattery(2l)
            .nameBattery("TestBattery2")
            .postcode(22000L)
            .wattCapacity(500.0)
            .build();
    Battery battery3 = Battery.builder()
            .idBattery(3l)
            .nameBattery("TestBattery3")
            .postcode(23000L)
            .wattCapacity(500.0)
            .build();
    Battery battery4 = Battery.builder()
            .idBattery(4l)
            .nameBattery("TestBattery4")
            .postcode(11000L)
            .wattCapacity(1000.0)
            .build();


    @Test
    void findByRangeTest() throws Exception{
        List<Battery> batteries = List.of(battery1, battery2, battery3, battery4);
        List<Battery> savedBatteries = batteryRepository.saveAll(batteries);
        Long fromPostcode = 21000L;
        Long toPostcode = 23000L;

        List<Battery> filteredBatteries = batteryRepository.findByRange(fromPostcode, toPostcode);

        assertEquals(3, filteredBatteries.size());
        assertEquals(1, filteredBatteries.get(0).getIdBattery());
        assertEquals(2, filteredBatteries.get(1).getIdBattery());
        assertEquals(3, filteredBatteries.get(2).getIdBattery());
    }

}
