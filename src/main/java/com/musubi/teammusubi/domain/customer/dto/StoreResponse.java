package com.musubi.teammusubi.domain.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoreResponse {

    private Long id;//1
    private String name; //
    private LocalTime openTime;
    private LocalTime closeTime;
    private Category category;
    private String address;
    private boolean togo;
    private StoreStatus status;
    @Setter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuResponse> menus;

    public static StoreResponse of(Store store) {
        StoreResponse response = new StoreResponse();
        response.init(store);
        return response;
    }

    public static StoreResponse allOf(Store store) {
        StoreResponse response = StoreResponse.of(store);
        response.setMenus(store.getMenus().stream().map(MenuResponse::of).toList());
        return response;
    }

    private void init(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.togo = store.isTogo();
        this.status = store.getStatus();
    }

}
