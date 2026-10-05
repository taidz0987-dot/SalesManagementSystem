package com.swp391.service;

import com.swp391.model.Product;
import com.swp391.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void addProduct_validProduct_savesAndReturnsProduct() {
        Product p = new Product(1, "Keyboard", 50, 10);
        when(repository.findById(1)).thenReturn(Optional.empty());

        Product result = service.addProduct(p);

        assertSame(p, result);
        verify(repository).save(p);
    }

    @Test
    void addProduct_duplicateId_throwsException() {
        Product p = new Product(1, "Keyboard", 50, 10);
        when(repository.findById(1)).thenReturn(Optional.of(p));

        assertThrows(IllegalStateException.class, () -> service.addProduct(p));
        verify(repository, never()).save(any());
    }

    @Test
    void findProduct_existingId_returnsProduct() {
        Product p = new Product(2, "Mouse", 20, 5);
        when(repository.findById(2)).thenReturn(Optional.of(p));

        assertEquals("Mouse", service.findProductById(2).getName());
    }

    @Test
    void findProduct_missingId_throwsException() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.findProductById(99));
    }

    @Test
    void updateProduct_validData_updatesAllFields() {
        Product p = new Product(3, "Old", 10, 2);
        when(repository.findById(3)).thenReturn(Optional.of(p));

        Product result = service.updateProduct(3, "New", 99, 20);

        assertAll(
            () -> assertEquals("New", result.getName()),
            () -> assertEquals(99, result.getPrice()),
            () -> assertEquals(20, result.getStock())
        );
        verify(repository).save(p);
    }

    @Test
    void updateProduct_missingProduct_throwsException() {
        when(repository.findById(4)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> service.updateProduct(4, "New", 10, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void updateProduct_blankName_throwsException(String name) {
        Product p = new Product(5, "Phone", 100, 1);
        when(repository.findById(5)).thenReturn(Optional.of(p));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateProduct(5, name, 10, 1));
    }

    @Test
    void updateProduct_negativePrice_throwsException() {
        Product p = new Product(6, "Phone", 100, 1);
        when(repository.findById(6)).thenReturn(Optional.of(p));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateProduct(6, "Phone", -1, 1));
    }

    @Test
    void deleteProduct_existingProduct_deletes() {
        Product p = new Product(7, "Tablet", 200, 3);
        when(repository.findById(7)).thenReturn(Optional.of(p));

        service.deleteProduct(7);

        verify(repository).delete(7);
    }

    @Test
    void deleteProduct_missingProduct_doesNotDelete() {
        when(repository.findById(8)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.deleteProduct(8));
        verify(repository, never()).delete(anyInt());
    }

@Test
void updateProduct_negativeStock_throwsException() {
    Product p = new Product(9, "Camera", 100, 1);
    when(repository.findById(9)).thenReturn(Optional.of(p));
    assertThrows(IllegalArgumentException.class,
            () -> service.updateProduct(9, "Camera", 100, -1));
}

@Test
void addProduct_callsRepositoryOnlyOnce() {
    Product p = new Product(10, "Headset", 30, 4);
    when(repository.findById(10)).thenReturn(Optional.empty());

    service.addProduct(p);

    verify(repository, times(1)).save(p);
    verify(repository, times(1)).findById(10);
}

}
