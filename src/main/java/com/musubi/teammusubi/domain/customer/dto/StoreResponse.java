package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreResponse {

    private Long id;
    private String name;
    private List<MenuResponse> menus;


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
