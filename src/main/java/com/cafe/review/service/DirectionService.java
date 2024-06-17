package com.cafe.review.service;

import com.cafe.review.domain.Direction;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.kakao.AddressDocumentDto;
import com.cafe.review.exception.ErrorCode;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.repository.DirectionRepository;
import com.cafe.review.service.kakao.KakaoCategorySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cafe.review.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 10;
    private static final double RADIUS_KM = 3.0; //반경 3 km

    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    @Transactional(readOnly = true)
    public DirectionDto findDirectionByDirectionId(Long directionId) {

        return DirectionDto.fromEntity(directionRepository.findById(directionId)
                .orElseThrow(()->new ReviewException(DIRECTION_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public List<DirectionDto> searchDirectionListByAddress(String address) {

         return directionRepository.findAllByInputAddress(address).stream()
                .map(DirectionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DirectionDto> saveAll(List<DirectionDto> directionList) {

        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        var entityList = directionRepository.saveAll(directionList.stream()
                .map(Direction::fromDto)
                .collect(Collectors.toList()));

        return entityList.stream()
                .map(DirectionDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    @Scheduled(cron = "0 0 0 * *") //매월 00시 00분 00초
    public void autoDelete() {
        directionRepository.deleteByCreatedAtLessThanEqual(LocalDateTime.now().minusMonths(1));
    }

    public List<DirectionDto> buildDirectionList(AddressDocumentDto inputDto) {

        if (Objects.isNull(inputDto)) return Collections.emptyList(); //TODO: 예외 처리 할지 고민 중

        return kakaoCategorySearchService.requestStoreCategorySearch(inputDto.getLatitude(), inputDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(cafeDocumentDto ->
                        DirectionDto.builder()
                                //inputDto
                                .inputAddress(inputDto.getAddressName())
                                .inputLatitude(inputDto.getLatitude())
                                .inputLongitude(inputDto.getLongitude())
                                //cafeDto
                                .targetStoreName(cafeDocumentDto.getPlaceName())
                                .targetAddress(cafeDocumentDto.getAddressName())
                                .targetPhone(cafeDocumentDto.getPhone())
                                .targetUrl(cafeDocumentDto.getPlaceUrl())
                                .targetRoadAddressName(cafeDocumentDto.getRoadAddressName())
                                //distance
                                .distance(cafeDocumentDto.getDistance() * 0.001) //km 단위
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

}
