package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.common.security.MemberDetailsImpl;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import com.musubi.teammusubi.domain.seller.service.SellerDeliveryService;
import com.musubi.teammusubi.domain.seller.service.SellerStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerStoreController {
    private final SellerStoreService sellerStoreService;
    private final SellerDeliveryService sellerDeliveryService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponse> registerStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @Valid @RequestBody StoreCreateRequest createRequest) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = sellerStoreService.registerStore(loginedMember, createRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeResponse);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponse>> getAllStores(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        Member loginedMember = memberDetails.getMember();

        List<StoreResponse> storeResponse = sellerStoreService.getAllStores(loginedMember.getId());
        return ResponseEntity.ok(storeResponse);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> getStoreByStoreId(@AuthenticationPrincipal MemberDetailsImpl memberDetails , @PathVariable Long storeId) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = sellerStoreService.getStore(loginedMember.getId(), storeId);
        return ResponseEntity.ok(storeResponse);
    }

    @PutMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long storeId, @Valid @RequestBody StoreUpdateRequest updateRequest) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = sellerStoreService.updateStore(loginedMember.getId(), storeId, updateRequest);
        return ResponseEntity.ok(storeResponse);
    }

    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponse> closeStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long storeId) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = sellerStoreService.closeStore(loginedMember.getId(), storeId);
        return ResponseEntity.ok(storeResponse);
    }

    // 가게별 주문 조회
    // 주문 상태별 조회 - default: 대기
    @GetMapping("/stores/{storeId}/deliveries")
    public ResponseEntity<Page<DeliveryResponse>> retrieveDeliveryByStoreIdAsPageSize(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId,
            @RequestParam(required = false, value = "status", defaultValue = "PENDING") DeliveryStatus deliveryStatus,
            @RequestParam(required = false, value = "page", defaultValue = "1") int page,
            @RequestParam(required = false, value = "size", defaultValue = "10") int size,
            @RequestParam(required = false, value = "orderBy", defaultValue = "createdAt") String criteria,
            @RequestParam(required = false, value = "sort", defaultValue = "DESC") String sort
    ) {
        Long memberId = memberDetails.getMember().getId();
        Page<DeliveryResponse> responses = sellerDeliveryService.retrieveDeliveryByStoreIdAsPageSize(
                memberId, storeId, deliveryStatus, page, size, criteria, sort);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }


    // 주문 상태 변경 - default: 대기
    @PutMapping("/stores/{storeId}/deliveries/{deliveryId}")
    public ResponseEntity<DeliveryResponse> chageDeliveryStatus(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId,
            @PathVariable Long deliveryId,
            @RequestParam(required = false, value = "status", defaultValue = "PENDING") DeliveryStatus deliveryStatus
    ) {
        Long memberId = memberDetails.getMember().getId();
        DeliveryResponse response = sellerDeliveryService.changeDeliveryStatus(
                memberId, storeId, deliveryId, deliveryStatus);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}