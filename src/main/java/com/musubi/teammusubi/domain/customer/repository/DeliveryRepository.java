package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // Review 에서 사용하는 메소드
    @Query("SELECT d FROM Delivery d WHERE d.id = ?1 AND d.status = ?2")
    Optional<Delivery> findByIdAndStatus(Long id, DeliveryStatus status);
}
