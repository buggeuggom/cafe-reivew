package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.dto.CafeDto;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.request.ReviewRequest;
import com.cafe.review.repository.CafeRepository;
import com.cafe.review.service.kakao.KakaoAddressSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final CafeRepository cafeRepository;

    public List<DirectionDto> searchNearbyStoreList(String address) {

        var kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            return Collections.emptyList(); //TODO: 예외 처리 고민 중
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

    public CafeDto register(Long directionId) {
        DirectionDto direction = directionService.findDirectionByDirectionId(directionId);

        Optional<Cafe> byStoreNameAndAddress = cafeRepository.findByStoreNameAndAddress(
                direction.getTargetStoreName(),
                direction.getTargetAddress()
        );

        if (byStoreNameAndAddress.isPresent()) {
            return CafeDto.fromEntity(byStoreNameAndAddress.get());
        }

        Cafe saved = cafeRepository.save(Cafe.fromDirectionDto(direction));

        return CafeDto.fromEntity(saved);
    }
}
