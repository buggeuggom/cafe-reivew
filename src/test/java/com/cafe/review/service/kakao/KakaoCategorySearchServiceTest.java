package com.cafe.review.service.kakao;

import com.cafe.review.dto.kakao.CategoryResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("비즈니스 로직 - KakaoCategorySearchService")
class KakaoCategorySearchServiceTest {

    @Autowired
    private KakaoCategorySearchService kakaoCategorySearchService;

    @Test
    @DisplayName("[requestStoreCategorySearch][success]")
    void test() {
        //given
        //"경기도 군포시" 위경도
        double latitude = 37.3616318289596d;
        double longitude = 126.935205932727d;
        double radius = 3.0d;

        //when
        var categoryResponseDto = kakaoCategorySearchService.requestStoreCategorySearch(latitude, longitude, radius);

        //then
        System.out.println(categoryResponseDto);
        assertFalse(categoryResponseDto.getDocumentList().isEmpty());
        assertTrue(categoryResponseDto.getMetaDto().getTotalCount() > 0);
        assertNotNull(categoryResponseDto.getDocumentList().get(0).getAddressName());

    }
}