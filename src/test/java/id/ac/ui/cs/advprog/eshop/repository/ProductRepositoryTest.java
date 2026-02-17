package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        Product product = new Product();
        UUID productId = UUID.randomUUID();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
        assertEquals("Sampo Cap Bambang", foundProduct.getProductName());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        UUID randomId = UUID.randomUUID();
        Product foundProduct = productRepository.findById(randomId);

        assertNull(foundProduct);
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        Product result = productRepository.edit(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Usep", result.getProductName());
        assertEquals(50, result.getProductQuantity());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Usep", savedProduct.getProductName());
        assertEquals(50, savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductIfNotFound() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(10);
        Product result = productRepository.edit(product);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product deletedProduct = productRepository.delete(product.getProductId());
        assertNotNull(deletedProduct);
        assertEquals(product.getProductId(), deletedProduct.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductIfNotFound() {
        UUID randomId = UUID.randomUUID();
        Product result = productRepository.delete(randomId);
        assertNull(result);
    }
}