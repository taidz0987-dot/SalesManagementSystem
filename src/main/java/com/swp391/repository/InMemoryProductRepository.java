package com.swp391.repository;

import com.swp391.model.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Integer, Product> data = new HashMap<>();

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void save(Product product) {
        data.put(product.getId(), product);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
