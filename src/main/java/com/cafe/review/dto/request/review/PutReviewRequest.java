package com.cafe.review.dto.request.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PutReviewRequest {

    private String password;

    private String title;
    private String comment;

    private Integer tasteRating;
    private Integer ambienceRating; //분위기
    private Integer serviceRating;

    @Builder
    private PutReviewRequest(String password, String title, String comment, Integer tasteRating, Integer ambienceRating, Integer serviceRating) {
        this.password = password;
        this.title = title;
        this.comment = comment;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
    }
}
