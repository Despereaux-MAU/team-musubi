package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerStoreService {

    private final SellerStoreRepository sellerStoreRepository;

    public StoreResponse registerStore(Member loginedMember, StoreCreateRequest createRequest) {
        if (!(loginedMember.getRole().equals(MemberRoleEnum.OWNER))) {
            throw new IllegalArgumentException("사장님이 아닙니다");
        }

        validateStoreHours(createRequest.getOpenTime(),createRequest.getCloseTime());

        if (!(createRequest.getMinPrice() >= 0)) {
            throw new IllegalArgumentException("최소주문금액이 0원 이상이여야합니다.");
        }

        int storeCount = sellerStoreRepository.countByMemberIdAndStatusNot(loginedMember.getId(), StoreStatus.CLOSE);
        if (storeCount >= 3) {
            throw new IllegalArgumentException("가게는 최대 3개까지만 운영할 수 없습니다");
        }
        Store store = new Store(createRequest, loginedMember.getId());
        sellerStoreRepository.save(store);
        return new StoreResponse(store);
    }

    public List<StoreResponse> getAllStores(Long loginedMemberId) {
        List<Store> storeList = sellerStoreRepository.findAllByMemberIdAndStatusNot(loginedMemberId, StoreStatus.CLOSE);
        return storeList.stream().map(StoreResponse::new).toList();
    }

    public StoreResponse getStore(Long loginedMemberId, Long storeId) {
        Store store = sellerStoreRepository.findByIdAndMemberIdAndStatusNot(storeId, loginedMemberId, StoreStatus.CLOSE);
        if (store == null) {
            throw new IllegalArgumentException("가게가 존재하지 않거나, 폐업상태입니다.");
        }
        return new StoreResponse(store);
    }

    public StoreResponse updateStore(Long loginedMemberId, Long storeId, @Valid StoreUpdateRequest updateRequest) {
        Store store = sellerStoreRepository.findByIdAndMemberIdAndStatusNot(storeId, loginedMemberId, StoreStatus.CLOSE);
        if (store == null) {
            throw new IllegalArgumentException("가게가 존재하지 않거나, 폐업상태라서 수정할 수 없습니다.");
        }
        store.update(updateRequest);
        sellerStoreRepository.save(store);
        return new StoreResponse(store);
    }

    private void validateStoreHours(LocalTime openTime, LocalTime closeTime) {
        if (closeTime.isBefore(openTime)) {
            closeTime = closeTime.plusHours(24);
        }

        if (openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("오픈시간이 마감 시간보다 늦을 수 없습니다.");
        }
    }
}
