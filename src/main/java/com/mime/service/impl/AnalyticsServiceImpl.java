package com.mime.service.impl;

import com.mime.model.*;
import com.mime.repository.*;
import com.mime.service.AnalyticsService;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepo;

    public AnalyticsServiceImpl(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Map<String, Double> getSalesOverTime() {

        return orderRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCreatedAt().toLocalDate().toString(),
                        Collectors.summingDouble(Order::getTotalAmount)
                ));
    }

    @Override
    public Map<String, Integer> getTopProducts() {

        Map<String, Integer> result = new HashMap<>();

        for (Order order : orderRepo.findAll()) {
            for (OrderItem item : order.getItems()) {
                result.merge(item.getProduct().getName(),
                        item.getQuantity(),
                        Integer::sum);
            }
        }
        return result;
    }

    @Override
    public Map<String, Double> getCategoryPerformance() {

        Map<String, Double> result = new HashMap<>();

        for (Order order : orderRepo.findAll()) {
            for (OrderItem item : order.getItems()) {

                String category = item.getProduct().getCategory().getName();

                result.merge(category,
                        item.getPrice() * item.getQuantity(),
                        Double::sum);
            }
        }
        return result;
    }
}
