package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.CafeDto;
import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.request.CafeSearchRequest;
import com.cafe.review.exception.ErrorCode;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.repository.cafe.CafeRepository;
import com.cafe.review.service.kakao.KakaoAddressSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final DirectionService directionService;
    private final KakaoAddressSearchService kakaoAddressSearchService;

    public List<DirectionDto> searchNearbyStoreList(String address) {

        var kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            return Collections.emptyList();
        }
        var addressDocumentDto = kakaoApiResponseDto.getDocumentList().get(0);

        String modifiedAddress = addressDocumentDto.getAddressName();
        var searchedDirectionDtoList = directionService.searchDirectionListByAddress(modifiedAddress);

        if (CollectionUtils.isEmpty(searchedDirectionDtoList)) {
            var directionDtoList = directionService.buildDirectionList(addressDocumentDto);

            return directionService.saveAll(directionDtoList);
        }

        return searchedDirectionDtoList;
    }

    @Transactional(readOnly = true)
    public List<CafeReviewDto> getList(CafeSearchRequest request) {
        return cafeRepository.findCafeWithReviewCountAndAverage(request);
    }

    @Transactional(readOnly = true)
    public CafeDto getCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new ReviewException(ErrorCode.CAFE_NOT_FOUND));
        return  CafeDto.fromEntity(cafe);
    }
}
