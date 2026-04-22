package com.mime.service;

import com.mime.model.Cart;

public interface CartService {

    Cart getCartByUser(Long userId);

    Cart addToCart(Long userId, Long productId, int quantity);
}
