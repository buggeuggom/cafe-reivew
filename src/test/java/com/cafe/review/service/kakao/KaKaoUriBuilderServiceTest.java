package com.cafe.review.service.kakao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
