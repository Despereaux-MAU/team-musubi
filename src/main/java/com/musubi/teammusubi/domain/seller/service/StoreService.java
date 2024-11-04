package com.musubi.teammusubi.domain.seller.service;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse registerStore(Member loginedMember, StoreCreateRequest createRequest) {
        if (!(loginedMember.getRole() == MemberRoleEnum.OWNER)) {
            throw new IllegalArgumentException("사장님이 아닙니다");
        }

        Store store = new Store(createRequest, loginedMember.getId());

        storeRepository.save(store);
        return new StoreResponse(store);
    }
}
