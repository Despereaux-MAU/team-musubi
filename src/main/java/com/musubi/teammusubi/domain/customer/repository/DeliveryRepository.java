package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // Review 에서 사용하는 메소드
    @Query("SELECT d FROM Delivery d WHERE d.id = ?1 AND d.status = ?2")
    Optional<Delivery> findByIdAndStatus(Long id, DeliveryStatus status);

    default Delivery findByIdSafe(long id) {
        return findById(id).orElseThrow(() -> new ResponseException(ExceptionType.DELIVERY_NOT_FOUND));
    }
    List<Delivery> findByMemberId(Long memberId);

    @Query("SELECT d FROM Delivery d LEFT JOIN FETCH d.deliveryMenu WHERE d.id = :deliveryId")
    Optional<Delivery> findByIdWithDeliveryMenus(@Param("deliveryId") long id);

    default Delivery findByIdSafeWithDeliveryMenus(long id) {
        return findByIdWithDeliveryMenus(id).orElseThrow(() -> new ResponseException(ExceptionType.DELIVERY_NOT_FOUND));
    }
}
