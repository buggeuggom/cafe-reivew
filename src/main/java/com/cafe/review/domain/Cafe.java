package com.cafe.review.domain;

import com.cafe.review.dto.DirectionDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Cafe extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String storeName;
    private String address;
    private String phone;
    private String url;
    private String roadAddressName;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "cafe")
    private final List<Review>  reviews = new ArrayList<>();


    @Builder
    private Cafe(String storeName, String address, String phone, String url, String roadAddressName) {
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.roadAddressName = roadAddressName;
    }

    public static Cafe fromDirectionDto(DirectionDto directionDto) {
        return Cafe.builder()
                .storeName(directionDto.getTargetStoreName())
                .address(directionDto.getTargetAddress())
                .phone(directionDto.getTargetPhone())
                .url(directionDto.getTargetUrl())
                .roadAddressName(directionDto.getTargetRoadAddressName())
                .build();
    }

}
