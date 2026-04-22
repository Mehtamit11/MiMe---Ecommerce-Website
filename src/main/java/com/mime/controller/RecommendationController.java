package com.mime.controller;

import com.mime.service.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/recommendations")
    public String recommendations(@SessionAttribute("userId") Long userId,
                                  Model model) {

        model.addAttribute("products", service.getRecommendations(userId));
        return "recommendations";
    }
}
