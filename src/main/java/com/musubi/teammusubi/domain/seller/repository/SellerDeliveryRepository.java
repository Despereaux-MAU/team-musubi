package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerDeliveryRepository extends JpaRepository<Delivery, Long> {
//    List<Delivery> findByStoreIdAndStatusOrderByCreatedAtDesc(Long storeId, DeliveryStatus status);
    Page<Delivery> findByStoreIdAndStatus(Long storeId, DeliveryStatus deliveryStatus, Pageable pageable);
}