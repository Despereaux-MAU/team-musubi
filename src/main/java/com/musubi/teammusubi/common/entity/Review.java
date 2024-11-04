package com.musubi.teammusubi.common.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
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
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
