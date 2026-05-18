package com.example.wowshop.product;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {

    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "ALL") String category, Model model, Authentication authentication) {
        model.addAttribute("products", productService.findProducts(category));
        model.addAttribute("category", category);
        model.addAttribute("username", authentication == null ? null : authentication.getName());
        return "home";
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product-detail";
    }
}
