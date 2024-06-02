package com.cafe.review.service;

import com.cafe.review.domain.Direction;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.service.kakao.KakaoCategorySearchService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - DirectionServiceTest")
class DirectionServiceTest {

    @Autowired
    private DirectionService directionService;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private KakaoCategorySearchService kakaoCategorySearchService;

    @AfterEach
    void setup(){
        directionRepository.deleteAll();
    }

    @Test
    @DisplayName("[requestAddressSearch][success]: 주소가 주워졌을 때")
    void if_address_given_then_directionDtoList (){
        //given
        String address = "경기도 군포시";

        List<Direction> list = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Direction.builder()
                        .distance(10.0 + (double) i / 10)
                        .inputLatitude(10.0)
                        .inputLongitude(10.0)
                        .id((long) i)
                        .inputAddress(address)
                        .inputLongitude(11.1)
                        .inputLatitude(11.1)
                        .targetPhone("test phone " + i)
                        .targetAddress("test address " + i)
                        .targetStoreName("test cafeName " + i)
                        .build())
                .toList();

        List<Direction> directions = directionRepository.saveAll(list);

        //when
        var directionDtoList = directionService.searchDirectionList(address);

        //then
        assertEquals(10, directionDtoList.size());
        assertEquals(1L, directionDtoList.get(0).getId());
    }

    @Test
    @DisplayName("[requestAddressSearch][fail]: 주소가 주워졌으나 저장된 데이터가 없어서 return 빈 리스트")
    void if_address_given_But_empty_in_db_return_emptyList (){
        //given
        String address = "경기도 군포시";

        //when
        var directionDtoList = directionService.searchDirectionList(address);

        //then
        assertTrue(directionDtoList.isEmpty());
    }

    @Test
    @DisplayName("[saveAll][success]")
    void test (){
        //given
        String address = "경기도 군포시";

        List<DirectionDto> list = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> DirectionDto.builder()
                        .distance(10.0 + (double) i / 10)
                        .inputLatitude(10.0)
                        .inputLongitude(10.0)
                        .inputAddress(address)
                        .inputLongitude(11.1)
                        .inputLatitude(11.1)
                        .targetPhone("test phone " + i)
                        .targetAddress("test address " + i)
                        .targetStoreName("test cafeName " + i)
                        .build())
                .toList();
        //when
        List<DirectionDto> directionDtoList = directionService.saveAll(list);

        //then
        assertEquals(10, directionDtoList.size());
        assertEquals(1L, directionDtoList.get(0).getId());
    }
}