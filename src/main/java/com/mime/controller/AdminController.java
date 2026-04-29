package com.mime.controller;

import com.mime.model.Product;
import com.mime.service.*;

import com.mime.model.User;
import com.mime.service.AdminService;
import com.mime.service.ProductService;
import com.mime.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final AdminService adminService;
    private final UserService userService;

    public AdminController(ProductService productService,
                           AdminService adminService,
                           UserService userService) {
        this.productService = productService;
        this.adminService = adminService;
        this.userService = userService;
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

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "admin/users";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(defaultValue = "false") boolean adminRole,
                           Model model) {
        try {
            userService.createUser(user, adminRole);
            return "redirect:/admin/users";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("user", new User());
            model.addAttribute("error", ex.getMessage());
            return "admin/users";
        }
    }
}
