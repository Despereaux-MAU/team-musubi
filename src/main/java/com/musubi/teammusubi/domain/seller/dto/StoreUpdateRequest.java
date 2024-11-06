package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreUpdateRequest {

    @NotBlank(message = "가게 이름을 입력하세요.")
    private String name;
    @NotNull(message = "오픈 시간을 입력하세요.")
    private LocalTime openTime;
    @NotNull(message = "클로즈 시간을 입력하세요.")
    private LocalTime closeTime;
    @NotNull(message = "최소 가격을 입력하세요.")
    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    private Integer minPrice;
    @NotNull(message = "카테고리를 선택하세요.")
    private Category category;
    private boolean togo;
    private StoreStatus status;

}
