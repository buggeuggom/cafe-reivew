package com.cafe.review.controller;

import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.response.DirectionSearchResponse;
import com.cafe.review.service.CafeService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cafes")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @GetMapping
    public List<DirectionSearchResponse> search(@RequestParam @NotBlank String address) {
        List<DirectionDto> directionDtoList = cafeService.buildNearbyStoreList(address);

        return directionDtoList.stream().map(DirectionSearchResponse::fromDto)
                .collect(Collectors.toList());
    }
}
