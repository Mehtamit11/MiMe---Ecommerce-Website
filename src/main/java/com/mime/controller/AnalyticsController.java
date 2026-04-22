package com.mime.controller;

import com.mime.service.AnalyticsService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/admin/analytics")
    public String analytics(Model model) {

        model.addAttribute("sales", service.getSalesOverTime());
        model.addAttribute("products", service.getTopProducts());
        model.addAttribute("categories", service.getCategoryPerformance());

        return "admin/analytics";
    }
}
