package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.domain.customer.dto.MenuResponse;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    MenuRepository menuRepository;

    @Mock
    StoreRepository storeRepository;

    private Store store;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Menu");
        menu.setDescription("Test Description");
        menu.setStatus(MenuStatus.FOR_SALE);

        store = new Store();
        store.getMenus().add(menu);
    }

    @Test
    void findByMenuId() {
        // Given
        given(storeRepository.findById(1L)).willReturn(Optional.of(store));
        given(menuRepository.findById(1L)).willReturn(Optional.of(menu));

        // When
        MenuResponse result = menuService.findByMenuId(1L,1L);

        // Then
        assertNotNull(menu);
        assertEquals("Test Menu", result.getName());
    }
}