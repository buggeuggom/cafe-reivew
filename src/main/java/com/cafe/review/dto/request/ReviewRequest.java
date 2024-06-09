package com.cafe.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequest {

    private String title;
    private String memo;

    @Builder
    private ReviewRequest(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }
}
