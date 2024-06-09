package com.cafe.review.fixture;

import com.cafe.review.domain.Cafe;

public class CafeFixture {

    public static Cafe get(int i) {
        return Cafe.builder()
                .storeName("store name: " + i)
                .address("address: " + i)
                .phone("phone: " + i)
                .url("url: " + i)
                .roadAddressName("road address name: " + i)
                .build();
    }
}
