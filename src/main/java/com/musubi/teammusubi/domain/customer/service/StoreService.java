package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.dto.StoreResponse;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse findMenusByStoreId(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new NullPointerException("가게가 존재하지 않습니다."));

        return StoreResponse.of(store);
    }
}
