package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
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
    // 현재 Store가 없어서 id로 대체
    // 병합 이후 수정
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;

    @Column
    private Long storeId;


//    public Menu(String name, Integer price, String description, boolean isDeleted, Store store) {
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.isDeleted = isDeleted;
//        this.store = store;
//    }
//
//    public static Menu of(MenuRequest request, Store store) {
//        return new Menu(request.getName(), request.getPrice(), request.getDescription(), false, store);
//    }

    public Menu(String name, Integer price, String description, boolean isDeleted, Long storeId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isDeleted = isDeleted;
        this.storeId = storeId;
    }

    public static Menu of(MenuRequest request, Long storeId) {
        return new Menu(request.getName(), request.getPrice(), request.getDescription(), false, storeId);
    }

}
