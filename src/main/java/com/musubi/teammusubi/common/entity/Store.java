package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
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

    @Column()
    private LocalTime openTime;

    @Column()
    private LocalTime closeTime;

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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // 이 가게에 달린 리뷰들
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    // 이 가게가 받은 주문들
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Delivery> deliveryList = new ArrayList<>();

    // 가게 조회 시 메뉴 함께 조회
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menus = new ArrayList<>();


    public Store(StoreCreateRequest createRequest, Long loginedMemberId) {
        this.name = createRequest.getName();
        this.openTime = createRequest.getOpenTime();
        this.closeTime = createRequest.getCloseTime();
        this.minPrice = createRequest.getMinPrice();
        this.category = createRequest.getCategory();
        this.address = createRequest.getAddress();
        this.license = createRequest.getLicense();
        this.togo = createRequest.isTogo();
        this.status = createRequest.getStatus();
        this.memberId = loginedMemberId;
    }
}
