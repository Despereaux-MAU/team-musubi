package com.musubi.teammusubi.domain.seller.repository;

import com.musubi.teammusubi.common.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerMenuRepository extends JpaRepository<Menu, Long> {
}
