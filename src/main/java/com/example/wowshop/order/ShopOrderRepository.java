package com.example.wowshop.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {

    List<ShopOrder> findByMemberEmailOrderByOrderedAtDesc(String email);
}
