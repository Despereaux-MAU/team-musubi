package com.musubi.teammusubi.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = true)
    private String description;

    @Column
    private boolean isDeleted;

    // 메뉴를 제공하는 가게
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
