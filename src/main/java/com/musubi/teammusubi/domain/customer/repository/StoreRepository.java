package com.musubi.teammusubi.domain.customer.repository;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByIdSafe(long id) {
        return findById(id).orElseThrow(() -> new ResponseException(ExceptionType.STORE_NOT_FOUND));
    }

    @Query("SELECT s FROM Store s LEFT JOIN FETCH s.menus m WHERE s.id = :storeId AND m.status = 'FOR_SALE'")
    Optional<Store> findByIdWithMenus(@Param("storeId") long id);

    default Store findByIdSafeWithMenus(long id) {
        return findByIdWithMenus(id).orElseThrow(() -> new ResponseException(ExceptionType.STORE_NOT_FOUND));
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
