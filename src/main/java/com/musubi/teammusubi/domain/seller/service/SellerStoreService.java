package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.customer.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    public StoreResponse registerStore(Member loginedMember, StoreCreateRequest createRequest) {
        validateStoreHours(createRequest.getOpenTime(),createRequest.getCloseTime());
        // 어떤값인지 상수변수로 네이밍으로 입력해주는게 직관적이다.
        if (!(createRequest.getMinPrice() >= 0)) {
            throw new ResponseException(ExceptionType.MINIMUM_ORDER_AMOUNT);
        }

        int storeCount = sellerStoreRepository.countByMemberIdAndStatusNot(loginedMember.getId(), StoreStatus.CLOSE);
        if (storeCount >= 3) {
            throw new ResponseException(ExceptionType.EXCEEDS_MAXIMUM_STORE_LIMIT);
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
        Store store = sellerStoreRepository.findByIdAndStatusNot(storeId, StoreStatus.CLOSE);
        if (!(loginedMemberId.equals(store.getMemberId()))) {
            throw new ResponseException(ExceptionType.NOT_OWNER_OF_STORE);
        }

        if (store == null) {
            throw new ResponseException(ExceptionType.STORE_NOT_FOUND_OR_STORE_CLOSE);
        }
        return new StoreResponse(store);
    }

    public StoreResponse updateStore(Long loginedMemberId, Long storeId, @Valid StoreUpdateRequest updateRequest) {
        Store store = sellerStoreRepository.findByIdAndMemberIdAndStatusNot(storeId, loginedMemberId, StoreStatus.CLOSE);
        if (store == null) {
            throw new ResponseException(ExceptionType.STORE_NOT_FOUND_OR_STORE_CLOSE);
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
            throw new ResponseException(ExceptionType.OPEN_TIME_AFTER_CLOSE_TIME);
        }
    }

    public StoreResponse closeStore(Long loginedMemberId, Long storeId) {
        Store store = sellerStoreRepository.findByIdAndMemberId(storeId, loginedMemberId);
        store.closeStore();
        sellerStoreRepository.save(store);
        reviewRepository.deleteByStoreId(storeId).orElseThrow(() ->
                new ResponseException(ExceptionType.REVIEW_NOT_FOUND));
        return new StoreResponse(store);
    }
}
