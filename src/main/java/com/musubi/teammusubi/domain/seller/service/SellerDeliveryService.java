package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerDeliveryRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerDeliveryService {
    private final SellerStoreRepository storeRepository;
    private final SellerDeliveryRepository deliveryRepository;

//    public List<DeliveryResponse> retrieveDelivery(Long storeId, DeliveryStatus deliveryStatus) {
//        storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
//
//        List<Delivery> deliveries = deliveryRepository.findByStoreIdAndStatusOrderByCreatedAtDesc(storeId, deliveryStatus);
//
//        return deliveries.stream().map(DeliveryResponse::from).toList();
//    }

    public Page<DeliveryResponse> retrieveDeliveryByStoreIdAsPageSize(
            Long storeId, DeliveryStatus deliveryStatus, int page, int size, String criteria, String sort
    ) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        Pageable pageable = sort.equals("DESC") ?
                PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria))
                : PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, criteria));

        Page<Delivery> deliveries = deliveryRepository.findByStoreIdAndStatus(storeId, deliveryStatus, pageable);
        return deliveries.map(DeliveryResponse::from);
    }
}
