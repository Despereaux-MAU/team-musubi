package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
