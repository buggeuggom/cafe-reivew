package com.cafe.review.repository;

import com.cafe.review.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Optional<Cafe> findByStoreNameAndAddress(String storeName, String address);
}
