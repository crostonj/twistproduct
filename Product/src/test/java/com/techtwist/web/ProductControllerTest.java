package com.techtwist.web;

import com.azure.data.tables.models.TableEntity;
import com.techtwist.services.ProductServce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductServce productServce;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProduct() throws Exception {
        String partitionKey = "partition1";
        String rowKey = "row1";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Product1");

        mockMvc.perform(post("/api/products")
                        .param("partitionKey", partitionKey)
                        .param("rowKey", rowKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\"}"))
                .andExpect(status().isOk());

        verify(productServce, times(1)).createEntity(partitionKey, rowKey, properties);
    }

    @Test
    void testGetProduct() throws Exception {
        String partitionKey = "partition1";
        String rowKey = "row1";
        TableEntity expectedEntity = new TableEntity(partitionKey, rowKey);
        when(productServce.readEntity(partitionKey, rowKey)).thenReturn(expectedEntity);

        mockMvc.perform(get("/api/products/{partitionKey}/{rowKey}", partitionKey, rowKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partitionKey").value(partitionKey))
                .andExpect(jsonPath("$.rowKey").value(rowKey));

        verify(productServce, times(1)).readEntity(partitionKey, rowKey);
    }

    @Test
    void testUpdateProduct() throws Exception {
        String partitionKey = "partition1";
        String rowKey = "row1";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "UpdatedProduct");

        mockMvc.perform(put("/api/products/{partitionKey}/{rowKey}", partitionKey, rowKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedProduct\"}"))
                .andExpect(status().isOk());

        verify(productServce, times(1)).updateEntity(partitionKey, rowKey, properties);
    }

    @Test
    void testDeleteProduct() throws Exception {
        String partitionKey = "partition1";
        String rowKey = "row1";

        mockMvc.perform(delete("/api/products/{partitionKey}/{rowKey}", partitionKey, rowKey))
                .andExpect(status().isOk());

        verify(productServce, times(1)).deleteEntity(partitionKey, rowKey);
    }
}