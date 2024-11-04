package com.musubi.teammusubi.domain.seller.dto;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreCreateRequest {

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
    @NotBlank(message = "주소를 입력하세요.")
    @Size(max = 255, message = "주소는 최대 255자까지 입력할 수 있습니다.")
    private String address;
    @NotBlank(message = "사업자 등록증을 입력하세요.")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 등록증 형식이 올바르지 않습니다.")
    private String license;
    private boolean togo;
    private final StoreStatus status = StoreStatus.OPEN;

}
