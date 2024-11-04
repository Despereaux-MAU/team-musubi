package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.entity.Review;
import com.musubi.teammusubi.common.entity.Store;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequest {

    private Integer score;

    @Size(max = 255)
    private String comment;

}
