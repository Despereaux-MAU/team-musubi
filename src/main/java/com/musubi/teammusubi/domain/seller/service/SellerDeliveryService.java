package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerDeliveryRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerDeliveryService {
    private final SellerStoreRepository storeRepository;
    private final SellerDeliveryRepository deliveryRepository;

    public List<DeliveryResponse> retrieveDelivery(Long storeId, DeliveryStatus deliveryStatus) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        List<Delivery> deliveries = deliveryRepository.findByStoreIdAndStatusOrderByUpdatedAtDesc(storeId, deliveryStatus);

        return deliveries.stream().map(DeliveryResponse::from).toList();
    }
}
