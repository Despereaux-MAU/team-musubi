package com.musubi.teammusubi.domain.customer.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequest {

    private Integer score;

    @Size(max = 255)
    private String comment;

}
