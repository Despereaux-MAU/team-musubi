package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    default Delivery findByIdSafe(long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("(Dummy Exception)Id not found"));
    }
}
