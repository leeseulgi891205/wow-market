package com.example.wowshop.order;

import java.math.BigDecimal;
import java.util.List;

import com.example.wowshop.auth.MemberService;
import com.example.wowshop.cart.CartItem;
import com.example.wowshop.cart.CartService;
import com.example.wowshop.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final ShopOrderRepository shopOrderRepository;
    private final CartService cartService;
    private final MemberService memberService;

    public OrderService(ShopOrderRepository shopOrderRepository, CartService cartService, MemberService memberService) {
        this.shopOrderRepository = shopOrderRepository;
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @Transactional
    public Long checkout(String email) {
        List<CartItem> items = cartService.findItems(email);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        Member member = memberService.findByEmail(email);
        BigDecimal total = cartService.total(email);
        ShopOrder order = new ShopOrder(member, total);
        for (CartItem item : items) {
            item.getProduct().decreaseStock(item.getQuantity());
            BigDecimal linePrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            order.addItem(new ShopOrderItem(item.getProduct(), item.getQuantity(), linePrice));
        }
        ShopOrder saved = shopOrderRepository.save(order);
        cartService.clear(email);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public List<ShopOrder> findOrders(String email) {
        return shopOrderRepository.findByMemberEmailOrderByOrderedAtDesc(email);
    }
}
