package com.mime.config;

import com.mime.model.Category;
import com.mime.model.Product;
import com.mime.repository.CategoryRepository;
import com.mime.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FakeStoreDataLoader implements CommandLineRunner {

    private static final String FAKE_STORE_API_URL = "https://fakestoreapi.com/products";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestClient restClient;
    private final boolean fakeStoreSeedEnabled;

    public FakeStoreDataLoader(ProductRepository productRepository,
                               CategoryRepository categoryRepository,
                               @Value("${app.seed.fakestore.enabled:false}") boolean fakeStoreSeedEnabled) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = RestClient.create();
        this.fakeStoreSeedEnabled = fakeStoreSeedEnabled;
    }

    @Override
    public void run(String... args) {
        if (!fakeStoreSeedEnabled || productRepository.count() > 0) {
            return;
        }

        List<FakeStoreProduct> fakeStoreProducts = restClient.get()
                .uri(FAKE_STORE_API_URL)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<>() {});

        if (fakeStoreProducts == null || fakeStoreProducts.isEmpty()) {
            return;
        }

        Map<String, Category> categoryByName = new HashMap<>();
        categoryRepository.findAll().forEach(category -> categoryByName.put(category.getName(), category));

        for (FakeStoreProduct remote : fakeStoreProducts) {
            Category category = categoryByName.computeIfAbsent(remote.category(), name -> {
                Category created = new Category();
                created.setName(name);
                return categoryRepository.save(created);
            });

            Product product = new Product();
            product.setName(remote.title());
            product.setDescription(remote.description());
            product.setPrice(remote.price());
            product.setRating(remote.rating() != null ? remote.rating().rate() : 0.0);
            product.setStock(100);
            product.setImageUrl(remote.image());
            product.setCategory(category);

            productRepository.save(product);
        }

        System.out.printf("Loaded %d products from Fake Store API.%n", fakeStoreProducts.size());
    }

    private record FakeStoreProduct(String title,
                                    String description,
                                    double price,
                                    String image,
                                    String category,
                                    FakeStoreRating rating) {
    }

    private record FakeStoreRating(double rate, int count) {
    }
}
