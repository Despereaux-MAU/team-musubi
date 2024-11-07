package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.setCategory(Category.KOREAN);
        store.setStatus(StoreStatus.OPEN);
        store.setOpenTime(LocalTime.MIN);
        store.setCloseTime(LocalTime.MAX);
    }

    @Test
    @DisplayName("[고객] 가게 전체 조회")
    void readAllStores() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Store> storePage = new PageImpl<>(Collections.singletonList(store), pageable, 1);
        given(storeRepository.findAllByParams(eq(Category.KOREAN),eq(null),eq(LocalTime.NOON),any(),any())).willReturn(storePage);

        // When
        List<Store> result = storeService.readAllStores(1, 10, Optional.of("KOREAN"), Optional.empty(), LocalTime.NOON, false);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("[고객] 가게 상세 조회")
    void readStore() {
        // Given
        given(storeRepository.findByIdSafeWithMenus(1L)).willReturn(store);

        // When
        Store result = storeService.readStore(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Store", result.getName());
    }
}