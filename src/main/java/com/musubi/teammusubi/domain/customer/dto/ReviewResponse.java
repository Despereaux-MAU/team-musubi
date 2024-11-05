package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponse {

    private Long id;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
    private String memberNickname;
    private Long deliveryId;


    public static ReviewResponse of(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.init(review);
        return response;
    }

    private void init(Review review) {
        this.id = review.getId();
        this.score = review.getScore();
        this.comment = review.getComment();
        this.memberNickname = review.getMemberNickname();
        this.deliveryId = review.getDeliveryId();
        this.createdAt = review.getCreatedAt();
    }


    public static ReviewResponse to(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.init(review);
        return response;
    }
}
