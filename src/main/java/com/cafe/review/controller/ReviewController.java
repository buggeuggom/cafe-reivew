package com.cafe.review.controller;


import com.cafe.review.dto.request.review.DeleteReviewRequest;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.dto.request.review.PutReviewRequest;
import com.cafe.review.dto.response.review.PostReviewResponse;
import com.cafe.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public PostReviewResponse post(@RequestBody @Valid PostReviewRequest request) {

        return PostReviewResponse.fromDto(reviewService.post(request));
    }

    @PutMapping("/{reviewId}")
    public void put(@PathVariable Long reviewId, @RequestBody @Valid PutReviewRequest request) {
        reviewService.edit(reviewId, request);
    }

    @PostMapping("/{reviewId}/delete")
    public void delete(@PathVariable Long reviewId, @RequestBody @Valid DeleteReviewRequest request) {
        reviewService.delete(reviewId, request);
    }
}
