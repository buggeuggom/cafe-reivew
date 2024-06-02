package com.cafe.review.dto;

import com.cafe.review.domain.Direction;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DirectionDto {

    private Long id;

    //현위치
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    //목적지
    private String targetStoreName;
    private String targetAddress;
    private String targetPhone;
    private String targetUrl;
    private String targetRoadAddressName;

    private double distance;


    @Builder
    private DirectionDto(Long id, String inputAddress, double inputLatitude, double inputLongitude, String targetStoreName, String targetAddress, String targetPhone, String targetUrl, String targetRoadAddressName, double distance) {
        this.id = id;
        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetStoreName = targetStoreName;
        this.targetAddress = targetAddress;
        this.targetPhone = targetPhone;
        this.targetUrl = targetUrl;
        this.targetRoadAddressName = targetRoadAddressName;
        this.distance = distance;
    }


    public static DirectionDto fromEntity(Direction entity) {
        return DirectionDto.builder()
                .id(entity.getId())
                .inputAddress(entity.getInputAddress())
                .inputLatitude(entity.getInputLatitude())
                .inputLongitude(entity.getInputLongitude())
                .targetStoreName(entity.getTargetStoreName())
                .targetAddress(entity.getTargetAddress())
                .targetPhone(entity.getTargetPhone())
                .targetUrl(entity.getTargetUrl())
                .targetRoadAddressName(entity.getTargetRoadAddressName())
                .distance(entity.getDistance())
                .build();
    }
}
