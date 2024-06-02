package com.cafe.review.domain;

import com.cafe.review.dto.DirectionDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "IDX_INPUT_ADDRESS", columnList = "input_address")
        }
)
@NoArgsConstructor(access = PROTECTED)
public class Direction extends AuditingFields{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = IDENTITY)
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
    public Direction(Long id, String inputAddress, double inputLatitude, double inputLongitude, String targetStoreName, String targetAddress, String targetPhone, String targetUrl, String targetRoadAddressName, double distance) {
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

    public static Direction fromDto(DirectionDto dto) {
        return Direction.builder()
                .inputAddress(dto.getInputAddress())
                .inputLatitude(dto.getInputLatitude())
                .inputLongitude(dto.getInputLongitude())
                .targetStoreName(dto.getTargetStoreName())
                .targetAddress(dto.getTargetAddress())
                .targetPhone(dto.getTargetPhone())
                .targetUrl(dto.getTargetUrl())
                .targetRoadAddressName(dto.getTargetRoadAddressName())
                .distance(dto.getDistance())
                .build();
    }
}
