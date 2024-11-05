package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.dto.StoreResponse;
import com.musubi.teammusubi.domain.customer.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    // 특정 가게의 메뉴 전체 조회
    @GetMapping("/{storeId}/menus")
    public ResponseEntity<StoreResponse> findMenusByStoreId(@PathVariable Long storeId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeService.findMenusByStoreId(storeId));

    }

    @GetMapping
    public ResponseEntity<Object> readStores(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search
    ) {
        Optional<String> optCategory = Optional.ofNullable(category);
        Optional<String> optSearch = Optional.ofNullable(search);

        List<Store> stores = storeService.readAllStores(page, size, optCategory, optSearch);
        List<StoreResponse> res = stores.stream().map(StoreResponse::new).toList();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readStore(@PathVariable long id) {
        Store store = storeService.readStore(id);
        return ResponseEntity.ok(new StoreResponse(store));
    }
}
