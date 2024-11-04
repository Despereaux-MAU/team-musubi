package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.domain.customer.dto.StoreResponse;
import com.musubi.teammusubi.domain.customer.service.StoreService;
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
public class StoreController {

    private final StoreService storeService;

    // 특정 가게의 메뉴 전체 조회
    @GetMapping("/{storeId}/menus")
    public ResponseEntity<StoreResponse> findMenusByStoreId(@PathVariable Long storeId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.findMenusByStoreId(storeId));

    }
}
