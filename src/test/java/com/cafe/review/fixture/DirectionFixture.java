package com.cafe.review.fixture;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Direction;

public class DirectionFixture {

    public static Direction get(int i) {
        return Direction.builder()
                .inputAddress("inputAddress " + i)
                .inputLatitude(i)
                .inputLongitude(i)
                .targetStoreName("target store name " + i)
                .targetAddress("target address")
                .targetPhone("000-0000-0000")
                .targetUrl("https://test")
                .targetRoadAddressName("target road address name")
                .distance(i)
                .build();
    }
}
