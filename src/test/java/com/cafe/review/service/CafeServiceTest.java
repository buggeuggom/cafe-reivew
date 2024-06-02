package com.cafe.review.service;

import com.cafe.review.domain.Direction;
import com.cafe.review.repository.DirectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - CafeServiceTest")
class CafeServiceTest {

    @Autowired
    private CafeService cafeService;
    @Autowired
    private DirectionRepository directionRepository;

    @AfterEach
    void setup(){
        directionRepository.deleteAll();
    }

    @Test
    @DisplayName("[searchNearbyStoreList][success]: db에 데이터가 있는 경우")
    void if_address_given_then_db_result (){
        //given
        var address = "경기도 군포시";

        var directionList = IntStream.rangeClosed(1, 10)
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
        directionRepository.saveAll(directionList);

        //when
        var result = cafeService.searchNearbyStoreList(address);

        //then
        assertEquals(10, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("[searchNearbyStoreList][success]: db에 데이터가 없는 경우 db에 저장하고 데이터를 출력한다")
    void if_address_given_then_db_result_empty (){
        //given
        var address = "경기도 군포시";

        //when
        var result = cafeService.searchNearbyStoreList(address);


        //then
        List<Direction> repositoryAll = directionRepository.findAll();
        assertEquals(result.size(), repositoryAll.size());
        assertEquals(result.get(0).getInputAddress(), repositoryAll.get(0).getInputAddress());
        assertEquals(result.get(0).getTargetStoreName(), repositoryAll.get(0).getTargetStoreName());


    }
}