package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.*;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.domain.customer.repository.DeliveryMenuRepository;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @InjectMocks
    DeliveryService deliveryService;

    @Mock
    DeliveryRepository deliveryRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    DeliveryMenuRepository deliveryMenuRepository;

    @Mock
    MenuRepository menuRepository;

    private Store store;
    private Member member;
    private Delivery delivery;
    private Menu menu;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setRole(MemberRoleEnum.USER);

        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setMemberId(member.getId());
        delivery.setDetails("Test Details");

        menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Menu");
        menu.setStatus(MenuStatus.FOR_SALE);
        menu.setPrice(10000);

        store = new Store();
        store.setId(1L);
        store.setName("Test Store");
        store.getMenus().add(menu);
    }

    @Test
    @DisplayName("[고객] 주문 전체 조회")
    void readAllDeliveries() {
        // Given
        List<Delivery> deliveryList = new ArrayList<>();
        deliveryList.add(delivery);
        given(deliveryRepository.findByMemberId(1L)).willReturn(deliveryList);

        // When
        List<Delivery> deliveries = deliveryService.readAllDeliveries(1L);

        // Then
        assertNotNull(deliveries);
        assertEquals("Test Details",deliveries.get(0).getDetails());
    }

    @Test
    @DisplayName("[고객] 주문 상세 조회")
    void readDelivery() {
        // Given
        given(deliveryRepository.findByIdSafeWithDeliveryMenus(1L)).willReturn(delivery);

        // When
        Delivery delivery = deliveryService.readDelivery(1L,1L);

        // Then
        assertNotNull(delivery);
        assertEquals("Test Details",delivery.getDetails());
    }

    @Test
    @DisplayName("[고객] 주문 생성")
    void orderDelivery() {
        // Given
        Map<Long, Integer> orderMap = new HashMap<>();
        orderMap.put(1L, 1);
        given(deliveryRepository.save(any())).willReturn(delivery);
        given(storeRepository.findByIdSafe(any(Long.class))).willReturn(store);
        given(menuRepository.findAllById(any())).willReturn(List.of(menu));
        given(deliveryMenuRepository.saveAll(any())).willReturn(null);

        // When
        Delivery result = deliveryService.orderDelivery("Test Details", 1L, 1L, orderMap);

        // Then
        assertNotNull(result);
        assertEquals("Test Details",result.getDetails());
    }
}