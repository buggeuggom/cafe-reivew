package com.cafe.review.repository;

import com.cafe.review.domain.Cafe;
import com.cafe.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByCafe(Cafe cafe);
}
