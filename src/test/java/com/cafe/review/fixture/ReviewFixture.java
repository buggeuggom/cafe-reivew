package com.cafe.review.fixture;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class ReviewFixture {

    public static Review get(int i, Cafe cafe, PasswordEncoder passwordEncoder) {
        return Review.builder()
                .writerId("writer id: " + i)
                .password(passwordEncoder.encode("password: " + i))
                .title("title: " + i)
                .comment("comment: " + i)
                .cafe(cafe)
                .tasteRating(i % 5)
                .ambienceRating(i % 5)
                .serviceRating(i % 5)
                .build();
    }
}
