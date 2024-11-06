package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.DeliveryMenu;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.repository.DeliveryMenuRepository;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        if (!memberId.equals(delivery.getMemberId())) {
            throw new RuntimeException("(Dummy) 로그인한 사용자의 주문이 아닙니다!");
        }

        return delivery;
    }

    @Transactional
    public Delivery orderDelivery(String details, Long memberId, Long storeId, Map<Long, Integer> orderMap) {

        // 주문 엔티티를 전용 팩토리 메서드로 생성합니다. (*주문 상태를 기본값 Pending 으로 생성함)
        // 아직 초기화되지 않은 NOT NULL 필드가 있으므로, 저장하지 않습니다.
        Delivery delivery = Delivery.deliveryFactory(details, memberId, storeId);

        // Id 로 보유하는 정보들을 영속 엔티티로 가져옵니다.
        // 여러 메뉴를 맵 key 를 통해, 저장소에서 한 번에 불러옵니다.
        Store store = storeRepository.findByIdSafe(storeId);
        List<Menu> menus = menuRepository.findAllById(orderMap.keySet());

        // 총 합계를 가산할 변수
        int totalPrice = 0;

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
            deliveryMenuRepository.save(deliveryMenu);
        }

        // 주문 엔티티에 누락되었던 총 합계를 저장하고, 마지막으로 가게와도 참조를 맺습니다.
        delivery.setTotalPrice(totalPrice);
        delivery.joinStoreDelivery(store);

        // 주문 엔티티를 저장하고 반환합니다.
        return deliveryRepository.save(delivery);
    }

}
