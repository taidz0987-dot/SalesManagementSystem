package com.swp391.model;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product is required");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return product.getPrice() * quantity; }
}
