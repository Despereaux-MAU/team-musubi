package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String details;

    @Column
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(nullable = false)
    private Integer totalPrice;

    // todo - 추가됨, 선택한 수량
    @Column(nullable = false)
    private Integer quantity;

    // todo - 추가됨, 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    // 주문을 한 고객
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
    @Column
    private long memberId;

    // 주문을 받은 가게
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public static Delivery DeliveryFactory(long memberId, Store store, Menu menu, Delivery base) {
        return Delivery.builder()
                .details(base.getDetails())
                .status(DeliveryStatus.PENDING)
                .quantity(base.getQuantity())
                .totalPrice(base.getTotalPrice())
                .memberId(memberId)
                .store(store)
                .menu(menu)
                .build();
    }
}