package com.example.wowshop.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = "product")
    List<CartItem> findByMemberEmail(String email);

    @EntityGraph(attributePaths = {"member", "product"})
    Optional<CartItem> findByMemberEmailAndProductId(String email, Long productId);

    void deleteByMemberEmail(String email);
}
