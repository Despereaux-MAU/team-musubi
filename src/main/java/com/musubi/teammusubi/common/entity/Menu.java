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
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(String name, Integer price, String description, Store store) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isDeleted = false;
        this.store = store;
    }

    public static Menu of(MenuRequest request, Store store) {
        return new Menu(request.getName(), request.getPrice(), request.getDescription(), store);
    }

    public void modify(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void delete() {
        if(isDeleted) {
            throw new IllegalStateException("이미 삭제된 메뉴입니다.");
        }
        this.isDeleted = true;
    }
}
