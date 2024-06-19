package com.cafe.review.repository.cafe;

import com.cafe.review.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long>, CafeRepositoryCustom {

    List<Cafe> findAllByStoreNameAndAddressOrderByCreatedAtDesc(String storeName, String address);
}