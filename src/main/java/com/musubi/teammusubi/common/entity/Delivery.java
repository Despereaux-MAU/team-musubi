package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    // 주문자
    @Column(nullable = false)
    private long memberId;

    // 주문을 받은 가게
    @Column
    private Long storeId;

    @OneToMany(mappedBy = "delivery")
    @Builder.Default
    private final List<DeliveryMenu> deliveryMenu = new ArrayList<>();

    public static Delivery deliveryFactory(String details, Long memberId, Long storeId) {
        return Delivery.builder()
                .details(details)
                .status(DeliveryStatus.PENDING)
                .memberId(memberId)
                .storeId(storeId)
                .build();
    }

    public void changeStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void joinStoreDelivery(Store store) {
        this.storeId = store.getId();
        store.getDeliveryList().add(this);
    }

}
