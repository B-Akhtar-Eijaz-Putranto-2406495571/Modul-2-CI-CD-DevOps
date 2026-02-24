package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homePage"));
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("createProduct"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Sampo Cap Bambang")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product = new Product();
        List<Product> allProducts = Arrays.asList(product);

        when(productService.findAll()).thenReturn(allProducts);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", allProducts))
                .andExpect(view().name("productList"));
    }

    @Test
    void testEditProductPage() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = new Product();

        when(productService.findById(id)).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("product", product))
                .andExpect(view().name("editProduct"));
    }

    @Test
    void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", UUID.randomUUID().toString())
                        .param("productName", "Sampo Cap Usep"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));

        verify(productService, times(1)).edit(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/product/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:../list"));

        verify(productService, times(1)).deleteProductById(id);
    }
}