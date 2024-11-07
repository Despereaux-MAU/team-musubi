package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.entity.Menu;
import com.musubi.teammusubi.common.enums.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private MenuStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuResponse from(final Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription(),
                menu.getStatus(),
                menu.getCreatedAt(),
                menu.getUpdatedAt()
        );
    }
}
