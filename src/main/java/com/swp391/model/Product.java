package com.swp391.model;

public class Product {
    private final int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        if (id <= 0) throw new IllegalArgumentException("Product id must be positive");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name is required");
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
