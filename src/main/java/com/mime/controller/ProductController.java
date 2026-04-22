package com.mime.controller;

import com.mime.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "products";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        return "product-details";
    }
}
