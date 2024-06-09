package com.cafe.review.dto.request.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteReviewRequest {

    private String password;

    @Builder
    private DeleteReviewRequest(String password) {
        this.password = password;
    }
}
