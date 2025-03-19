package com.techtwist.web;


import com.techtwist.models.Product;
import com.techtwist.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private String rowKey;

    @BeforeEach
    void setUp() {

        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
            rowKey = UUID.randomUUID().toString();
        } catch (Exception e) {
            // Handle exceptions during setup
            e.printStackTrace(); // Or log the exception
        }
    }

    private Product createTestProduct() {
        return new Product("Product1", 10.0, "partition1", rowKey);
    }

    @Test
    void testCreateProduct() throws Exception {
        // Given
        Product product = createTestProduct();

        // Mocking the service call
        when(productService.create(any(Product.class))).thenReturn(null); // Adjust return value if needed

        // When
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                                product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        // Then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productService, times(1)).create(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(product.getName(), capturedProduct.getName());
        assertEquals(product.getPrice(), capturedProduct.getPrice());
        assertEquals(product.getPartitionKey(), capturedProduct.getPartitionKey());
        assertEquals(product.getRowKey(), capturedProduct.getRowKey());
    }

    @Test
    void testGetProduct() throws Exception {
        Product product = createTestProduct();

        when(productService.get(product.getPartitionKey(), product.getRowKey())).thenReturn(product);

        mockMvc.perform(get("/api/products/{partitionKey}/{rowKey}", product.getPartitionKey(), product.getRowKey())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partitionKey").value(product.getPartitionKey()))
                .andExpect(jsonPath("$.rowKey").value(product.getRowKey()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));

        verify(productService, times(1)).get(product.getPartitionKey(), product.getRowKey());
    }

    @Test
    void testInsertAndUpdateProduct() throws Exception {
        Product product = createTestProduct();


        // Insert the product
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                                product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        // Update the product
        product.setName("UpdatedProduct");
        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                                product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        verify(productService, times(1)).update(product);
    }

    @Test
    void testInsertAndDeleteProduct() throws Exception {
        Product product = createTestProduct();

        // Insert the product first to ensure it exists before deletion
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                                product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());


        // Delete the product
        mockMvc.perform(delete("/api/products", product.getPartitionKey(), product.getRowKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                        product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        verify(productService, times(1)).delete(product);
    }
}