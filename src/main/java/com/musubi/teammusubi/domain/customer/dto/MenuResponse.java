package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
        this.isDeleted = menu.isDeleted();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
    }


}
