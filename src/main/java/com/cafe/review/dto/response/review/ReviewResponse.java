package com.cafe.review.dto.response.review;

import com.cafe.review.dto.ReviewDto;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReviewResponse {

    private Long id;
    private String writerId;
    private String title;
    private Integer tasteRating;
    private Integer ambienceRating;
    private Integer serviceRating;
    private String comment;
    private LocalDate createdAt;

    private ReviewResponse(Long id, String writerId, String title, Integer tasteRating, Integer ambienceRating, Integer serviceRating, String comment, LocalDate createdAt) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public static ReviewResponse fromDto(ReviewDto dto){
        return new ReviewResponse(
                dto.getId(),
                dto.getWriterId(),
                dto.getTitle(),
                dto.getTasteRating(),
                dto.getAmbienceRating(),
                dto.getServiceRating(),
                dto.getComment(),
                dto.getCreatedAt().toLocalDate()
        );
    }
}
