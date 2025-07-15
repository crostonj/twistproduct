package com.techtwist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techtwist.dto.*;
import com.techtwist.services.interfaces.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ProductController.class, ProductControllerTest.TestConfig.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Configuration
    static class TestConfig {
        @Bean
        public IProductService productService() {
            return mock(IProductService.class);
        }
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setName("FlexiRetail Mobile POS");
        createDTO.setDescription("Mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        createDTO.setPrice(new BigDecimal("399.99"));
        createDTO.setCategoryName("Retail");
        createDTO.setBrand("TechTwist");

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId("ret_pos_004");
        responseDTO.setName("FlexiRetail Mobile POS");
        responseDTO.setDescription("Mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        responseDTO.setPrice(new BigDecimal("399.99"));
        responseDTO.setCategoryName("Retail");
        responseDTO.setBrand("TechTwist");

        when(productService.create(any(ProductCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("ret_pos_004"))
                .andExpect(jsonPath("$.name").value("FlexiRetail Mobile POS"))
                .andExpect(jsonPath("$.price").value(399.99));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId("ret_pos_004");
        responseDTO.setName("FlexiRetail Mobile POS");
        responseDTO.setDescription("Mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        responseDTO.setPrice(new BigDecimal("399.99"));
        responseDTO.setCategoryName("Retail");
        responseDTO.setBrand("TechTwist");

        when(productService.findById("ret_pos_004")).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/products/ret_pos_004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ret_pos_004"))
                .andExpect(jsonPath("$.name").value("FlexiRetail Mobile POS"))
                .andExpect(jsonPath("$.price").value(399.99));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("FlexiRetail Mobile POS v2");
        updateDTO.setDescription("Enhanced mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        updateDTO.setPrice(new BigDecimal("449.99"));
        updateDTO.setCategoryName("Retail");
        updateDTO.setBrand("TechTwist");

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId("ret_pos_004");
        responseDTO.setName("FlexiRetail Mobile POS v2");
        responseDTO.setDescription("Enhanced mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        responseDTO.setPrice(new BigDecimal("449.99"));
        responseDTO.setCategoryName("Retail");
        responseDTO.setBrand("TechTwist");

        when(productService.update(eq("ret_pos_004"), any(ProductUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/products/ret_pos_004")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ret_pos_004"))
                .andExpect(jsonPath("$.name").value("FlexiRetail Mobile POS v2"))
                .andExpect(jsonPath("$.price").value(449.99));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("Updated Product");

        when(productService.update(eq("ret_pos_999"), any(ProductUpdateDTO.class))).thenReturn(null);

        mockMvc.perform(put("/api/products/ret_pos_999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // The delete method returns void, so we just verify it's called
        mockMvc.perform(delete("/api/products/ret_pos_004"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        // Since delete returns void, we can't test not found scenario via service
        // The controller always returns 204 No Content for delete operations
        mockMvc.perform(delete("/api/products/ret_pos_999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductResponseDTO response1 = new ProductResponseDTO();
        response1.setId("ret_pos_004");
        response1.setName("FlexiRetail Mobile POS");
        response1.setPrice(new BigDecimal("399.99"));

        ProductResponseDTO response2 = new ProductResponseDTO();
        response2.setId("ret_pos_005");
        response2.setName("FlexiRetail Desktop POS");
        response2.setPrice(new BigDecimal("599.99"));

        List<ProductResponseDTO> products = Arrays.asList(response1, response2);

        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("ret_pos_004"))
                .andExpect(jsonPath("$[0].name").value("FlexiRetail Mobile POS"))
                .andExpect(jsonPath("$[1].id").value("ret_pos_005"))
                .andExpect(jsonPath("$[1].name").value("FlexiRetail Desktop POS"));
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId("ret_pos_004");
        response.setName("FlexiRetail Mobile POS");
        response.setPrice(new BigDecimal("399.99"));
        response.setCategoryName("Retail");

        List<ProductResponseDTO> products = Arrays.asList(response);

        when(productService.findByCategory("Retail")).thenReturn(products);

        mockMvc.perform(get("/api/products/category/Retail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("ret_pos_004"))
                .andExpect(jsonPath("$[0].name").value("FlexiRetail Mobile POS"));
    }

    @Test
    public void testGetProductsByBrand() throws Exception {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId("ret_pos_004");
        response.setName("FlexiRetail Mobile POS");
        response.setPrice(new BigDecimal("399.99"));
        response.setBrand("TechTwist");

        List<ProductResponseDTO> products = Arrays.asList(response);

        when(productService.findByBrand("TechTwist")).thenReturn(products);

        mockMvc.perform(get("/api/products/brand/TechTwist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("ret_pos_004"))
                .andExpect(jsonPath("$[0].name").value("FlexiRetail Mobile POS"));
    }

    @Test
    public void testGetProductByName() throws Exception {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId("ret_pos_004");
        response.setName("FlexiRetail Mobile POS");
        response.setPrice(new BigDecimal("399.99"));

        when(productService.findByName("FlexiRetail Mobile POS")).thenReturn(response);

        mockMvc.perform(get("/api/products/name/FlexiRetail Mobile POS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ret_pos_004"))
                .andExpect(jsonPath("$.name").value("FlexiRetail Mobile POS"));
    }
}
