package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import com.musubi.teammusubi.domain.seller.service.SellerDeliveryService;
import com.musubi.teammusubi.domain.seller.service.SellerStoreService;
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

    // 가게별 주문 조회
    // 최신 업데이트 순
    @GetMapping("/stores/{storeId}/deliveries")
    public ResponseEntity<List<DeliveryResponse>> retrieveDeliveryByStoreId(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId
    ) {
        MemberRoleEnum memberRole = memberDetails.getMember().getRole();
        if(!memberRole.equals(MemberRoleEnum.OWNER)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        List<DeliveryResponse> responses = sellerDeliveryService.retrieveDelivery(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }




}
