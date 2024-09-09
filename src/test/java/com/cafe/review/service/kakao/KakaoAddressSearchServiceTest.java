package com.cafe.review.service.kakao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - KakaoAddressSearchService")
class KakaoAddressSearchServiceTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @Test
    @DisplayName("[requestAddressSearch][success]")
    void if_address_given_success() {
        //given
        String address = "경기도 군포시";

        //when
        var result = kakaoAddressSearchService.requestAddressSearch(address);

        //then
        assertFalse(result.getDocumentList().isEmpty());
        assertTrue(result.getMetaDto().getTotalCount() > 0);
        assertNotNull(result.getDocumentList().get(0).getAddressName());
    }

    @Test
    @DisplayName("[requestAddressSearch][fail]")
    void if_address_given_fail() {
        //given
        String address = "경기도 군포시";

        //when
        var result = kakaoAddressSearchService.requestAddressSearch(address);

        //then
        assertFalse(result.getDocumentList().isEmpty());
        assertFalse(result.getMetaDto().getTotalCount().equals(0));
        assertNotEquals("address name", result.getDocumentList().get(0).getAddressName());
    }
}