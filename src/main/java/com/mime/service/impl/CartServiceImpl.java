package com.mime.service.impl;

import com.mime.model.*;
import com.mime.repository.*;
import com.mime.service.CartService;

import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartServiceImpl(CartRepository cartRepo,
                           ProductRepository productRepo,
                           UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Cart getCartByUser(Long userId) {
        return cartRepo.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(userRepo.findById(userId).orElseThrow());
                    return cartRepo.save(cart);
                });
    }

    @Override
    public Cart addToCart(Long userId, Long productId, int quantity) {

        Cart cart = getCartByUser(userId);
        Product product = productRepo.findById(productId).orElseThrow();

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCart(cart);

        cart.getItems().add(item);

        return cartRepo.save(cart);
    }
    @Override
public Cart checkout(Long userId) {

    Cart cart = getCartByUser(userId);

    Order order = new Order();
    order.setUser(cart.getUser());
    order.setCreatedAt(java.time.LocalDateTime.now());

    double total = 0;

    List<OrderItem> orderItems = new java.util.ArrayList<>();

    for (CartItem item : cart.getItems()) {

        OrderItem oi = new OrderItem();
        oi.setProduct(item.getProduct());
        oi.setQuantity(item.getQuantity());
        oi.setPrice(item.getProduct().getPrice());
        oi.setOrder(order);

        total += item.getQuantity() * item.getProduct().getPrice();

        orderItems.add(oi);
    }

    order.setItems(orderItems);
    order.setTotalAmount(total);
    order.setStatus("PLACED");

    // save order
    orderRepo.save(order);

    // clear cart
    cart.getItems().clear();
    cartRepo.save(cart);

    return cart;
}
}
