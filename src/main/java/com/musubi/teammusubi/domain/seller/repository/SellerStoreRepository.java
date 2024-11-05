package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerStoreRepository extends JpaRepository<Store, Long> {
    Integer countByMemberId(Long id);

    List<Store> findAllByMemberId(Long loginedMemberId);

    Store findByIdAndMemberId(Long id, Long loginedMemberId);
}
