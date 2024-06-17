package com.cafe.review.controller;

import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.request.CafeSearchRequest;
import com.cafe.review.dto.request.review.DeleteReviewRequest;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.dto.request.review.PutReviewRequest;
import com.cafe.review.dto.response.review.ReviewResponse;
import com.cafe.review.dto.response.review.PostReviewResponse;
import com.cafe.review.service.CafeService;
import com.cafe.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final CafeService cafeService;

    @PostMapping("/reviews")
    public PostReviewResponse post(@RequestBody @Valid PostReviewRequest request) {

        return PostReviewResponse.fromDto(reviewService.post(request));
    }

    @PutMapping("/reviews/{reviewId}")
    public void put(@PathVariable Long reviewId, @RequestBody @Valid PutReviewRequest request) {
        reviewService.edit(reviewId, request);
    }

    @PostMapping("/reviews/{reviewId}/delete")
    public void delete(@PathVariable Long reviewId, @RequestBody @Valid DeleteReviewRequest request) {
        reviewService.delete(reviewId, request);
    }
}
