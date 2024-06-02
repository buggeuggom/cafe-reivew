package com.cafe.review.service;

import com.cafe.review.dto.DirectionDto;
import com.cafe.review.service.kakao.KakaoAddressSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<DirectionDto> searchNearbyStoreList(String address) {

        var searchedDirectionDtoList = directionService.searchDirectionList(address);

        if (isEmptyList(searchedDirectionDtoList)) {
            return buildNearbyStoreList(address);
        }

        return searchedDirectionDtoList;
    }

    private boolean isEmptyList(List<DirectionDto> searchedDirectionDtoList) {

        return CollectionUtils.isEmpty(searchedDirectionDtoList);
    }

    private List<DirectionDto> buildNearbyStoreList(String address) {

        var kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            return Collections.emptyList(); //TODO: 예외 처리 고민 중
        }

        var addressDocumentDto = kakaoApiResponseDto.getDocumentList().get(0);

        var directionDtoList = directionService.buildDirectionList(addressDocumentDto);

        return directionService.saveAll(directionDtoList);

    }
}
