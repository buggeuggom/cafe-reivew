package com.cafe.review.dto.response.direction;

import com.cafe.review.dto.DirectionDto;
import lombok.Getter;

@Getter
public class SpecificDirectionResponse {

    private Long id;
    //현위치
    private String inputAddress;
    //목적지
    private String targetStoreName;
    private String targetAddress;
    private String targetPhone;
    private String targetUrl;
    private String targetRoadAddressName;

    private SpecificDirectionResponse(Long id, String inputAddress, String targetStoreName, String targetAddress, String targetPhone, String targetUrl, String targetRoadAddressName) {
        this.id = id;
        this.inputAddress = inputAddress;
        this.targetStoreName = targetStoreName;
        this.targetAddress = targetAddress;
        this.targetPhone = targetPhone;
        this.targetUrl = targetUrl;
        this.targetRoadAddressName = targetRoadAddressName;
    }

    public static SpecificDirectionResponse fromDto(DirectionDto dto) {
        return new SpecificDirectionResponse(
                dto.getId(),
                dto.getInputAddress(),
                dto.getTargetStoreName(),
                dto.getTargetAddress(),
                dto.getTargetPhone(),
                dto.getTargetUrl(),
                dto.getTargetRoadAddressName()
        );
    }
}
