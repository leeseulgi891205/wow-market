package com.example.wowshop.order;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public String checkout(Authentication authentication, RedirectAttributes redirectAttributes) {
        Long orderId = orderService.checkout(authentication.getName());
        redirectAttributes.addFlashAttribute("message", "주문이 완료되었습니다. 주문번호 #" + orderId);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orders(Authentication authentication, Model model) {
        model.addAttribute("orders", orderService.findOrders(authentication.getName()));
        return "orders";
    }
}
