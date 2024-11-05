package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByIdSafe(long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("(Dummy Exception) Id 찾을 수 없음"));
    }

    @Query("""
        SELECT s FROM Store s \s
        WHERE \s
            (:search IS NULL OR s.name LIKE %:search%) \s
            AND \s
            (:category IS NULL OR s.category LIKE :category)
   \s""")
    Page<Store> findAllByParams(@Param("category") Category category, @Param("search") String search, Pageable pageable);
}
