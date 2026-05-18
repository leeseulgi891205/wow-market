package com.example.wowshop.product;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 40)
    private String category;

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, length = 20)
    private String badge;

    @Column(nullable = false, length = 160)
    private String imageUrl;

    protected Product() {
    }

    public Product(String name, String category, BigDecimal price, int stockQuantity, String description, String badge, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.badge = badge;
        this.imageUrl = imageUrl;
    }

    public void decreaseStock(int quantity) {
        if (stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        stockQuantity -= quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getDescription() {
        return description;
    }

    public String getBadge() {
        return badge;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
