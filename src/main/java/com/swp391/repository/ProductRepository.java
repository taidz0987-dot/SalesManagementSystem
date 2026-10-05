package com.swp391.repository;

import com.swp391.model.Product;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(int id);
    void save(Product product);
    void delete(int id);
}
