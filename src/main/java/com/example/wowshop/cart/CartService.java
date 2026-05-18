package com.example.wowshop.cart;

import java.math.BigDecimal;
import java.util.List;

import com.example.wowshop.auth.MemberService;
import com.example.wowshop.member.Member;
import com.example.wowshop.product.Product;
import com.example.wowshop.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public CartService(CartItemRepository cartItemRepository, ProductService productService, MemberService memberService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.memberService = memberService;
    }

    @Transactional
    public void add(String email, Long productId, int quantity) {
        Product product = productService.findById(productId);
        cartItemRepository.findByMemberEmailAndProductId(email, productId)
                .ifPresentOrElse(
                        item -> item.addQuantity(quantity),
                        () -> {
                            Member member = memberService.findByEmail(email);
                            cartItemRepository.save(new CartItem(member, product, quantity));
                        }
                );
    }

    @Transactional(readOnly = true)
    public List<CartItem> findItems(String email) {
        return cartItemRepository.findByMemberEmail(email);
    }

    @Transactional(readOnly = true)
    public BigDecimal total(String email) {
        return findItems(email).stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Transactional
    public void clear(String email) {
        cartItemRepository.deleteByMemberEmail(email);
    }
}
