package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerStoreRepository extends JpaRepository<Store, Long> {
    Integer countByMemberIdAndStatusNot(Long loginedMemberId, StoreStatus storeStatus);

    List<Store> findAllByMemberIdAndStatusNot(Long loginedMemberId, StoreStatus storeStatus);

    Store findByIdAndMemberIdAndStatusNot(Long storeId, Long loginedMemberId, StoreStatus storeStatus);
}
