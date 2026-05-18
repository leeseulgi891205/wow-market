package com.example.wowshop.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.wowshop.member.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PAID;

    @Column(nullable = false, updatable = false)
    private Instant orderedAt = Instant.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopOrderItem> items = new ArrayList<>();

    protected ShopOrder() {
    }

    public ShopOrder(Member member, BigDecimal totalPrice) {
        this.member = member;
        this.totalPrice = totalPrice;
    }

    public void addItem(ShopOrderItem item) {
        items.add(item);
        item.assignOrder(this);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getOrderedAt() {
        return orderedAt;
    }

    public List<ShopOrderItem> getItems() {
        return items;
    }
}
