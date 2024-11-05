package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Delivery extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String details;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Integer totalPrice;

    // 주문을 한 고객
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 주문을 받은 가게
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
