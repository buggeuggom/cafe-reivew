package com.cafe.review.dto.response.review;

import com.cafe.review.dto.ReviewDto;
import lombok.Getter;

@Getter
public class PostReviewResponse {

    private String writerId;
    private String title;

    public PostReviewResponse(String writerId, String title) {
        this.writerId = writerId;
        this.title = title;
    }

    public static PostReviewResponse fromDto(ReviewDto dto) {
        return new PostReviewResponse(
                dto.getWriterId(),
                dto.getTitle());
    }
}
