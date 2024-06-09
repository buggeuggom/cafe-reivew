package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.ReviewDto;
import com.cafe.review.dto.request.review.DeleteReviewRequest;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.dto.request.review.PutReviewRequest;
import com.cafe.review.fixture.CafeFixture;
import com.cafe.review.fixture.ReviewFixture;
import com.cafe.review.repository.CafeRepository;
import com.cafe.review.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.IntStream;

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

    @BeforeEach
    void setup() {
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
        Long wrongId = 2L;

        Cafe cafe = cafeRepository.save(CafeFixture.get(1));

        List<Review> reviewList = IntStream.rangeClosed(1, 10).mapToObj(i ->
                        ReviewFixture.get(i, cafe, passwordEncoder))
                .toList();
        reviewRepository.saveAll(reviewList);

        //expected
        assertThrows(RuntimeException.class, () -> reviewService.getList(wrongId));
    }

    @Test
    @DisplayName("[post][success]: 카페 id가 있고 request 가 정상인 경우")
    void post_성공_카페id가_있는_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));

        var request = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        //when
        var reviewDto = reviewService.post(1L, request);

        //then
        assertEquals("test writer id", reviewDto.getWriterId());
        assertTrue(passwordEncoder.matches("test password", reviewDto.getPassword()));
    }

    @Test
    @DisplayName("[post][fail]: 카페 id가 잘못 되었고 request 가 정상인 경우")
    void post_실패_카페id가_잘못되는_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        Long cafeId = cafe.getId();

        var request = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        //expected
        assertThrows(RuntimeException.class, () -> reviewService.post(cafeId + 1, request));
    }

    @Test
    @DisplayName("[edit][success]: 리뷰 id가 있고 request 비밀 번호가 정상인 경우")
    void edit_성공_리뷰id가_있고_request가_정상인_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var postReviewRequest = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        ReviewDto reviewDto = reviewService.post(1L, postReviewRequest);

        var request = PutReviewRequest.builder()
                .password("test password")
                .title("test title changed")
                .comment("test comment changed")
                .tasteRating(4)
                .ambienceRating(1)
                .serviceRating(5)
                .build();
        //when
        reviewService.edit(reviewDto.getId(), request);

        //then
        Review review = reviewRepository.findById(reviewDto.getId()).get();

        assertEquals(1, reviewRepository.findAll().size());
        assertEquals(request.getTitle(), review.getTitle());
        assertEquals(request.getComment(), review.getComment());
        assertEquals(request.getTasteRating(), review.getTasteRating());
        assertEquals(request.getAmbienceRating(), review.getAmbienceRating());
        assertEquals(request.getServiceRating(), review.getServiceRating());
    }

    @Test
    @DisplayName("[edit][fail]: 리뷰 id가 없고 request 비밀 번호가 정상인 경우")
    void edit_실패_리뷰id가_없고_request가_정상인_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var postReviewRequest = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        ReviewDto reviewDto = reviewService.post(1L, postReviewRequest);

        var request = PutReviewRequest.builder()
                .password("test password")
                .title("test title changed")
                .comment("test comment changed")
                .tasteRating(4)
                .ambienceRating(1)
                .serviceRating(5)
                .build();

        //expected
        assertThrows(RuntimeException.class, () -> reviewService.edit(reviewDto.getId() + 1, request));
    }

    @Test
    @DisplayName("[edit][fail]: 리뷰 id가 있고 request 비밀 번호가 다른 경우")
    void edit_실패_리뷰id가_있고_request가_잘못된_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var postReviewRequest = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        ReviewDto reviewDto = reviewService.post(1L, postReviewRequest);

        var request = PutReviewRequest.builder()
                .password("test password wrong")
                .title("test title changed")
                .comment("test comment changed")
                .tasteRating(4)
                .ambienceRating(1)
                .serviceRating(5)
                .build();

        //expected
        assertThrows(RuntimeException.class, () -> reviewService.edit(reviewDto.getId() + 1, request));
    }

    @Test
    @DisplayName("[delete][fail]: 카페 id가 없고 request 가 정상인 경우")
    void delete_실패_카페id가_없고_request가_정상인_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var postReviewRequest = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        ReviewDto reviewDto = reviewService.post(cafe.getId(), postReviewRequest);

        var deleteRequest = DeleteReviewRequest.builder()
                .password("test password")
                .build();

        //expected
        assertThrows(RuntimeException.class, ()->reviewService.delete(reviewDto.getId()+1, deleteRequest));
    }

    @Test
    @DisplayName("[delete][fail]: 카페 id가 있고 request 가 없는 경우")
    void delete_실패_카페id가_있고_request가_정상인_경우() {
        //given
        Cafe cafe = cafeRepository.save(CafeFixture.get(1));
        var postReviewRequest = PostReviewRequest.builder()
                .writerId("test writer id")
                .password("test password")
                .title("test title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(3)
                .serviceRating(3)
                .build();
        ReviewDto reviewDto = reviewService.post(cafe.getId(), postReviewRequest);

        var deleteRequest = DeleteReviewRequest.builder()
                .password("wrong test password")
                .build();

        //expected
        assertThrows(RuntimeException.class, ()->reviewService.delete(reviewDto.getId(), deleteRequest));

    }

}