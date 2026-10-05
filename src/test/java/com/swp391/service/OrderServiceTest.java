package com.swp391.service;

import com.swp391.model.Order;
import com.swp391.model.Product;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService service;

    @BeforeEach
    void setUp() { service = new OrderService(); }

    @Test
    void createOrder_validQuantity_reducesStockAndCreatesItem() {
        Product p = new Product(1, "Laptop", 1000, 10);
        Order order = service.createOrder(1, p, 3);

        assertAll(
            () -> assertEquals(7, p.getStock()),
            () -> assertEquals(1, order.getItems().size()),
            () -> assertEquals(3, order.getItems().get(0).getQuantity())
        );
    }

    @Test
    void createOrder_quantityEqualsStock_allowsOrder() {
        Product p = new Product(2, "Phone", 500, 4);
        Order order = service.createOrder(2, p, 4);
        assertEquals(0, p.getStock());
        assertEquals(2000, service.calculateTotal(order));
    }

    @Test
    void createOrder_insufficientStock_throwsException() {
        Product p = new Product(3, "Monitor", 200, 2);
        assertThrows(IllegalStateException.class, () -> service.createOrder(3, p, 3));
        assertEquals(2, p.getStock());
    }

    @Test
    void createOrder_zeroQuantity_throwsException() {
        Product p = new Product(4, "Mouse", 20, 5);
        assertThrows(IllegalArgumentException.class, () -> service.createOrder(4, p, 0));
    }

    @Test
    void calculateTotal_multipleItems_returnsSum() {
        Product a = new Product(5, "A", 10, 10);
        Product b = new Product(6, "B", 25, 10);
        Order order = new Order(5);
        order.addItem(new com.swp391.model.OrderItem(a, 2));
        order.addItem(new com.swp391.model.OrderItem(b, 3));

        assertEquals(95, service.calculateTotal(order));
    }

    @Test
    void calculateTotal_cancelledOrder_throwsException() {
        Order order = new Order(6);
        order.cancel();
        assertThrows(IllegalStateException.class, () -> service.calculateTotal(order));
    }

    @Test
    void cancelOrder_restoresStockAndMarksCancelled() {
        Product p = new Product(7, "SSD", 80, 10);
        Order order = service.createOrder(7, p, 4);

        service.cancelOrder(order);

        assertAll(
            () -> assertTrue(order.isCancelled()),
            () -> assertEquals(10, p.getStock())
        );
    }

    @Test
    void cancelOrder_twice_throwsException() {
        Product p = new Product(8, "RAM", 40, 10);
        Order order = service.createOrder(8, p, 2);
        service.cancelOrder(order);

        assertThrows(IllegalStateException.class, () -> service.cancelOrder(order));
    }
}
