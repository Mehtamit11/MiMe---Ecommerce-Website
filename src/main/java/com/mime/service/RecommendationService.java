package com.mime.service;

import com.mime.model.Product;
import java.util.List;

public interface RecommendationService {

    List<Product> getRecommendations(Long userId);

    List<Product> getSimilarProducts(Long productId);
}
