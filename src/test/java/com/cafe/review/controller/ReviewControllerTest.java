package com.cafe.review.controller;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Direction;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.fixture.CafeFixture;
import com.cafe.review.fixture.DirectionFixture;
import com.cafe.review.fixture.ReviewFixture;
import com.cafe.review.repository.cafe.CafeRepository;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.repository.ReviewRepository;
import com.cafe.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static com.cafe.review.exception.ErrorCode.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("api 컨트롤러 - ReviewControllerTest")
class ReviewControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private DirectionRepository directionRepository;

    @BeforeEach
    void setup(){
        reviewRepository.deleteAll();
        cafeRepository.deleteAll();

    }

    @Test
    @DisplayName("[getList][success]: cafe, review entity 가 주워 지면 review 리스트가 주워진다.")
    void cafeId_given_return_ReviewList() throws Exception {
        //given
        Cafe savedCafe = cafeRepository.save(CafeFixture.get(1));

        List<Review> reviewList = IntStream.rangeClosed(1, 10).mapToObj(i ->
                        ReviewFixture.get(i, savedCafe, passwordEncoder))
                .toList();

        Long cafeId = savedCafe.getId();
        reviewRepository.saveAll(reviewList);

        //expected
        mockMvc.perform(get("/api/v1/{cafeId}/reviews", cafeId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(reviewList.get(0).getId()))
                .andDo(print());
    }

    @Test
    @DisplayName("[getList][success]: cafe entity 가 주워 지면 빈 review 리스트가 주워진다.")
    void cafeId_given_return_empty_ReviewList() throws Exception {
        //given
        Cafe saved = cafeRepository.save(CafeFixture.get(1));

        //expected
        mockMvc.perform(get("/api/v1/{cafeId}/reviews", saved.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("[post][success]: id 가 주워지고 검증된 request 주워진다.")
    void cafeId_request_given_return_writerId_title() throws Exception {
        //given
        Cafe saved = cafeRepository.save(CafeFixture.get(1));
        var request = PostReviewRequest.builder()
                .writerId("testId")
                .password("Test010101")
                .title("title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(4)
                .serviceRating(5)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/{cafeId}/reviews", saved.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.writerId").value(request.getWriterId()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andDo(print());
    }

    @Test
    @DisplayName("[post][fail]: id 가 잘못 주워지고 검증된 request 주워진다.")
    void wrong_cafeId_request_given_return_writerId_title() throws Exception {
        //given
        Cafe saved = cafeRepository.save(CafeFixture.get(1));
        Long wrongId = saved.getId() + 101;

        var request = PostReviewRequest.builder()
                .writerId("testId")
                .password("Test010101")
                .title("title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(4)
                .serviceRating(5)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/{cafeId}/reviews", wrongId)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is(CAFE_NOT_FOUND.getStatus().value()))
                .andDo(print());
    }

    @Test
    @DisplayName("[post][fail]: id가 주워지고 검증되지 않은 request 주워진다.")
    void cafeId_and_wrong_request_given_return_writerId_title() throws Exception {
        //given
        Cafe saved = cafeRepository.save(CafeFixture.get(1));

        var request = PostReviewRequest.builder()
                .writerId("tes!")
                .password("Tes!")
                .title("타이틀")
                .comment("      ")
                .tasteRating(6)
                .ambienceRating(7)
                .serviceRating(8)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/{cafeId}/reviews", saved.getId())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    @DisplayName("[put][success]: id 가 주워지고 검증된 request 주워진다.")
    void reviewId_request_given_return_void() throws Exception {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));

        var postReviewRequest = PostReviewRequest.builder()
                .directionId(saved.getId())
                .writerId("testId")
                .password("Test010101")
                .title("title")
                .comment("test comment")
                .tasteRating(3)
                .ambienceRating(4)
                .serviceRating(5)
                .build();
//        ReviewDto reviewDto = reviewService.post(saved.getId(), postReviewRequest);
//
//        PutReviewRequest request = PutReviewRequest.builder()
//                .password("Test010101")
//                .title("title")
//                .comment("test comment")
//                .tasteRating(3)
//                .ambienceRating(4)
//                .serviceRating(5)
//                .build();
//
//        //expected
//        mockMvc.perform(put("/api/v1/reviews/{reviewId}", reviewDto.getId())
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andDo(print());
    }

    @Test
    @DisplayName("[delete][success]: id 가 주워지고 검증된 request 주워진다.")
    void reviewId_deleteRequest_given_return_void() throws Exception {
//        //given
//        Cafe saved = cafeRepository.save(CafeFixture.get(1));
//        var postReviewRequest = PostReviewRequest.builder()
//                .writerId("testId")
//                .password("Test010101")
//                .title("title")
//                .comment("test comment")
//                .tasteRating(3)
//                .ambienceRating(4)
//                .serviceRating(5)
//                .build();
//        ReviewDto reviewDto = reviewService.post(saved.getId(), postReviewRequest);
//
//        var request = DeleteReviewRequest.builder()
//                .password("Test010101")
//                .build();
//
//        //expected
//        mockMvc.perform(post("/api/v1/reviews/{reviewId}/delete", reviewDto.getId())
//                        .contentType(APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andDo(print());
    }
}