package com.musubi.teammusubi.common.entity;

import com.musubi.teammusubi.common.enums.MenuStatus;
import com.musubi.teammusubi.domain.seller.dto.MenuRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.musubi.teammusubi.common.enums.MenuStatus.*;

@Entity
@Getter
@Setter
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    // 메뉴를 제공하는 가게
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(String name, Integer price, String description, Store store) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.store = store;
        this.status = FOR_SALE;
    }

    public static Menu of(MenuRequest request, Store store) {
        return new Menu(request.getName(), request.getPrice(), request.getDescription(), store);
    }

    public void modify(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void close() {
        this.status = NOT_FOR_SALE;
    }
}
