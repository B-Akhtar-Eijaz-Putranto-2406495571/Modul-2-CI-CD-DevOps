package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertEquals(product.getProductId(), createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product, product2);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(product.getProductName(), result.get(0).getProductName());
    }

    @Test
    void testFindById() {
        UUID id = product.getProductId();
        when(productRepository.findById(id)).thenReturn(product);

        Product foundProduct = productService.findById(id);

        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testEdit() {
        productService.edit(product);
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDeleteProductById() {
        UUID id = UUID.randomUUID();
        productService.deleteProductById(id);

        verify(productRepository, times(1)).delete(id);
    }
}