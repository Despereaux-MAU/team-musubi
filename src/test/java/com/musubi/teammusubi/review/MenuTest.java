package com.musubi.teammusubi.review;

import com.musubi.teammusubi.domain.customer.dto.MenuResponse;
import com.musubi.teammusubi.domain.customer.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MenuTest {

    private final MenuService menuService;

    @Autowired
    public MenuTest(MenuService menuService) {
        this.menuService = menuService;
    }

    @Test
    @DisplayName("특정 가게 메뉴 상세 조회")
    void test1() {

        //Given
        Long storeId = 1L;
        Long menuId = 1L;

        //When
        MenuResponse response = menuService.findByMenuId(storeId, menuId);

        //Then
        assertEquals(menuId, response.getId());


    }
}
