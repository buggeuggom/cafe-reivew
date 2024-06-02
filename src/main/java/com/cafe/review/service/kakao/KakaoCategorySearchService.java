package com.cafe.review.service.kakao;

import com.cafe.review.dto.kakao.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;

    private static final String CAFE_CATEGORY = "CE7";

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public CategoryResponseDto requestStoreCategorySearch(double latitude, double longitude, double radius) {

        var uri = kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, CAFE_CATEGORY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        var httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, GET, httpEntity, CategoryResponseDto.class).getBody();
    }
}

