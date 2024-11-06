package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.security.MemberDetailsImpl;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.service.SellerMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerMenuController {

    private final SellerMenuService sellerMenuService;

    @PostMapping("/stores/{storeId}/menus")
    public ResponseEntity<MenuResponse> createMenu(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId,
            @RequestBody @Valid MenuRequest requestDto
    ) {
        MemberRoleEnum memberRole = memberDetails.getMember().getRole();
        if(!memberRole.equals(MemberRoleEnum.OWNER)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        Long memberId = memberDetails.getMember().getId();
        MenuResponse response = sellerMenuService.createMenu(memberId, storeId, requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> modifyMenu(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuRequest requestDto
    ) {
        MemberRoleEnum memberRole = memberDetails.getMember().getRole();
        if(!memberRole.equals(MemberRoleEnum.OWNER)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        Long memberId = memberDetails.getMember().getId();
        MenuResponse response = sellerMenuService.modifyMenu(memberId, storeId, menuId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponse> closeMenu(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails,
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        // todo - filter에서 확인했으니, 삭제하기!
//        MemberRoleEnum memberRole = memberDetails.getMember().getRole();
//        if(!memberRole.equals(MemberRoleEnum.OWNER)) {
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .build();
//        }

        Long memberId = memberDetails.getMember().getId();
        MenuResponse response = sellerMenuService.closeMenu(memberId, storeId, menuId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
