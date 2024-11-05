package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Delivery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDeliveryRequest {

    private String details;
    private Integer quantity;
    private Integer totalPrice;

    public Delivery toBaseDelivery() {
        return Delivery.builder()
                .details(this.details)
                .quantity(this.quantity)
                .totalPrice(this.totalPrice)
                .build();
    }
}
