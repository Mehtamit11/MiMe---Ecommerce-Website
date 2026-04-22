package com.mime.service.impl;

import com.mime.model.*;
import com.mime.repository.*;
import com.mime.service.RecommendationService;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;

    public RecommendationServiceImpl(ProductRepository productRepo,
                                     OrderRepository orderRepo,
                                     CartRepository cartRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    @Override
    public List<Product> getRecommendations(Long userId) {

        Set<Product> recommended = new HashSet<>();

        // 1. From Orders
        List<Order> orders = orderRepo.findAll().stream()
                .filter(o -> o.getUser().getId().equals(userId))
                .toList();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                recommended.addAll(getSimilarProducts(item.getProduct().getId()));
            }
        }

        // 2. From Cart
        cartRepo.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(cart -> {
                    for (CartItem item : cart.getItems()) {
                        recommended.addAll(getSimilarProducts(item.getProduct().getId()));
                    }
                });

        // 3. Fallback (Top Rated)
        if (recommended.isEmpty()) {
            return productRepo.findAll().stream()
                    .sorted((a,b) -> Double.compare(b.getRating(), a.getRating()))
                    .limit(8)
                    .toList();
        }

        return recommended.stream().limit(8).toList();
    }

    @Override
    public List<Product> getSimilarProducts(Long productId) {

        Product base = productRepo.findById(productId).orElseThrow();

        return productRepo.findAll().stream()
                .filter(p -> !p.getId().equals(productId))
                .filter(p -> p.getCategory().getId().equals(base.getCategory().getId()))
                .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
