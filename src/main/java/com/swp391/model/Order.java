package com.swp391.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private final List<OrderItem> items = new ArrayList<>();
    private boolean cancelled;

    public Order(int id) {
        if (id <= 0) throw new IllegalArgumentException("Order id must be positive");
        this.id = id;
    }

    public int getId() { return id; }
    public List<OrderItem> getItems() { return List.copyOf(items); }
    public boolean isCancelled() { return cancelled; }
    public void addItem(OrderItem item) { items.add(item); }
    public void cancel() { cancelled = true; }
}
