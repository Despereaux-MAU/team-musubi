package com.musubi.teammusubi.seller;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import com.musubi.teammusubi.domain.seller.dto.MenuResponse;
import com.musubi.teammusubi.domain.seller.repository.SellerMenuRepository;
import com.musubi.teammusubi.domain.seller.repository.SellerStoreRepository;
import com.musubi.teammusubi.domain.seller.service.SellerMenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SellerMenuServiceTest {

    @InjectMocks
    private SellerMenuService sellerMenuService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SellerStoreRepository sellerStoreRepository;

    @Mock
    private SellerMenuRepository sellerMenuRepository;

    @Test
    @DisplayName("사업자 메뉴 등록 - 성공")
    void menuCreateTest() throws Exception {
        // given
        Long memberId = 1L;
        Long storeId = 1L;

        String menuName = "낙곱새";
        int price = 45000;
        String description = "맛있게 매운 낙곱새";

        MenuRequest mockRequestDto = createMenuRequest(menuName, price, description);

        Member mockMember = new Member();
        Store mockStore = new Store();
        mockStore.setMemberId(memberId);
        Menu mockMenu = createMenu(menuName, price, description, mockStore);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));
        given(sellerStoreRepository.findById(storeId)).willReturn(Optional.of(mockStore));
        given(sellerMenuRepository.save(any(Menu.class))).willReturn(mockMenu);

        // when
        MenuResponse response = sellerMenuService.createMenu(memberId, storeId, mockRequestDto);

        // then
        verify(sellerMenuRepository).save(any(Menu.class));
        assertEquals(menuName, response.getName());
        assertEquals(price, response.getPrice());
        assertEquals(description, response.getDescription());
    }

    @Test
    @DisplayName("사업자 메뉴 수정 - 성공")
    void menuModifyTest() throws Exception {
        // given
        Long memberId = 1L;
        Long storeId = 1L;
        Long menuId = 1L;

        // 수정 전 메뉴 정보
        String menuName = "낙곱새";
        int price = 45000;
        String description = "맛있게 매운 낙곱새";

        // 수정 후 메뉴 정보
        String menuNameToModify = "감자전";
        int priceToModify = 12000;
        String descriptionToModify = "먹는 순간 입안에서 감자밭이~";

        MenuRequest mockRequestDto
                = createMenuRequest(menuNameToModify, priceToModify, descriptionToModify);

        Member mockMember = new Member();
        Store mockStore = new Store();
        mockStore.setId(storeId);
        mockStore.setMemberId(memberId);
        Menu mockMenu = createMenu(menuName, price, description, mockStore);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));
        given(sellerStoreRepository.findById(storeId)).willReturn(Optional.of(mockStore));
        given(sellerMenuRepository.findById(menuId)).willReturn(Optional.of(mockMenu));

        // when
        MenuResponse response = sellerMenuService.modifyMenu(memberId, storeId, menuId, mockRequestDto);

        // then
        assertEquals(menuNameToModify, response.getName());
        assertEquals(priceToModify, response.getPrice());
        assertEquals(descriptionToModify, response.getDescription());
    }

    @Test
    @DisplayName("사업자 메뉴 삭제 - 성공")
    void menuDeleteTest() throws Exception {
        // given
        Long memberId = 1L;
        Long storeId = 1L;
        Long menuId = 1L;

        // 메뉴 정보
        String menuName = "낙곱새";
        int price = 45000;
        String description = "맛있게 매운 낙곱새";

        Member mockMember = new Member();
        Store mockStore = new Store();
        mockStore.setId(storeId);
        mockStore.setMemberId(memberId);
        Menu mockMenu = createMenu(menuName, price, description, mockStore);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));
        given(sellerStoreRepository.findById(storeId)).willReturn(Optional.of(mockStore));
        given(sellerMenuRepository.findById(menuId)).willReturn(Optional.of(mockMenu));

        // when
        MenuResponse response = sellerMenuService.closeMenu(memberId, storeId, menuId);

        // then
        assertEquals(MenuStatus.NOT_FOR_SALE, response.getStatus());
    }


    private MenuRequest createMenuRequest(String menuName, int price, String description) {
        return MenuRequest.builder()
                .name(menuName)
                .price(price)
                .description(description)
                .build();
    }

    private Menu createMenu(String menuName, int price, String description, Store mockStore) {
        return new Menu(menuName, price, description, mockStore);
    }
}
