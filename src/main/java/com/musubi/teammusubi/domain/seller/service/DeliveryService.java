package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.seller.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final StoreRepository storeRepository;
    private final DeliveryRepository deliveryRepository;

    public List<DeliveryResponse> retrieveDelivery(Long storeId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        List<Delivery> deliveries = deliveryRepository.findByStoreIdOrderByUpdatedAtDesc(storeId);
        // 아무 주문이 없으면, 따로 처리해줄까?
        return deliveries.stream().map(DeliveryResponse::from).toList();
    }
}
