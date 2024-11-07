package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.DeliveryMenu;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.customer.repository.DeliveryMenuRepository;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMenuRepository deliveryMenuRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public List<Delivery> readAllDeliveries(Long memberId) {
        return deliveryRepository.findByMemberId(memberId);
    }

    public Delivery readDelivery(Long memberId, Long deliveryId) {
        Delivery delivery = deliveryRepository.findByIdSafeWithDeliveryMenus(deliveryId);

        // 전달된 id 에 해당하는 주문 엔티티의 보유자가 아닐 경우 예외를 반환합니다.
        if (!memberId.equals(delivery.getMemberId())) {
            throw new ResponseException(ExceptionType.NOT_OWNER_OF_DELIVERY);
        }

        return delivery;
    }

    @Transactional
    public Delivery orderDelivery(String details, Long memberId, Long storeId, Map<Long, Integer> orderMap) {

        // 주문 엔티티를 전용 팩토리 메서드로 생성합니다. (*주문 상태를 기본값 Pending 으로 생성함)
        // 아직 초기화되지 않은 NOT NULL 필드가 있으므로, 저장하지 않습니다.
        Delivery delivery = Delivery.deliveryFactory(details, memberId, storeId);

        // 전달된 맵에서 수량이 0 이하인 경우, 해당 메뉴는 주문되지 않은 것으로 간주합니다.
        orderMap.values().removeIf(integer -> integer <= 0);

        // 전달된 맵에 메뉴가 없을 경우, 해당 예외를 반환합니다.
        if(orderMap.isEmpty()) {
            throw new ResponseException(ExceptionType.MINIMUM_ORDER_AMOUNT);
        }

        // Id 로 보유하는 정보들을 영속 엔티티로 가져옵니다.
        // 여러 메뉴를 맵 key 를 통해, 저장소에서 한 번에 불러옵니다.
        Store store = storeRepository.findByIdSafe(storeId);
        List<Menu> menus = menuRepository.findAllById(orderMap.keySet());

        // 불러온 메뉴들이 모두 현재 식당의 메뉴로 등록되어 있는 지 확인하고, 아닐 경우 예외를 반환합니다.
        if (!new HashSet<>(store.getMenus()).containsAll(menus)) {
            throw new ResponseException(ExceptionType.NOT_A_MENU_OF_STORE);
        }

        // 불러온 메뉴들 중 판매 중지된 상품이 있다면, 해당 예외를 반환합니다.
        menus.forEach(menu -> {
            if (menu.getStatus().equals(MenuStatus.NOT_FOR_SALE)) {
                throw new ResponseException(ExceptionType.MENU_NOT_FOR_SALE);
            }
        });

        // 총 합계를 가산할 변수와 중간 테이블 객체를 저장할 콜렉션
        int totalPrice = 0;
        List<DeliveryMenu> deliveryMenus = new ArrayList<>();

        // 메뉴 수만큼 반복합니다.
        for (Menu menu : menus) {
            // 메뉴에 해당하는 수량을 맵에서 가져옵니다.
            Long menuId = menu.getId();
            Integer quantity = orderMap.get(menuId);

            // 메뉴에서 가져온 단가 정보로, 부분 합계 계산 & 총 합계 가산을 수행합니다.
            int subTotalPrice = menu.getPrice() * quantity;
            totalPrice = Math.addExact(totalPrice, subTotalPrice);

            // 중간 테이블 객체를 생성합니다.
            DeliveryMenu deliveryMenu = new DeliveryMenu();
            deliveryMenu.setMenu(menu);
            deliveryMenu.setQuantity(quantity);
            deliveryMenu.setPrice(subTotalPrice);

            // 비영속 상태의 주문 엔티티와 미리 참조를 맺어줍니다.
            deliveryMenu.connectDelivery(delivery);

            // 중간 테이블을 저장합니다.
            deliveryMenus.add(deliveryMenu);
        }
        // 중간 테이블을 한 번에 저장합니다.
        deliveryMenuRepository.saveAll(deliveryMenus);

        // 주문 엔티티에 누락되었던 총 합계를 저장하고, 마지막으로 가게와도 참조를 맺습니다.
        delivery.setTotalPrice(totalPrice);
        delivery.joinStoreDelivery(store);

        // 주문 엔티티를 저장하고 반환합니다.
        return deliveryRepository.save(delivery);
    }

}
