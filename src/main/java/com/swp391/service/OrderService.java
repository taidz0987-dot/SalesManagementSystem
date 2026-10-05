package com.swp391.service;

import com.swp391.model.Order;
import com.swp391.model.OrderItem;
import com.swp391.model.Product;

public class OrderService {
    public Order createOrder(int orderId, Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
        product.setStock(product.getStock() - quantity);
        Order order = new Order(orderId);
        order.addItem(new OrderItem(product, quantity));
        return order;
    }

    public double calculateTotal(Order order) {
        if (order.isCancelled()) throw new IllegalStateException("Cancelled order");
        return order.getItems().stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public void cancelOrder(Order order) {
        if (order.isCancelled()) throw new IllegalStateException("Order already cancelled");
        order.cancel();
        for (OrderItem item : order.getItems()) {
            item.getProduct().setStock(item.getProduct().getStock() + item.getQuantity());
        }
    }
}
