package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerDeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStoreIdOrderByUpdatedAtDesc(Long storeId);
}