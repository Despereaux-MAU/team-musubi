package com.musubi.teammusubi.domain.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDeliveryRequest {
    private Long menuId;
    private Integer quantity;
}
