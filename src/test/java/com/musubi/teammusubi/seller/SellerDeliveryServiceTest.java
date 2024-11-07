package com.musubi.teammusubi.seller;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.DeliveryResponse;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerDeliveryRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import com.musubi.teammusubi.domain.seller.service.SellerDeliveryService;
import com.musubi.teammusubi.domain.seller.service.SellerMenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SellerDeliveryServiceTest {

    @InjectMocks
    private SellerDeliveryService sellerDeliveryService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SellerStoreRepository sellerStoreRepository;

    @Mock
    private SellerDeliveryRepository sellerDeliveryRepository;

    @Test
    @DisplayName("사업자 가게별 주문 조회 - 성공")
    void deliveryRetrieveTest() throws Exception {
        // given
        Long storeId = 1L;
        DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;
        int page = 1;
        int size = 10;
        String criteria = "createdAt";
        String sort = "DESC";

        Store mockStore = new Store();
        Delivery mockDelivery1 = new Delivery();
        Delivery mockDelivery2 = new Delivery();
        List<Delivery> deliveryList = Arrays.asList(mockDelivery1, mockDelivery2);
        Page<Delivery> deliveryPage = new PageImpl<>(deliveryList);

        given(sellerStoreRepository.findById(storeId)).willReturn(Optional.of(mockStore));
        given(sellerDeliveryRepository
                .findByStoreIdAndStatus(any(Long.class), any(DeliveryStatus.class), any(Pageable.class)))
                .willReturn(deliveryPage);

        // when
        Page<DeliveryResponse> responses
                = sellerDeliveryService.retrieveDeliveryByStoreIdAsPageSize(
                        storeId, deliveryStatus, page, size, criteria, sort);

        // then
        assertEquals(2, responses.getTotalElements());
        assertEquals(1, responses.getTotalPages());
        assertEquals(2, responses.getContent().size());
    }

    @Test
    @DisplayName("사업자 주문 상태 변경 - 성공")
    void deliveryStatusModifyTest() throws Exception {
        // given
        Long memberId = 1L;
        Long storeId = 1L;
        Long deliveryId = 1L;
        DeliveryStatus deliveryStatusToModify = DeliveryStatus.IN_PROGRESS;

        Member mockMember = new Member();
        Store mockStore = new Store();
        Delivery mockDelivery = new Delivery();
        mockDelivery.setStoreId(storeId);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));
        given(sellerStoreRepository.findById(storeId)).willReturn(Optional.of(mockStore));
        given(sellerDeliveryRepository.findById(deliveryId)).willReturn(Optional.of(mockDelivery));

        // when
        DeliveryResponse response
                = sellerDeliveryService.changeDeliveryStatus(memberId, storeId, deliveryId, deliveryStatusToModify);

        // then
        assertEquals(deliveryStatusToModify, response.getStatus());
    }
}
