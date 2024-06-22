package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Direction;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.CafeDto;
import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.request.CafeSearchRequest;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.fixture.CafeFixture;
import com.cafe.review.fixture.DirectionFixture;
import com.cafe.review.fixture.ReviewFixture;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.repository.ReviewRepository;
import com.cafe.review.repository.cafe.CafeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.cafe.review.exception.ErrorCode.CAFE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - CafeServiceTest")
class CafeServiceTest {

    @Autowired
    private CafeService cafeService;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        directionRepository.deleteAll();
        cafeRepository.deleteAll();
    }

    @Test
    @DisplayName("[searchNearbyStoreList][success]: db에 데이터가 있는 경우")
    void searchNearbyStoreList_success_if_address_given_then_db_result() {
        //given
        var address = "경기 수원시";

        var directionList = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> DirectionFixture.get(i, address))
                .toList();
        List<Direction> existedDirection = directionRepository.saveAll(directionList);

        //when
        var result = cafeService.searchNearbyStoreList(address);

        //then
        assertEquals(10, result.size());
        assertEquals(existedDirection.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("[searchNearbyStoreList][success]: db에 데이터가 없는 경우 db에 저장하고 데이터를 출력한다")
    void searchNearbyStoreList_success_if_address_given_then_db_result_empty() {        //given
        var address = "경기도 군포시";

        //when
        var result = cafeService.searchNearbyStoreList(address);

        //then
        List<Direction> repositoryAll = directionRepository.findAll();
        assertEquals(result.size(), repositoryAll.size());
        assertEquals(result.get(0).getInputAddress(), repositoryAll.get(0).getInputAddress());
        assertEquals(result.get(0).getTargetStoreName(), repositoryAll.get(0).getTargetStoreName());
    }


    @Test
    @DisplayName("[getList][success]")
    void getList_success() {
        //given
        List<List<Review>> collect = IntStream.rangeClosed(0, 4).mapToObj(
                i -> {
                    Cafe cafe = cafeRepository.save(CafeFixture.get(i));
                    List<Review> reviewList = IntStream.range(0, 10).mapToObj(j ->
                                    ReviewFixture.get(j, cafe, passwordEncoder))
                            .collect(Collectors.toList());
                    return reviewRepository.saveAll(reviewList);
                }).collect(Collectors.toList());

        var request = CafeSearchRequest.builder()
                .roadAddress("")
                .storeName("")
                .page(1)
                .size(10)
                .build();
        //when
        List<CafeReviewDto> cafeReviewDtos = cafeService.getList(request);
        //then
        assertEquals(collect.size(), cafeReviewDtos.size());
        assertEquals(collect.get(4).get(0).getCafe().getId(), cafeReviewDtos.get(0).getId());
        assertEquals(2.0, cafeReviewDtos.get(0).getAverageScore());
        assertEquals(collect.get(4).size(), cafeReviewDtos.get(0).getReviewCount());
    }

    @Test
    @DisplayName("[getCafe][success]")
    void getCafe_success() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var cafeId = cafe.getId();

        //when
        CafeDto cafeDto = cafeService.getCafe(cafeId);

        //then
        assertEquals(cafe.getId(), cafeDto.getId());
        assertEquals(cafe.getPhone(), cafeDto.getPhone());
        assertEquals(cafe.getRoadAddressName(), cafeDto.getRoadAddressName());
        assertEquals(cafe.getStoreName(), cafeDto.getStoreName());
    }

    @Test
    @DisplayName("[getList][fail]: wrong cafe id")
    void getCafe_fail_if_wrong_cafeId() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var wrong = cafe.getId() + 100L;

        //expected
        ReviewException e = assertThrows(ReviewException.class, () -> cafeService.getCafe(wrong));
        assertEquals(CAFE_NOT_FOUND, e.getErrorCode());
        assertEquals(String.format("%s 아이디의 카페가 없습니다.", wrong), e.getMessage());
    }

}