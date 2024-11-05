package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.Security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import com.musubi.teammusubi.domain.seller.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponse> registerStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @Valid @RequestBody StoreCreateRequest createRequest) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = storeService.registerStore(loginedMember, createRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeResponse);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponse>> getAllStores(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        Member loginedMember = memberDetails.getMember();

        List<StoreResponse> storeResponse = storeService.getAllStores(loginedMember.getId());
        return ResponseEntity.ok(storeResponse);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> getStoreByStoreId(@AuthenticationPrincipal MemberDetailsImpl memberDetails , @PathVariable Long storeId) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = storeService.getStore(loginedMember.getId(), storeId);
        return ResponseEntity.ok(storeResponse);
    }

    @PutMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long storeId, @Valid @RequestBody StoreUpdateRequest updateRequest) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = storeService.updateStore(loginedMember.getId(), storeId, updateRequest);
        return ResponseEntity.ok(storeResponse);
    }




}
