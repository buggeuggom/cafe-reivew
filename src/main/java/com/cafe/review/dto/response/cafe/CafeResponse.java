package com.cafe.review.dto.response.cafe;

import com.cafe.review.dto.CafeDto;
import lombok.Getter;

@Getter
public class CafeResponse {

    private Long id;

    private String storeName;
    private String address;
    private String phone;
    private String url;
    private String roadAddressName;


    private CafeResponse(Long id, String storeName, String address, String phone, String url, String roadAddressName) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.roadAddressName = roadAddressName;
    }

    public static CafeResponse fromDto(CafeDto dto) {
        return new CafeResponse(
                dto.getId(),
                dto.getStoreName(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getUrl(),
                dto.getRoadAddressName()
        );
    }
}
