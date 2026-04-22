package com.mime.controller;

import com.mime.model.Product;
import com.mime.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final AdminService adminService;

    public AdminController(ProductService productService, AdminService adminService) {
        this.productService = productService;
        this.adminService = adminService;
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", adminService.getTotalUsers());
        model.addAttribute("orders", adminService.getTotalOrders());
        model.addAttribute("revenue", adminService.getTotalRevenue());
        return "admin/dashboard";
    }

    // PRODUCT LIST
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    // ADD PRODUCT PAGE
    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    // SAVE PRODUCT
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // DELETE PRODUCT
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
