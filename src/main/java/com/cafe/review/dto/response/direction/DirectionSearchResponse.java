package com.cafe.review.dto.response.direction;

import com.cafe.review.dto.DirectionDto;
import lombok.Getter;

@Getter
public class DirectionSearchResponse {
    private Long id;

    //목적지
    private String targetStoreName;
    private String targetPhone;
    private String targetRoadAddressName;

    private double distance;

    private DirectionSearchResponse(Long id, String targetStoreName, String targetPhone, String targetRoadAddressName, double distance) {
        this.id = id;
        this.targetStoreName = targetStoreName;
        this.targetPhone = targetPhone;
        this.targetRoadAddressName = targetRoadAddressName;
        this.distance = distance;
    }

    public static DirectionSearchResponse fromDto(DirectionDto dto) {
        return new DirectionSearchResponse(
                dto.getId(),
                dto.getTargetStoreName(),
                dto.getTargetPhone(),
                dto.getTargetRoadAddressName(),
                (double) Math.round(dto.getDistance() * 100) / 100
        );
    }
}
