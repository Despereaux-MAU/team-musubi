package com.musubi.teammusubi.domain.customer.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class ReviewRequest {

    @Range(min=1, max = 5)
    private Integer score;

    @Size(max = 255)
    private String comment;

}
