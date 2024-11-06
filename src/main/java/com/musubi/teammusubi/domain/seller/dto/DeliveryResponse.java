package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryResponse {
    private Long id;
    private String details;
    private DeliveryStatus status;
    private Integer totalPrice;
    private Long storeId;

    public static DeliveryResponse from(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getDetails(),
                delivery.getStatus(),
                delivery.getTotalPrice(),
                delivery.getStoreId()
        );
    }
}