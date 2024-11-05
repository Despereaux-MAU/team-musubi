package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoreResponse {

    private Long id;//1
    private String name; //
    private Category category;
    private String address;
    private boolean togo;
    private StoreStatus status;
    private List<MenuResponse> menus;

    public StoreResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.togo = store.isTogo();
        this.status = store.getStatus();
        this.menus = store.getMenus().stream().map(MenuResponse::of).toList();
    }

    public static StoreResponse of(Store store) {
        StoreResponse response = new StoreResponse();
        response.init(store);
        return response;
    }

    private void init(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.menus = store.getMenus().stream().map(MenuResponse::of).toList();
    }

}
