package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.Security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.service.DeliveryService;
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
    private final DeliveryService deliveryService;

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


    // 가게별 주문 조회
    // 최신 업데이트 순
    @GetMapping("/stores/{storeId}/orders")
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

        List<DeliveryResponse> responses = deliveryService.retrieveDelivery(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }
}
