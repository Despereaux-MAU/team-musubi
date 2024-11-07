package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStoreId(Long storeId, Pageable pageable);

    Page<Review> findByScore(Integer score, Pageable pageable);

    List<Review> findAllByStoreId(Long storeId);
}
