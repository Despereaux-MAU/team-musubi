package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.Security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreResponse> registerStore(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody StoreCreateRequest createRequest) {
        Member loginedMember = memberDetails.getMember();
        StoreResponse storeResponse = storeService.registerStore(loginedMember, createRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(storeResponse);
    }


}
