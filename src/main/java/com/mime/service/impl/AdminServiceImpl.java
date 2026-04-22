package com.mime.service.impl;

import com.mime.repository.*;
import com.mime.service.AdminService;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    public AdminServiceImpl(UserRepository userRepo, OrderRepository orderRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public long getTotalUsers() {
        return userRepo.count();
    }

    @Override
    public long getTotalOrders() {
        return orderRepo.count();
    }

    @Override
    public double getTotalRevenue() {
        return orderRepo.findAll()
                .stream()
                .mapToDouble(o -> o.getTotalAmount())
                .sum();
    }
}
