package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String memberNickname;

    @Column(nullable = false, unique = true)
    private Long deliveryId;

    // 추후 기능: 대댓글 기능
//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    private Review parentReview;
//
//    @OneToMany(mappedBy = "parentReview", cascade = CascadeType.REMOVE)
//    private List<Review> childReviews;

    public static Review from(Store store, Long deliveryId, ReviewRequest req, String memberNickname) {
        Review review = new Review();
        review.init(store, deliveryId, req, memberNickname);
        return review;
    }

    private void init(Store store, Long deliveryId, ReviewRequest req, String memberNickname) {
        this.score = req.getScore();
        this.comment = req.getComment();
        this.store = store;
        this.memberNickname = memberNickname;
        this.deliveryId = deliveryId;
    }
}
