package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.repository.MenuRepository;
import com.musubi.teammusubi.domain.seller.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MemberRepository memberRepository;
//    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public MenuResponse createMenu(Long memberId, Long storeId, MenuRequest requestDto) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

//        Menu savedMenu = menuRepository.save(Menu.of(requestDto, store));
        Menu savedMenu = menuRepository.save(Menu.of(requestDto, storeId));

        return MenuResponse.from(savedMenu);
    }
}
