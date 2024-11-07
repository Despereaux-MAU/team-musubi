package com.musubi.teammusubi.domain.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musubi.teammusubi.common.entity.Delivery;
import com.musubi.teammusubi.common.enums.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeliveryResponse {

    private long id;
    private String details;
    private DeliveryStatus status;
    private int totalPrice;
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DeliveryMenuResponse> deliveryMenus;

    public static DeliveryResponse of(Delivery delivery) {
        DeliveryResponse response = new DeliveryResponse();
        response.init(delivery);
        return response;
    }

    public static DeliveryResponse allOf(Delivery delivery) {
        DeliveryResponse response = DeliveryResponse.of(delivery);
        response.setDeliveryMenus(delivery.getDeliveryMenu().stream().map(DeliveryMenuResponse::of).toList());
        return response;
    }

    private void init(Delivery delivery) {
        this.id = delivery.getId();
        this.details = delivery.getDetails();
        this.status = delivery.getStatus();
        this.totalPrice = delivery.getTotalPrice();
    }
}
