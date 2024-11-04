package com.musubi.teammusubi.domain.seller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequest {
    @NotNull(message = "메뉴명은 필수 입력 사항입니다.")
    private String name;
    @NotNull(message = "메뉴 가격은 필수 입력 사항입니다.")
    private Integer price;
    private String description;
    private boolean isDeleted;
}