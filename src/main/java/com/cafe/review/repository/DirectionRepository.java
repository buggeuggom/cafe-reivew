package com.cafe.review.repository;

import com.cafe.review.domain.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DirectionRepository extends JpaRepository<Direction, Long> {

    List<Direction> findAllByInputAddress(String inputAddress);

    void deleteByCreatedAtLessThanEqual(LocalDateTime localDateTime);
}
