package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerMenuRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerMenuService {
    private final MemberRepository memberRepository;
    private final SellerStoreRepository sellerStoreRepository;
    private final SellerMenuRepository sellerMenuRepository;

    public MenuResponse createMenu(Long memberId, Long storeId, MenuRequest requestDto) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Store store = sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
        if(!memberId.equals(store.getMemberId())) {
            throw new IllegalArgumentException("해당 가게의 주인이 아닙니다.");
        }
        Menu savedMenu = sellerMenuRepository.save(Menu.of(requestDto, store));

        return MenuResponse.from(savedMenu);
    }

    @Transactional
    public MenuResponse modifyMenu(Long memberId, Long storeId, Long menuId, MenuRequest requestDto) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
        Menu menu = sellerMenuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        if(!memberId.equals(menu.getStore().getMemberId())) {
            throw new IllegalArgumentException("해당 가게의 주인이 아닙니다.");
        }
        if(!storeId.equals(menu.getStore().getId())) {
            throw new IllegalArgumentException("해당 가게의 메뉴가 아닙니다.");
        }

        menu.modify(requestDto.getName(), requestDto.getPrice(), requestDto.getDescription());
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse closeMenu(Long memberId, Long storeId, Long menuId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
        Menu menu = sellerMenuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        if(!memberId.equals(menu.getStore().getMemberId())) {
            throw new IllegalArgumentException("해당 가게의 주인이 아닙니다.");
        }
        if(!storeId.equals(menu.getStore().getId())) {
            throw new IllegalArgumentException("해당 가게의 메뉴가 아닙니다.");
        }

        menu.close();
        return MenuResponse.from(menu);
    }
}
