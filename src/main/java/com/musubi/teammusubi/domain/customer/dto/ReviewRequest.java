package com.musubi.teammusubi.domain.customer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequest {

    @Min(value = 1)
    @Max(value = 5)
    private Integer score;

    @Size(max = 255)
    private String comment;

}
