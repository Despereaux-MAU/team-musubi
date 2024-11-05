package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreResponse {

    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minPrice;
    private Category category;
    private String address;
    private String license;
    private boolean togo;
    private StoreStatus status;
    private Long memberId;


    public StoreResponse(Store store) {
        this.name = store.getName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minPrice = store.getMinPrice();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.license = store.getLicense();
        this.togo = store.isTogo();
        this.status = store.getStatus();
        this.memberId = store.getMemberId();
    }
}
