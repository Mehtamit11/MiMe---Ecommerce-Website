package com.mime.service.impl;

import com.mime.model.*;
import com.mime.repository.*;
import com.mime.service.WishlistService;

import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public WishlistServiceImpl(WishlistRepository repo,
                               UserRepository userRepo,
                               ProductRepository productRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    public void add(Long userId, Long productId) {

        Wishlist wishlist = repo.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    Wishlist w = new Wishlist();
                    w.setUser(userRepo.findById(userId).orElseThrow());
                    return repo.save(w);
                });

        wishlist.getProducts().add(productRepo.findById(productId).orElseThrow());
        repo.save(wishlist);
    }
}
