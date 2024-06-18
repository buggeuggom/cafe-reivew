package com.cafe.review.controller;

import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.request.CafeSearchRequest;
import com.cafe.review.dto.response.cafe.CafeResponse;
import com.cafe.review.dto.response.review.ReviewResponse;
import com.cafe.review.service.CafeService;
import com.cafe.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;
    private final ReviewService reviewService;

    @GetMapping()
    public List<CafeReviewDto> getList(@ModelAttribute CafeSearchRequest request) {
        return cafeService.getList(request);
    }

    @GetMapping("/{cafeId}")
    public CafeResponse get(@PathVariable Long cafeId) {
        return CafeResponse.fromDto(cafeService.getCafe(cafeId));
    }

    @GetMapping("/{cafeId}/reviews")
    public List<ReviewResponse> getList(@PathVariable Long cafeId) {

        return reviewService.getList(cafeId).stream()
                .map(ReviewResponse::fromDto)
                .collect(Collectors.toList());
    }

}
