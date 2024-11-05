package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.domain.customer.repository.DeliveryRepository;
import com.musubi.teammusubi.domain.customer.repository.MenuRepository;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public List<Delivery> readAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Transactional
    public Delivery orderDelivery(Long memberId, Long storeId, Long menuId, Delivery baseDelivery) {
        Store store = storeRepository.findByIdSafe(storeId);
        Menu menu = menuRepository.findByIdSafe(menuId);

        Delivery delivery = Delivery.DeliveryFactory(memberId, store, menu, baseDelivery);
        delivery = deliveryRepository.save(delivery);

        return delivery;
    }
}
