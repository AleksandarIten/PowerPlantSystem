package com.example.powerplantsystem.controller;

import com.example.powerplantsystem.dto.BatteryDto;
import com.example.powerplantsystem.model.Battery;
import com.example.powerplantsystem.service.BatteryServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryController.class)
public class BatteryControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BatteryServiceImp batteryServiceImp;

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

    @Test
    void saveBatteriesTest() throws Exception{
        List<Battery> batteries = List.of(battery1,battery2,battery3,battery4);

        List<Battery> batteriesWithNullIds = batteries.stream()
                .map(battery -> new Battery(null, battery.getNameBattery(), battery.getPostcode(), battery.getWattCapacity()))
                .collect(Collectors.toList());

        List<BatteryDto> batteriesDto = batteries.stream()
                .map(battery -> modelMapper.map(battery, BatteryDto.class))
                .collect(Collectors.toList());

        Mockito.when(batteryServiceImp.saveAll(batteriesWithNullIds)).thenReturn(batteries);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/saveBatteries")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(batteriesDto));


        mvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].nameBattery").exists())
                .andExpect(jsonPath("$[0].postcode").exists())
                .andExpect(jsonPath("$[0].wattCapacity").exists())
                .andExpect(jsonPath("$[1].nameBattery").exists())
                .andExpect(jsonPath("$[1].postcode").exists())
                .andExpect(jsonPath("$[1].wattCapacity").exists())
                .andExpect(jsonPath("$[2].nameBattery").exists())
                .andExpect(jsonPath("$[2].postcode").exists())
                .andExpect(jsonPath("$[2].wattCapacity").exists())
                .andExpect(jsonPath("$[3].nameBattery").exists())
                .andExpect(jsonPath("$[3].postcode").exists())
                .andExpect(jsonPath("$[3].wattCapacity").exists())
                .andReturn();
    }

    @Test
    void getStatisticTest() throws Exception{
        Long fromPostcode = 21000L;
        Long toPostcode = 23000L;

        List<Battery> batteries = new ArrayList<Battery>();

        Mockito.when(batteryServiceImp.findByRange(fromPostcode,toPostcode)).thenReturn(batteries);
        Mockito.when(batteryServiceImp.averageWattCapacity(batteries)).thenReturn(600.0);
        Mockito.when(batteryServiceImp.totalWattCapacity(batteries)).thenReturn(1800.0);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get("/api/getStatistic/{fromPostcode}/{toPostcode}",Long.toString(fromPostcode),Long.toString(toPostcode))
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryDtoList").isEmpty())
                .andExpect(jsonPath("$.totalCapacity").value(1800.0))
                .andExpect(jsonPath("$.avergeWattCapacity").value(600.0))
                .andReturn();
    }
}
