package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;

    @Column(nullable = false)
    private Integer minPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String license;

    @Column(nullable = false)
    private boolean togo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    // 이 가게의 사장님
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 이 가게에 달린 리뷰들
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    // 이 가게가 받은 주문들
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Delivery> deliveryList = new ArrayList<>();

    // 가게 조회 시 메뉴 함께 조회
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menus = new ArrayList<>();


}
