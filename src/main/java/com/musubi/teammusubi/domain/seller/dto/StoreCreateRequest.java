package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreCreateRequest {

    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private Category category;
    private String address;
    private String license;
    private boolean togo;
    private final StoreStatus status = StoreStatus.OPEN;

}
