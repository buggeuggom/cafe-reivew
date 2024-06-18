package com.cafe.review.controller;

import com.cafe.review.dto.DirectionDto;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.service.CafeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("api 컨트롤러 - DirectionControllerTest")
class DirectionControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private CafeService cafeService;

    @AfterEach
    void clean() {
        directionRepository.deleteAll();
    }

    @Test
    @DisplayName("[search][success]")
    void if_address_given_success() throws Exception {
        //given
        String address = "경기도 군포시";

        //when
        mockMvc.perform(get("/api/v1/directions")
                        .contentType(APPLICATION_JSON)
                        .queryParam("address", address))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("[searchSpecific][success]")
    void if_directionId_given_success() throws Exception {
        //given
        String address = "경기도 군포시";
        List<DirectionDto> directionDtoList = cafeService.searchNearbyStoreList(address);

        //expected
        mockMvc.perform(get("/api/v1/directions/{directionId}", directionDtoList.get(0).getId())
                        .contentType(APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}