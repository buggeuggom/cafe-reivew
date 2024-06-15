package com.cafe.review.dto;

import lombok.Getter;

import static java.lang.Math.round;

@Getter
public class CafeReviewDto {
    private Long id;

    private String storeName;
    private String address;
    private Double averageScore;
    private Long reviewCount;

    public CafeReviewDto(Long id, String storeName, String address, Double averageScore, Long reviewCount) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.averageScore = (double) (round(averageScore * 10) / 10);
        this.reviewCount = reviewCount;
    }
}
