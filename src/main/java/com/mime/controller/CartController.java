package com.mime.controller;

import com.mime.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id,
                      @RequestParam int quantity,
                      @SessionAttribute("userId") Long userId) {

        service.addToCart(userId, id, quantity);
        return "redirect:/cart";
    }

    @GetMapping
    public String cart() {
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(@SessionAttribute("userId") Long userId) {
        service.checkout(userId);
        return "redirect:/orders";
    }
}
