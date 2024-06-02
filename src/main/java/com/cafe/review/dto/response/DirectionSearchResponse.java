package com.cafe.review.dto.response;

import com.cafe.review.dto.DirectionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class DirectionSearchResponse {
    private Long id;

    //목적지
    private String targetStoreName;
    private String targetPhone;
    private String targetUrl;
    private String targetRoadAddressName;

    private double distance;

    private DirectionSearchResponse(Long id, String targetStoreName, String targetPhone, String targetUrl, String targetRoadAddressName, double distance) {
        this.id = id;
        this.targetStoreName = targetStoreName;
        this.targetPhone = targetPhone;
        this.targetUrl = targetUrl;
        this.targetRoadAddressName = targetRoadAddressName;
        this.distance = distance;
    }

    public static DirectionSearchResponse fromDto(DirectionDto dto) {
        return new DirectionSearchResponse(
                dto.getId(),
                dto.getTargetStoreName(),
                dto.getTargetPhone(),
                dto.getTargetUrl(),
                dto.getTargetRoadAddressName(),
                dto.getDistance()
        );
    }
}
