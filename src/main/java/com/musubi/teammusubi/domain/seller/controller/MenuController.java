package com.musubi.teammusubi.domain.seller.controller;

import com.musubi.teammusubi.common.Security.MemberDetailsImpl;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.domain.seller.service.MenuService;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class MenuController {
    private final MenuService menuService;

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
        MenuResponse response = menuService.createMenu(memberId, storeId, requestDto);
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
        MenuResponse response = menuService.modifyMenu(memberId, storeId, menuId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
