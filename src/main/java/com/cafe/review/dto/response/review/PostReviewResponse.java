package com.cafe.review.dto.response.review;

import com.cafe.review.dto.ReviewDto;
import lombok.Getter;

@Getter
public class PostReviewResponse {

    private String reviewId;

    public PostReviewResponse(String reviewId) {
        this.reviewId = reviewId;
    }

    public static PostReviewResponse fromDto(ReviewDto dto) {
        return new PostReviewResponse(
                dto.getWriterId());
    }
}
