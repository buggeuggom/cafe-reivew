package com.cafe.review.dto;

import com.cafe.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {

    private Long id;

    private String writerId;
    private String password;

    private String title;
    private String comment;
    private CafeDto cafeDto;
    private Integer tasteRating;
    private Integer ambienceRating; //분위기
    private Integer serviceRating;
    private LocalDateTime createdAt;

    private ReviewDto(Long id, String writerId, String password, String title, String comment, CafeDto cafeDto, Integer tasteRating, Integer ambienceRating, Integer serviceRating, LocalDateTime createdAt) {
        this.id = id;
        this.writerId = writerId;
        this.password = password;
        this.title = title;
        this.comment = comment;
        this.cafeDto = cafeDto;
        this.tasteRating = tasteRating;
        this.ambienceRating = ambienceRating;
        this.serviceRating = serviceRating;
        this.createdAt = createdAt;
    }

    public static ReviewDto fromEntity(Review entity) {
        return new ReviewDto(
                entity.getId(),

                entity.getWriterId(),
                entity.getPassword(),

                entity.getTitle(),
                entity.getComment(),
                CafeDto.fromEntity(entity.getCafe()),
                entity.getTasteRating(),
                entity.getAmbienceRating(),
                entity.getServiceRating(),

                entity.getCreatedAt()
        );
    }
}
