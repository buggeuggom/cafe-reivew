package com.cafe.review.service.kakao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("비즈니스 로직 - KaKaoUriBuilderService")
@SpringBootTest
class KaKaoUriBuilderServiceTest {

    @Autowired
    private KakaoUriBuilderService kaKaoUriBuilderService;


    @Test
    @DisplayName("[requestAddressSearch][success]")
    void if_address_given_success() {
        //given
        String address = "경기도 군포시";

        //when
        URI uri = kaKaoUriBuilderService.buildUriByAddressSearch(address);
        String decoded = URLDecoder.decode(uri.toString(), UTF_8);

        //then
        assertEquals(decoded, "https://dapi.kakao.com/v2/local/search/address.json?query=경기도 군포시");
    }

    @Test
    @DisplayName("[requestAddressSearch][fail]")
    void if_wrong_address_given_fail() {
        //given
        String address = "경기도 안양시";

        //when
        URI uri = kaKaoUriBuilderService.buildUriByAddressSearch(address);
        String decoded = URLDecoder.decode(uri.toString(), UTF_8);

        //then
        assertNotEquals(decoded, "https://dapi.kakao.com/v2/local/search/address.json?query=경기도 군포시");
    }

    @Test
    @DisplayName("[buildUriByCategorySearch][success]")
    void if_parameter_given_success() {
        //given
        double latitude = 36.0;
        double longitude = 11.1;
        double radius = 10d;
        String category = "CE7";

        //when
        URI uri = kaKaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, category);
        String decoded = URLDecoder.decode(uri.toString(), UTF_8);
        //then
        assertEquals(decoded, "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=11.1&y=36.0&radius=10000.0&sort=distance");
    }

    @Test
    @DisplayName("[buildUriByCategorySearch][fail]")
    void if_parameter_given_fail() {
        //given
        double latitude = 36.2;
        double longitude = 11.3;
        double radius = 11;
        String category = "CE7";

        //when
        URI uri = kaKaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, category);
        String decoded = URLDecoder.decode(uri.toString(), UTF_8);
        //then
        assertNotEquals(decoded, "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=11.1&y=36.0&radius=10000.0&sort=distance");
    }
}
