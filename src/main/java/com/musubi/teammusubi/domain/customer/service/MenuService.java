package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.domain.customer.dto.MenuResponse;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public MenuResponse findByMenuId(Long storeId, Long menuId) {

        storeRepository.findById(storeId).orElseThrow(() ->
                new NullPointerException("가게가 존재하지 않습니다."));

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new NullPointerException("메뉴가 존재하지 않습니다."));

        return MenuResponse.of(menu);

    }
}
