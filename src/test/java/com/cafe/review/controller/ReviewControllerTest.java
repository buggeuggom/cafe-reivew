package com.cafe.review.controller;

import com.cafe.review.domain.Direction;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.fixture.DirectionFixture;
import com.cafe.review.repository.DirectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.cafe.review.exception.ErrorCode.DIRECTION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("api 컨트롤러 - ReviewControllerTestTest")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DirectionRepository directionRepository;

    @Test
    @DisplayName("[search][success]")
    void search_success() throws Exception {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));

        var request = PostReviewRequest.builder()
                .directionId(saved.getId())
                .writerId("testId01")
                .password("passwordtest123")
                .title("테스트 타이틀")
                .comment("comment")
                .tasteRating(1)
                .ambienceRating(2)
                .serviceRating(4)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.writerId").value("testId01"))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andDo(print());
    }

    @Test
    @DisplayName("[search][fail]: DIRECTION_NOT_FOUND <- directionId is wrong")
    void search_fail_by_wrong_directionId() throws Exception {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));

        var request = PostReviewRequest.builder()
                .directionId(saved.getId() + 100L)
                .writerId("testId01")
                .password("passwordtest123")
                .title("테스트 타이틀")
                .comment("comment")
                .tasteRating(1)
                .ambienceRating(2)
                .serviceRating(4)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(DIRECTION_NOT_FOUND.getStatus().value()))
                .andDo(print());
    }

    @Test
    @DisplayName("[search][fail]: MethodArgumentNotValidException <- request is not validated")
    void search_fail_by_request_validation_wrong() throws Exception {
        //given
        Direction saved = directionRepository.save(DirectionFixture.get(1));

        var request = PostReviewRequest.builder()
                .directionId(saved.getId())
                .writerId("testId01")
                .password("passwordtest123")
                .comment(" ")
                .tasteRating(6)
                .ambienceRating(6)
                .serviceRating(6)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목은 빈칸일 수 없습니다."))
                .andExpect(jsonPath("$.validation.comment").value("코멘트는 빈칸일 수 없습니다."))
                .andExpect(jsonPath("$.validation.tasteRating").value("맛 평가는 0 ~ 5의 숫자만 가능합니다."))
                .andExpect(jsonPath("$.validation.ambienceRating").value("분위기 평가는 0 ~ 5의 숫자만 가능합니다."))
                .andExpect(jsonPath("$.validation.serviceRating").value("서비스 평가는 0 ~ 5의 숫자만 가능합니다."))
                .andDo(print());
    }
}