package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse registerStore(Member loginedMember, StoreCreateRequest createRequest) {
        if (!(loginedMember.getRole() == MemberRoleEnum.OWNER)) {
            throw new IllegalArgumentException("사장님이 아닙니다");
        }

        validateStoreHours(createRequest.getOpenTime(),createRequest.getCloseTime());

        Store store = new Store(createRequest, loginedMember.getId());
        storeRepository.save(store);
        return new StoreResponse(store);
    }

    private void validateStoreHours(LocalTime openTime, LocalTime closeTime) {
        if (openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("오픈시간이 마감 시간보다 늦을 수 없습니다.");
        }
    }
}
