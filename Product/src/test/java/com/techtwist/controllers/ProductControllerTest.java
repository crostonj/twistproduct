package com.techtwist.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techtwist.helpers.ProductServiceTestHelper;
import com.techtwist.models.Product;
import com.techtwist.services.InMemoryProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class) // Explicitly specify the controller
@Import({InMemoryProductService.class, ProductServiceTestHelper.class}) // Include InMemoryProductService and ProductServiceTestHelper in the test context
class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController productController; // Autowire the controller from the Spring context

    @Autowired
    private InMemoryProductService productService; // Use the actual InMemoryProductService

    @Autowired
    private ProductServiceTestHelper productServiceTestHelper; // Autowire the helper class

    private String rowKey;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build(); // Use the autowired controller
        rowKey = UUID.randomUUID().toString();
    }

    private Product createTestProduct() {
        return new Product(1, "Product1", 10.0, "Product Description", "partition1", rowKey);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = createTestProduct();

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) // Specify the expected response type
                .content(objectMapper.writeValueAsString(product)))
            .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.partitionKey").value(product.getPartitionKey())) // Verify JSON object properties
            .andExpect(jsonPath("$.rowKey").value(product.getRowKey()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    void testGetProduct() throws Exception {
        Product product = createTestProduct();

        productServiceTestHelper.addProduct(product); // Use the helper to add the product to the in-memory store

        productService.create(product); // Add the product to the in-memory store

        mockMvc.perform(get("/product/{partitionKey}/{rowKey}", product.getPartitionKey(), product.getRowKey())
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Log response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.partitionKey").value(product.getPartitionKey())) // Verify JSON object properties
            .andExpect(jsonPath("$.rowKey").value(product.getRowKey()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    void testGetByName() throws Exception {
        Product product = new Product(1, "MoneyIn", 100.0, "Product Description", "partition1", "row1");
        productServiceTestHelper.addProduct(product); // Use the helper to add the product to the in-memory store


        mockMvc.perform(get("/product/MoneyIn").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("MoneyIn"))
            .andExpect(jsonPath("$.price").value(100.0))
            .andExpect(jsonPath("$.partitionKey").value("partition1"))
            .andExpect(jsonPath("$.rowKey").value("row1"));
    }

    @Test
    void testGetByName_NotFound() throws Exception {
        mockMvc.perform(get("/product/NonExistent").accept(MediaType.APPLICATION_JSON))
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
    }

    @Test
    void testInsertAndDeleteProduct() throws Exception {
        Product product = createTestProduct();
        productServiceTestHelper.addProduct(product); // Use the helper to add the product to the in-memory store

        // Insert the product first to ensure it exists before deletion
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) // Specify the expected response type
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        // Delete the product
        mockMvc.perform(delete("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"price\":%s, \"partitionKey\":\"%s\", \"rowKey\":\"%s\"}",
                        product.getName(), product.getPrice(), product.getPartitionKey(), product.getRowKey())))
                .andExpect(status().isOk());
    }
}