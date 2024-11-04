package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Review;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReviewResponse {

    private Long id;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewResponse of(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.init(review);
        return response;
    }

    private void init(Review review) {
        this.id = review.getId();
        this.score = review.getScore();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }


}
