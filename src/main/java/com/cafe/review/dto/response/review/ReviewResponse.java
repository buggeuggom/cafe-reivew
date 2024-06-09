package com.cafe.review.dto.response.review;

import com.cafe.review.dto.ReviewDto;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long id;
    private String writerId;
    private String title;
    private Integer tasteRating;
    private Integer ambienceRating;
    private Integer serviceRating;

    private ReviewResponse(Long id, String writerId, String title, Integer tasteRating, Integer ambienceRating, Integer serviceRating) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
    }

    public static ReviewResponse fromDto(ReviewDto dto){
        return new ReviewResponse(
                dto.getId(),
                dto.getWriterId(),
                dto.getTitle(),
                dto.getTasteRating(),
                dto.getAmbienceRating(),
                dto.getServiceRating()
        );
    }
}
