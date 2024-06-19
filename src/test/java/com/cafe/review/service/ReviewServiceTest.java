package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Direction;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.ReviewDto;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.fixture.CafeFixture;
import com.cafe.review.fixture.DirectionFixture;
import com.cafe.review.fixture.ReviewFixture;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.repository.cafe.CafeRepository;
import com.cafe.review.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.IntStream;

import static com.cafe.review.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - ReviewServiceTest")
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DirectionRepository directionRepository;

    @BeforeEach
    void setup() {
        directionRepository.deleteAll();
        reviewRepository.deleteAll();
        cafeRepository.deleteAll();
    }

    @Test
    @DisplayName("[getList][success]: 카페, 리뷰 data 가 있는 경우")
    void getList_성공_데이터가_모두_있는_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));

        List<Review> reviewList = IntStream.rangeClosed(1, 10).mapToObj(i ->
                        ReviewFixture.get(i, cafe, passwordEncoder))
                .toList();
        List<Review> saved = reviewRepository.saveAll(reviewList);

        //when
        List<ReviewDto> result = reviewService.getList(cafe.getId());
        //then
        assertEquals(10, result.size());
        assertEquals(reviewList.get(0).getId(), result.get(0).getId());
        assertEquals(reviewList.get(9).getTitle(), result.get(9).getTitle());
    }

    @Test
    @DisplayName("[getList][success]: 카페, 리뷰 data 가 있는 경우")
    void getList_성공_데이터가_카페_만_있는_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));

        //when
        List<ReviewDto> result = reviewService.getList(cafe.getId());

        //then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("[getList][fail]: 카페 id 가 잘못된 경우")
    void getList_실패_카페id가_잘못된_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));

        Long wrongId = cafe.getId() + 100;

        List<Review> reviewList = IntStream.rangeClosed(1, 10).mapToObj(i ->
                        ReviewFixture.get(i, cafe, passwordEncoder))
                .toList();
        reviewRepository.saveAll(reviewList);

        //expected
        ReviewException e = assertThrows(ReviewException.class, () -> reviewService.getList(wrongId));
        assertEquals(CAFE_NOT_FOUND, e.getErrorCode());
        assertEquals(String.format("%s 아이디의 카페가 없습니다.", wrongId), e.getMessage());
    }

    @Test
    @DisplayName("[post][success]: 거리 id가 있고 request 가 정상인 경우")
    void post_success() {
        //given
        Direction direction = directionRepository.save(DirectionFixture.get(1));

        var request = PostReviewRequest.builder()
                .directionId(direction.getId())
                .writerId("test123")
                .password("password12345")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        //when
        var reviewDto = reviewService.post(request);

        //then
        assertEquals("test123", reviewDto.getWriterId());
        assertTrue(passwordEncoder.matches("password12345", reviewDto.getPassword()));
    }
}