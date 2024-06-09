package com.cafe.review.service;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import com.cafe.review.dto.ReviewDto;
import com.cafe.review.dto.request.review.DeleteReviewRequest;
import com.cafe.review.dto.request.review.PostReviewRequest;
import com.cafe.review.dto.request.review.PutReviewRequest;
import com.cafe.review.repository.CafeRepository;
import com.cafe.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<ReviewDto> getList(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(RuntimeException::new);


        return reviewRepository.findAllByCafe(cafe).stream()
                .map(ReviewDto::fromEntity)
                .toList();
    }

    public ReviewDto post(Long cafeId, PostReviewRequest request) {

        var cafeEntity = cafeRepository.findById(cafeId).orElseThrow(RuntimeException::new);

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
        var reviewEntity = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        var rawPassword = request.getPassword();

        if (isWrongPassword(rawPassword, reviewEntity)) {
            throw new RuntimeException();
        }

        reviewEntity.edit(request);
    }

    public void delete(Long reviewId, DeleteReviewRequest request) {
        var reviewEntity = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        var rawPassword = request.getPassword();

        if (isWrongPassword(rawPassword, reviewEntity)) {
            throw new RuntimeException();
        }

        reviewRepository.delete(reviewEntity);
    }


    private boolean isWrongPassword(String rawPassword, Review reviewEntity) {
        return !passwordEncoder.matches(rawPassword, reviewEntity.getPassword());
    }
}
