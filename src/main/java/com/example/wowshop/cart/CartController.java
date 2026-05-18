package com.example.wowshop.cart;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Authentication authentication, Model model) {
        model.addAttribute("items", cartService.findItems(authentication.getName()));
        model.addAttribute("total", cartService.total(authentication.getName()));
        return "cart";
    }

    @PostMapping("/cart/items")
    public String add(Authentication authentication, @RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity) {
        cartService.add(authentication.getName(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/items/{id}/delete")
    public String remove(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }
}
