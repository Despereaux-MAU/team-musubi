package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    public static MenuResponse of(Menu menu) {
        MenuResponse response = new MenuResponse();
        response.init(menu);
        return response;

    }

    private void init(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }


}
