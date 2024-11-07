package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.DeliveryMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryMenuResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private Integer price;

    public static DeliveryMenuResponse of(DeliveryMenu deliveryMenu) {
        DeliveryMenuResponse response = new DeliveryMenuResponse();
        response.init(deliveryMenu);
        return response;
    }

    private void init(DeliveryMenu deliveryMenu) {
        this.id = deliveryMenu.getMenu().getId();
        this.name = deliveryMenu.getMenu().getName();
        this.quantity = deliveryMenu.getQuantity();
        this.price = deliveryMenu.getPrice();
    }
}
