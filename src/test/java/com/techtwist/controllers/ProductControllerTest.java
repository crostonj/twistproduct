package com.techtwist.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.techtwist.helpers.ProductServiceTestHelper;
import com.techtwist.models.Product;
import com.techtwist.services.InMemoryProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class,
    excludeAutoConfiguration = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class}) // Explicitly specify the controller and exclude MongoDB auto-configuration
@AutoConfigureMockMvc(addFilters = false) // disables Spring Security filters
@Import({InMemoryProductService.class, ProductServiceTestHelper.class}) // Include InMemoryProductService and ProductServiceTestHelper in the test context
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc; // Autowire MockMvc provided by @WebMvcTest

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
        // With @WebMvcTest, MockMvc is auto-configured and injected.
        // The line below is not needed and can be removed.
        // mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        rowKey = UUID.randomUUID().toString();
    }

    private Product createTestProduct() {
        return new Product("1", "Product1", 10.0, "Product Description", "imugeurl", "category", "brand", rowKey, "partitionKey");
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
        Product product = new Product("1", "MoneyIn", 10.0, "Product Description", "imugeurl", "category", "brand", "partitionKey", rowKey);
        productServiceTestHelper.addProduct(product); // Use the helper to add the product to the in-memory store


        mockMvc.perform(get("/product/name/MoneyIn").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("MoneyIn"))
            .andExpect(jsonPath("$.price").value(10.0))
            .andExpect(jsonPath("$.partitionKey").value("partitionKey"))
            .andExpect(jsonPath("$.rowKey").value(rowKey));
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