package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByIdSafe(long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("(Dummy Exception) Id 찾을 수 없음"));
    }

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.menus WHERE s.id = :storeId")
    Optional<Store> findByIdWithMenus(@Param("storeId") long id);

    default Store findByIdSafeWithMenus(long id) {
        return findByIdWithMenus(id).orElseThrow(() -> new RuntimeException("(Dummy Exception) Id 찾을 수 없음"));
    }

    @Query("""
        SELECT s FROM Store s \s
        WHERE \s
            (:search IS NULL OR s.name LIKE %:search%) AND \s
            (:category IS NULL OR s.category LIKE :category) AND \s
            (:now IS NULL OR s.openTime <= :now) AND \s
            (:now IS NULL OR s.closeTime >= :now) AND \s
            (s.status <> 'CLOSE') AND \s
            (:includeTemp = true OR s.status = 'OPEN')
   \s""")
    Page<Store> findAllByParams(
            @Param("category") Category category,
            @Param("search") String search,
            @Param("now") LocalTime now,
            @Param("includeTemp") Boolean includeTemp,
            Pageable pageable);
}
