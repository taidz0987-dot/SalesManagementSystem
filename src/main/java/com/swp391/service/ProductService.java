package com.swp391.service;

import com.swp391.model.Product;
import com.swp391.repository.ProductRepository;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(Product product) {
        if (repository.findById(product.getId()).isPresent()) {
            throw new IllegalStateException("Product already exists");
        }
        repository.save(product);
        return product;
    }

    public Product findProductById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Product updateProduct(int id, String name, double price, int stock) {
        Product product = findProductById(id);
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name is required");
        if (price < 0 || stock < 0) throw new IllegalArgumentException("Invalid product values");
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        repository.save(product);
        return product;
    }

    public void deleteProduct(int id) {
        findProductById(id);
        repository.delete(id);
    }
}
