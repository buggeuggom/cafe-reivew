package com.cafe.review.controller;

import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.response.direction.DirectionSearchResponse;
import com.cafe.review.dto.response.direction.SpecificDirectionResponse;
import com.cafe.review.service.CafeService;
import com.cafe.review.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/directions")
@RequiredArgsConstructor
public class DirectionController {

    private final CafeService cafeService;
    private final DirectionService directionService;

    @GetMapping()
    public List<DirectionSearchResponse> search(String address) {
        List<DirectionDto> directionDtoList = cafeService.searchNearbyStoreList(address);

        return directionDtoList.stream()
                .map(DirectionSearchResponse::fromDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{directionId}")
    public SpecificDirectionResponse searchSpecific(@PathVariable Long directionId) {

        return SpecificDirectionResponse.fromDto(directionService.findDirectionByDirectionId(directionId));
    }
}

