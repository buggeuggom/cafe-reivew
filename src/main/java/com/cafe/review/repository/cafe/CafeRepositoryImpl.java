package com.cafe.review.repository.cafe;


import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.QReview;
import com.cafe.review.dto.CafeReviewDto;
import com.cafe.review.dto.request.CafeSearchRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.cafe.review.domain.QCafe.*;
import static com.cafe.review.domain.QReview.*;

@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<CafeReviewDto> findCafeWithReviewCountAndAverage(CafeSearchRequest request) {

        return jpaQueryFactory
                .select(Projections.constructor(CafeReviewDto.class,
                        cafe.id,
                        cafe.storeName,
                        cafe.address,
                        (review.ambienceRating.avg()
                                .add(review.tasteRating.avg())
                                .add(review.serviceRating.avg())
                                ).divide(3).as("averageScore"),
                        review.count()))
                .from(review)
                .leftJoin(review).on(cafe.id.eq(review.cafe.id))
                .where(cafe.roadAddressName.contains(request.getRoadAddress()).and(
                        cafe.storeName.contains(request.getStoreName())))
                .orderBy(cafe.storeName.desc())
                .limit(request.getSize())
                .offset(request.getOffset())
                .groupBy(cafe.id, cafe.storeName)
                .fetch();
    }
}
