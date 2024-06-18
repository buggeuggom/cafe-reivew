package com.cafe.review.dto;

import com.cafe.review.domain.Cafe;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CafeDto {

    private Long id;

    private String storeName;
    private String address;
    private String phone;
    private String url;
    private String roadAddressName;

    @Builder
    private CafeDto(Long id, String storeName, String address, String phone, String url, String roadAddressName) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.roadAddressName = roadAddressName;
    }

    public static CafeDto fromEntity(Cafe entity){
        return CafeDto.builder()
                .storeName(entity.getStoreName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .url(entity.getUrl())
                .roadAddressName(entity.getRoadAddressName())
                .build();
    }
}

