package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerDeliveryRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.musubi.teammusubi.common.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class SellerDeliveryService {
    private final MemberRepository memberRepository;
    private final SellerStoreRepository storeRepository;
    private final SellerDeliveryRepository deliveryRepository;

    public Page<DeliveryResponse> retrieveDeliveryByStoreIdAsPageSize(
            Long memberId, Long storeId, DeliveryStatus deliveryStatus, int page, int size, String criteria, String sort
    ) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(STORE_NOT_FOUND));

        if(!memberId.equals(store.getMemberId())) {
            throw new ResponseException(NOT_OWNER_OF_STORE);
        }

        Pageable pageable = sort.equals("DESC") ?
                PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria))
                : PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, criteria));

        Page<Delivery> deliveries = deliveryRepository.findByStoreIdAndStatus(storeId, deliveryStatus, pageable);
        return deliveries.map(DeliveryResponse::from);
    }

    @Transactional
    public DeliveryResponse changeDeliveryStatus(Long memberId, Long storeId, Long deliveryId, DeliveryStatus status) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(STORE_NOT_FOUND));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseException(DELIVERY_NOT_FOUND));

        if(!memberId.equals(store.getMemberId())) {
            throw new ResponseException(NOT_OWNER_OF_STORE);
        }
        if(!storeId.equals(delivery.getStoreId())) {
            throw new ResponseException(NOT_A_DELIVERY_OF_STORE);
        }

        delivery.changeStatus(status);

        return DeliveryResponse.from(delivery);
    }
}
