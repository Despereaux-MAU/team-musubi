package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.domain.customer.dto.MenuResponse;
import com.musubi.teammusubi.domain.customer.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> findByMenuId(@PathVariable Long storeId,
                                                     @PathVariable Long menuId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuService.findByMenuId(storeId, menuId));

    }
}
