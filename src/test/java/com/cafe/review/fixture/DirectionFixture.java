package com.cafe.review.fixture;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Direction;

public class DirectionFixture {

    public static Direction get(int i) {
        return Direction.builder()
                .inputAddress("inputAddress " + i)
                .inputLatitude(10.0 + i * 0.101)
                .inputLongitude(20.0 + i * 0.102)
                .targetStoreName("target store name " + i)
                .targetAddress("target address")
                .targetPhone("000-0000-0000")
                .targetUrl("https://test")
                .targetRoadAddressName("target road address name")
                .distance(i)
                .build();
    }

    public static Direction get(int i, String address) {
        return Direction.builder()
                .inputAddress(address)
                .inputLatitude(10.0 + i * 0.101)
                .inputLongitude(20.0 + i * 0.102)
                .targetStoreName("target store name " + i)
                .targetAddress(address)
                .targetPhone("000-0000-0000")
                .targetUrl("https://test")
                .targetRoadAddressName("target road address name")
                .distance(10.0 + (double) i / 10)
                .build();
    }
}
