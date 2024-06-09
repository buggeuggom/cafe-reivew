package com.cafe.review.controller;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import com.cafe.review.fixture.CafeFixture;
import com.cafe.review.fixture.ReviewFixture;
import com.cafe.review.repository.CafeRepository;
import com.cafe.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

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


    @BeforeEach
    void setup(){
        reviewRepository.deleteAll();
        cafeRepository.deleteAll();

    }

    @Test
    @DisplayName("[getList][success]: cafe, review entity 가 주워 지면 review 리스트가 주워진다.")
    void cafeId_given_return_ReviewList() throws Exception {
        //given
        Cafe saved = cafeRepository.save(CafeFixture.get(1));

        List<Review> reviewList = IntStream.rangeClosed(1, 10).mapToObj(i ->
                        ReviewFixture.get(i, saved, passwordEncoder))
                .toList();

        List<Review> reviewEntityList = reviewRepository.saveAll(reviewList);

        //expected
        mockMvc.perform(get("/api/v1/{cafeId}/reviews", 1)
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
        mockMvc.perform(get("/api/v1/{cafeId}/reviews", 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }
}