package com.cafe.review.service;

import com.cafe.review.domain.Direction;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.exception.ErrorCode;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.fixture.DirectionFixture;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.service.kakao.KakaoCategorySearchService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static com.cafe.review.exception.ErrorCode.*;
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
    void setup() {
        directionRepository.deleteAll();
    }


    @Test
    @DisplayName("[findDirectionByDirectionId][success]: ")
    void findDirectionByDirectionId_success() {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));
        Long id = saved.getId();

        //when
        DirectionDto result = directionService.findDirectionByDirectionId(id);

        //then
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getInputAddress(), result.getInputAddress());
    }

    @Test
    @DisplayName("[findDirectionByDirectionId][fail]: DIRECTION_NOT_FOUND")
    void findDirectionByDirectionId_fail() {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));
        Long id = saved.getId();

        //expected
        ReviewException e = assertThrows(ReviewException.class, () ->
                directionService.findDirectionByDirectionId(id + 10));

        assertEquals(DIRECTION_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("[findDirectionListByAddress][success]: 주소가 주워졌을 때")
    void findDirectionListByAddress_success_if_address_given_then_directionDtoList() {
        //given
        String address = "경기도 군포시";
        List<Direction> list = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> DirectionFixture.get(i, address))
                .toList();
        List<Direction> directions = directionRepository.saveAll(list);

        //when
        var directionDtoList = directionService.findDirectionListByAddress(address);

        //then
        assertEquals(directions.get(0).getId(), directionDtoList.get(0).getId());
        assertEquals(directions.get(0).getInputAddress(), directionDtoList.get(0).getInputAddress());

    }

    @Test
    @DisplayName("[findDirectionListByAddress][success]: 주소가 주워졌으나 저장된 데이터가 없어서 return 빈 리스트")
    void findDirectionListByAddress_success_if_address_given_But_empty_in_db_return_emptyList() {
        //given
        String address = "경기도 군포시";

        //when
        var directionDtoList = directionService.findDirectionListByAddress(address);

        //then
        assertTrue(directionDtoList.isEmpty());
    }

    @Test
    @DisplayName("[saveAll][success]")
    void saveAll_success() {
        //given
        String address = "경기도 군포시";

        List<DirectionDto> list = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> DirectionDto.builder()
                        .distance(10.0 + (double) i / 10)
                        .inputLatitude(10.0)
                        .inputLongitude(10.0)
                        .inputAddress(address)
                        .targetPhone("test phone " + i)
                        .targetAddress("test address " + i)
                        .targetStoreName("test cafeName " + i)
                        .build())
                .toList();
        //when
        List<DirectionDto> directionDtoList = directionService.saveAll(list);

        //then
        assertEquals(10, directionDtoList.size());
        assertEquals(list.get(0).getInputAddress(), directionDtoList.get(0).getInputAddress());
        assertEquals(list.get(0).getDistance(), directionDtoList.get(0).getDistance());
        assertEquals(list.get(0).getInputLatitude(), directionDtoList.get(0).getInputLatitude());
        assertEquals(list.get(0).getInputLongitude(), directionDtoList.get(0).getInputLongitude());
    }


}