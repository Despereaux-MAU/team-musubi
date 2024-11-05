package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    default Menu findByIdSafe(Long id) {
        // todo - overwrite custom exception
        return findById(id).orElseThrow(() -> new RuntimeException("(Dummy Exception)Menu not found"));
    }
}
