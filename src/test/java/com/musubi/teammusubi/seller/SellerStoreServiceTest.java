package com.musubi.teammusubi.seller;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreResponse;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import com.musubi.teammusubi.domain.seller.service.SellerStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class SellerStoreServiceTest {

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    private SellerStoreService sellerStoreService;

    @BeforeEach
    void setUp() {
        this.sellerStoreService = new SellerStoreService(sellerStoreRepository, null);
    }

    @Test
    @DisplayName("가게 - 정상적으로 가게 등록 케이스")
    void storeCreateSuccessTest() {

        //given
        var req = new StoreCreateRequest(
                "굽네",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                "서울시 종로구 창신동",
                "192-02-01114",
                true
        );
        Member member = mock(Member.class);
        // Stubbing : Mock 객체에게 행위를 정해주는 것
        when(member.getId()).thenReturn(1L);

        //when
        StoreResponse resp = sellerStoreService.registerStore(member, req);

        //then
        assertThat(resp).isNotNull();
        assertThat(resp.getName()).isEqualTo("굽네");
    }

    @Test
    @DisplayName("사장님이 소유한 모든 가게 정상 조회")
    void getAllStores() {
        //given
        Store store = new Store(
                "굽네폐업",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                "서울시 종로구 창신동",
                "192-02-01115",
                true,
                StoreStatus.CLOSE,
                1L
        );
        sellerStoreRepository.save(store);

        Store store1 = new Store(
                "굽네1호점",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                "서울시 종로구 창신동",
                "192-02-01115",
                true,
                StoreStatus.OPEN,
                1L
        );
        sellerStoreRepository.save(store1);

        Store store2 = new Store(
                "굽네2호점",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                "서울시 종로구 창신동",
                "192-02-01115",
                true,
                StoreStatus.OPEN,
                2L
        );
        sellerStoreRepository.save(store2);
        // Stubbing : Mock 객체에게 행위를 정해주는 것

        //when
        List<StoreResponse> respList = sellerStoreService.getAllStores(store1.getMemberId());

        //then
        assertThat(respList).isNotNull();
        assertThat(respList).hasSize(1);
        assertThat(respList.get(0).getName()).isEqualTo("굽네1호점");

    }

    @Test
    @DisplayName("사장님 가게 정보 정상 수정 ")
    void updateStoreSuccessTest() {
        //given
        Store store = new Store(
                "굽네1호점",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                "서울시 종로구 창신동",
                "192-02-01115",
                true,
                StoreStatus.OPEN,
                1L
        );
        sellerStoreRepository.save(store);

        // Stubbing : Mock 객체에게 행위를 정해주는 것
        var updateReq = new StoreUpdateRequest(
                "굽네2호점",
                LocalTime.parse("10:00:00"),
                LocalTime.parse("23:00:00"),
                10000,
                Category.KOREAN,
                true,
                StoreStatus.OPEN
        );

        //when
        StoreResponse updateResp = sellerStoreService.updateStore(store.getMemberId(), store.getId(), updateReq);

        //then
        assertThat(updateResp).isNotNull();
        assertThat(updateResp.getName()).isEqualTo("굽네2호점");
    }

}