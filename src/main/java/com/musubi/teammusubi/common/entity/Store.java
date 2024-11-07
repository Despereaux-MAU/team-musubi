package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.common.enums.StoreStatus;
import com.musubi.teammusubi.domain.seller.dto.StoreCreateRequest;
import com.musubi.teammusubi.domain.seller.dto.StoreUpdateRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private LocalTime openTime;

    // 고객 입장에서 가게 조회할 때 일자가 들어가면, 로직 수정이 많이 필요
    @Column
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
    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();

    // 이 가게가 받은 주문들
    @OneToMany
    @JoinColumn(name = "delivery_list")
    private List<Delivery> deliveryList = new ArrayList<>();

    // 가게 조회 시 메뉴 함께 조회
    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    public Store(String name, LocalTime openTime, LocalTime closeTime, Integer minPrice, Category category, String address, String license, boolean togo, StoreStatus status, Long memberId) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minPrice = minPrice;
        this.category = category;
        this.address = address;
        this.license = license;
        this.togo = togo;
        this.status = status;
        this.memberId = memberId;
    }

    public Store(StoreCreateRequest createRequest, Long loginedMemberId) {
        this.name = createRequest.getName();
        this.openTime = createRequest.getOpenTime();
        this.closeTime = createRequest.getCloseTime();
        this.minPrice = createRequest.getMinPrice();
        this.category = createRequest.getCategory();
        this.address = createRequest.getAddress();
        this.license = createRequest.getLicense();
        this.togo = createRequest.isTogo();
        this.memberId = loginedMemberId;
        this.status = StoreStatus.OPEN;
    }

    public void update(@Valid StoreUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.openTime = updateRequest.getOpenTime();
        this.closeTime = updateRequest.getCloseTime();
        this.minPrice = updateRequest.getMinPrice();
        this.category = updateRequest.getCategory();
        this.togo = updateRequest.isTogo();
        this.status = updateRequest.getStatus();
    }

    public void closeStore() {
        this.status = StoreStatus.CLOSE;
    }
}
