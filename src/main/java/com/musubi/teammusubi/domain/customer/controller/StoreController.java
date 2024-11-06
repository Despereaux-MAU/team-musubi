package com.musubi.teammusubi.domain.customer.controller;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.dto.StoreResponse;
import com.musubi.teammusubi.domain.customer.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<Object> readStores(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocalTime time
    ) {
        Optional<String> optCategory = Optional.ofNullable(category);
        Optional<String> optSearch = Optional.ofNullable(search);

        List<Store> stores = storeService.readAllStores(page, size, optCategory, optSearch, time);
        List<StoreResponse> res = stores.stream().map(StoreResponse::of).toList();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readStore(@PathVariable long id) {
        Store store = storeService.readStore(id);
        return ResponseEntity.ok(StoreResponse.allOf(store));
    }
}
