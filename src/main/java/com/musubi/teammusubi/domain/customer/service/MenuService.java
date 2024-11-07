package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
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
                new ResponseException(ExceptionType.STORE_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new ResponseException(ExceptionType.MENU_NOT_FOUND));

        return MenuResponse.of(menu);

    }
}
