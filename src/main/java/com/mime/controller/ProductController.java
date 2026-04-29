package com.mime.controller;

import com.mime.service.ProductService;
import com.mime.service.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
private final ProductService service;
    private final RecommendationService recService;

    public ProductController(ProductService service, RecommendationService recService) {
        this.service = service;
        this.recService = recService;
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "products";
    }
    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        model.addAttribute("similarProducts", recService.getSimilarProducts(id));
        return "product-details";
    }
}
