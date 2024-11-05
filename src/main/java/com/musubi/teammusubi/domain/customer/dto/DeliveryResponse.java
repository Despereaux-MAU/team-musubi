package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryResponse {

    private long id;
    private String details;
    private DeliveryStatus status;
    private int totalPrice;

    public DeliveryResponse(Delivery delivery) {
        this.id = delivery.getId();
        this.details = delivery.getDetails();
        this.status = delivery.getStatus();
        this.totalPrice = delivery.getTotalPrice();
    }
}
