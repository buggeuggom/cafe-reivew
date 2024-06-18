package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.DirectionDto;
import com.cafe.review.dto.ReviewDto;
import com.cafe.review.dto.request.review.DeleteReviewRequest;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.dto.request.review.PutReviewRequest;
import com.cafe.review.exception.ReviewException;
import com.cafe.review.repository.cafe.CafeRepository;
import com.cafe.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cafe.review.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DirectionService directionService;

    @Transactional(readOnly = true)
    public List<ReviewDto> getList(Long cafeId) {
        var cafeEntity = getCafeEntityOrCafeNotFoundException(cafeId);

        return reviewRepository.findAllByCafe(cafeEntity).stream()
                .map(ReviewDto::fromEntity)
                .toList();
    }

    @Transactional
    public ReviewDto post(PostReviewRequest request) {
        DirectionDto directionDto = directionService.findDirectionByDirectionId(request.getDirectionId());
        List<Cafe> cafeList = cafeRepository.findAllByStoreNameAndAddressOrderByCreatedAtDesc(
                        directionDto.getTargetStoreName(),
                        directionDto.getTargetAddress());
        Cafe cafeEntity = (cafeList.isEmpty()) ? cafeRepository.save(Cafe.fromDirectionDto(directionDto)) : cafeList.get(0);

        var reviewEntity = Review.builder()
                .writerId(request.getWriterId())
                .password(passwordEncoder.encode(request.getPassword()))
                .title(request.getTitle())
                .comment(request.getComment())
                .cafe(cafeEntity)
                .tasteRating(request.getTasteRating())
                .ambienceRating(request.getAmbienceRating())
                .serviceRating(request.getServiceRating())
                .build();

        return ReviewDto.fromEntity(reviewRepository.save(reviewEntity));
    }


    public void edit(Long reviewId, PutReviewRequest request) {

        var reviewEntity = getReviewOrCafeNotFoundException(reviewId);

        verifyPassword(request.getPassword(), reviewEntity.getPassword());

        reviewEntity.edit(request);
    }

    public void delete(Long reviewId, DeleteReviewRequest request) {

        var reviewEntity = getReviewOrCafeNotFoundException(reviewId);

        verifyPassword(request.getPassword(), reviewEntity.getPassword());

        reviewRepository.delete(reviewEntity);
    }

    private Cafe getCafeEntityOrCafeNotFoundException(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() ->
                new ReviewException(CAFE_NOT_FOUND, String.format("%s 아이디의 카페가 없습니다.", cafeId)));
    }

    private Review getReviewOrCafeNotFoundException(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewException(REVIEW_NOT_FOUND, String.format("%s 아이디의 리뷰가 없습니다.", reviewId)));
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ReviewException(INVALID_PASSWORD);
        }
    }


}
