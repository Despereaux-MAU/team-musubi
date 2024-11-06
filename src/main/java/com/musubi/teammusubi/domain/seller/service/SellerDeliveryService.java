package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
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
    private final MemberRepository memberRepository;
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

    public DeliveryResponse changeDeliveryStatus(Long memberId, Long storeId, Long deliveryId, DeliveryStatus status) {
        // todo - filter에서 확인했으면, 삭제하기!
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if(!storeId.equals(delivery.getStore().getId())) {
            throw new IllegalArgumentException("해당 가게의 주문이 아닙니다.");
        }

        delivery.changeStatus(status);

        return DeliveryResponse.from(delivery);
    }
}
