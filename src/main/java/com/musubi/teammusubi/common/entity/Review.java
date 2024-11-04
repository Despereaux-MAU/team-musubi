package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.domain.customer.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    // 리뷰가 작성된 가게
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // 리뷰를 작성한 고객
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Column(nullable = false)
    private Long memberId;

    public static Review from(Store store, ReviewRequest req, Member member) {
        Review review = new Review();
        review.init(store, req, member);
        return review;
    }

    private void init(Store store, ReviewRequest req, Member member) {
        this.score = req.getScore();
        this.comment = req.getComment();
        this.store = store;
        this.member = member;
    }
}
