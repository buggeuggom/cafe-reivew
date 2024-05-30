package com.cafe.review.service.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    /**
     * @param address String 주소를 받는다.
     * @return URI Kakao api 를 만들어 낸다.
     */
    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();//encode default utf-8
        log.info("[KakaoAddressSearchService buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    /**
     * @return 위도, 경도, 반경, 카테고리를 기반으로 카카오 api 카테로리 URI를 반환한다.
     */
    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {

        double meterRadius = radius * 1000;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code", category);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", meterRadius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoAddressSearchService buildUriByCategorySearch] uri: {} ", uri);

        return uri;
    }
}
