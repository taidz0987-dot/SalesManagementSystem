package com.swp391.integration;

import com.swp391.model.*;
import com.swp391.repository.InMemoryProductRepository;
import com.swp391.service.OrderService;
import com.swp391.service.ProductService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalesManagementIntegrationTest {
    @Test
    void createProductThenCreateOrder_usesRealRepositoryAndServices() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);
        OrderService orderService = new OrderService();

        Product product = new Product(100, "Gaming Laptop", 1500, 5);
        productService.addProduct(product);

        Product loaded = productService.findProductById(100);
        Order order = orderService.createOrder(500, loaded, 2);

        assertAll(
            () -> assertEquals(2, order.getItems().size()),
            () -> assertEquals(3000, orderService.calculateTotal(order)),
            () -> assertEquals(3, productService.findProductById(100).getStock())
        );
    }

    @Test
    void updateThenDeleteProduct_repositoryStateChangesCorrectly() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        ProductService service = new ProductService(repository);
        service.addProduct(new Product(101, "Keyboard", 50, 10));

        service.updateProduct(101, "Mechanical Keyboard", 80, 7);
        assertEquals("Mechanical Keyboard", service.findProductById(101).getName());

        service.deleteProduct(101);
        assertThrows(IllegalArgumentException.class, () -> service.findProductById(101));
    }
}
