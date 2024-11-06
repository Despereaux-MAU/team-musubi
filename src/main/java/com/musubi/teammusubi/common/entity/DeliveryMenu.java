package com.musubi.teammusubi.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DeliveryMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public void connectDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.getDeliveryMenu().add(this);
    }

    public void disconnectDelivery(Delivery delivery) {
        this.delivery = null;
        delivery.getDeliveryMenu().remove(this);
    }

}
