package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerStoreRepository extends JpaRepository<Store, Long> {
    Integer countByMemberId(Long id);

    List<Store> findAllByMemberId(Long loginedMemberId);

    Store findByIdAndMemberId(Long id, Long loginedMemberId);

    Integer countByMemberIdAndStatusNot(Long loginedMemberId, StoreStatus storeStatus);
}
