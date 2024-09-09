package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.dto.CafeDto;
import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.request.CafeSearchRequest;
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

import static com.cafe.review.exception.ErrorCode.CAFE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final DirectionService directionService;
    private final KakaoAddressSearchService kakaoAddressSearchService;

    /**
     * 사용자가 입력한 주소를 기반으로 근처 카페 리스트를 반환한다.
     *
     * @param address(사용자가 입력한 주소)
     * @return 근처 카페 리스트
     */
    public List<DirectionDto> searchNearbyStoreList(String address) {

        //address(eg: 경기도 군포시)를 넣어 kakaoApiResponseDto [공식적인 주소명, 위도, 경도]를 받는다.
        var kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        //kakaoApiResponseDto 가 비었다면 빈 리스트를 반환한다.
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            return Collections.emptyList();
        }
        //검색한 결과중 가장 상단의 결과를 반환한다.
        var addressDocumentDto = kakaoApiResponseDto.getDocumentList().get(0);


        //formal한 주소로 검색한 결과호 DB에 저장된지 확인 후
        String modifiedAddress = addressDocumentDto.getAddressName();
        var searchedDirectionDtoList = directionService.findDirectionListByAddress(modifiedAddress);
        //없다면 접보를 검색하여 가져오고
        if (CollectionUtils.isEmpty(searchedDirectionDtoList)) {
            var directionDtoList = directionService.buildDirectionList(addressDocumentDto);

            // 만약 거리에 카페가 없는 경우 처리
            if (Objects.isNull(directionDtoList) || CollectionUtils.isEmpty(directionDtoList))
                return Collections.emptyList();

            return directionService.saveAll(directionDtoList);
        }
        //있다면 DB정보를 가져온다.
        return searchedDirectionDtoList;
    }

    @Transactional(readOnly = true)
    public List<CafeReviewDto> getList(CafeSearchRequest request) {
        return cafeRepository.findCafeWithReviewCountAndAverage(request);
    }

    @Transactional(readOnly = true)
    public CafeDto getCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new ReviewException(CAFE_NOT_FOUND, String.format("%s 아이디의 카페가 없습니다.", cafeId)));
        return CafeDto.fromEntity(cafe);
    }
}
