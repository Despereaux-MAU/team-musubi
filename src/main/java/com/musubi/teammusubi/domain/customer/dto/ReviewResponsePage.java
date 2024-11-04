package com.musubi.teammusubi.domain.customer.dto;

import com.musubi.teammusubi.common.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ReviewResponsePage {

    private List<ReviewResponse> reviews;
    private int totalPages;
    private long totalElements;

    public ReviewResponsePage(Page<Review> reviews) {
        this.reviews = reviews.map(ReviewResponse::to).toList();
        this.totalPages = reviews.getTotalPages();
        this.totalElements = reviews.getTotalElements();
    }
}
