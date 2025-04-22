package com.techtwist.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techtwist.models.Product;
import com.techtwist.services.TableProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class) // Explicitly specify the controller
class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TableProductService productService; // Use @MockBean to provide a mock in the application context

    @Autowired
    private ProductController productController; // Autowire the controller from the Spring context

    private String rowKey;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build(); // Use the autowired controller
        rowKey = UUID.randomUUID().toString();
    }

    private Product createTestProduct() {
        return new Product("Product1", 10.0, "Product Description", "partition1", rowKey);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = createTestProduct();

        // Ensure the mock returns a valid Product object
        Mockito.when(productService.create(Mockito.any())).thenReturn(product);

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) // Specify the expected response type
                .content(objectMapper.writeValueAsString(product)))
            .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log response
            .andExpect(status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString())) // Log response
            .andExpect(jsonPath("$.partitionKey").value(product.getPartitionKey())) // Verify JSON object properties
            .andExpect(jsonPath("$.rowKey").value(product.getRowKey()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    void testGetProduct() throws Exception {
        Product product = createTestProduct();

        // Ensure the mock returns a valid Product object
        when(productService.get(product.getPartitionKey(), product.getRowKey())).thenReturn(product);

        mockMvc.perform(get("/product/{partitionKey}/{rowKey}", product.getPartitionKey(), product.getRowKey())
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.partitionKey").value(product.getPartitionKey())) // Verify JSON object properties
            .andExpect(jsonPath("$.rowKey").value(product.getRowKey()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.price").value(product.getPrice()));

        verify(productService, times(1)).get(product.getPartitionKey(), product.getRowKey());
    }

    @Test
    void testGetByName() throws Exception {
        // Arrange
        Product product = new Product("MoneyIn", 100.0, "Product Description","partition1", "row1");
        when(productService.getByName("MoneyIn")).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/product/MoneyIn").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MoneyIn")).andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.partitionKey").value("partition1"))
                .andExpect(jsonPath("$.rowKey").value("row1"));
    }

    @Test
    void testGetByName_NotFound() throws Exception {
        // Arrange
        when(productService.getByName("NonExistent")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/products/NonExistent").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsertAndUpdateProduct() throws Exception {
        Product product = createTestProduct();

        // Insert the product
        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                        product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        // Update the product
        product.setName("UpdatedProduct");
        mockMvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                        product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        verify(productService, times(1)).update(product);
    }

    @Test
    void testInsertAndDeleteProduct() throws Exception {
        Product product = createTestProduct();

        // Insert the product first to ensure it exists before deletion
        mockMvc.perform(post("/product")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON) // Specify the expected response type
        .content(objectMapper.writeValueAsString(product)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log response
                .andExpect(status().isOk());

        // Delete the product
        mockMvc.perform(delete("/product", product.getPartitionKey(), product.getRowKey())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                        product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());

        verify(productService, times(1)).delete(product);
    }
}