package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerMenuRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.musubi.teammusubi.common.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class SellerMenuService {
    private final MemberRepository memberRepository;
    private final SellerStoreRepository sellerStoreRepository;
    private final SellerMenuRepository sellerMenuRepository;

    public MenuResponse createMenu(Long memberId, Long storeId, MenuRequest requestDto) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));
        Store store = sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(STORE_NOT_FOUND));

        if(!memberId.equals(store.getMemberId())) {
            throw new ResponseException(NOT_OWNER_OF_STORE);

        }
        Menu savedMenu = sellerMenuRepository.save(Menu.of(requestDto, store));

        return MenuResponse.from(savedMenu);
    }

    @Transactional
    public MenuResponse modifyMenu(Long memberId, Long storeId, Long menuId, MenuRequest requestDto) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));
        sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(STORE_NOT_FOUND));
        Menu menu = sellerMenuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(MENU_NOT_FOUND));

        if(!memberId.equals(menu.getStore().getMemberId())) {
            throw new ResponseException(NOT_OWNER_OF_STORE);
        }
        if(!storeId.equals(menu.getStore().getId())) {
            throw new ResponseException(NOT_A_MENU_OF_STORE);
        }

        menu.modify(requestDto.getName(), requestDto.getPrice(), requestDto.getDescription());
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse closeMenu(Long memberId, Long storeId, Long menuId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));
        sellerStoreRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(STORE_NOT_FOUND));
        Menu menu = sellerMenuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(MENU_NOT_FOUND));

        if(!memberId.equals(menu.getStore().getMemberId())) {
            throw new ResponseException(NOT_OWNER_OF_STORE);
        }
        if(!storeId.equals(menu.getStore().getId())) {
            throw new ResponseException(NOT_A_MENU_OF_STORE);
        }

        menu.close();
        return MenuResponse.from(menu);
    }
}
