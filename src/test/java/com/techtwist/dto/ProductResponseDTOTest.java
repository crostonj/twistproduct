package com.techtwist.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ProductResponseDTO
 */
public class ProductResponseDTOTest {

    private ObjectMapper objectMapper;
    private ProductResponseDTO productResponseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        productResponseDTO = new ProductResponseDTO();
    }

    @Test
    void testDefaultConstructor() {
        ProductResponseDTO dto = new ProductResponseDTO();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        // Basic fields
        productResponseDTO.setId("ret_pos_004");
        productResponseDTO.setName("FlexiRetail Mobile POS");
        productResponseDTO.setBrand("TechTwist");
        productResponseDTO.setPrice(new BigDecimal("399.99"));
        productResponseDTO.setDescription("Mobile POS solution with tablet and wireless payment processing");
        productResponseDTO.setImageUrl("ttp4.png");
        productResponseDTO.setCategory("retail");
        productResponseDTO.setCategoryName("Retail");
        productResponseDTO.setProductArea("pos_systems");
        productResponseDTO.setProductAreaName("Retail POS Systems");

        // Advanced fields
        productResponseDTO.setStockQuantity(50);
        productResponseDTO.setSku("RET_POS_004");
        productResponseDTO.setStatus("ACTIVE");
        productResponseDTO.setFeatured(true);
        productResponseDTO.setWeight(1.2);
        productResponseDTO.setDimensions("10.1 x 7.5 x 0.5 inches");
        productResponseDTO.setWarranty("2 years");
        productResponseDTO.setManufacturer("TechTwist Manufacturing");
        productResponseDTO.setCreatedAt("2024-01-15T10:30:00Z");
        productResponseDTO.setUpdatedAt("2024-01-15T10:30:00Z");

        // Collections
        List<String> features = Arrays.asList("Wireless operation", "Tablet-based", "Mobile payments", "Cloud sync");
        productResponseDTO.setFeatures(features);

        Map<String, String> specifications = new HashMap<>();
        specifications.put("screen", "10.1 inch tablet");
        specifications.put("connectivity", "WiFi, 4G LTE");
        specifications.put("battery", "8 hours");
        specifications.put("weight", "1.2 lbs");
        productResponseDTO.setSpecifications(specifications);

        List<String> tags = Arrays.asList("pos", "retail", "mobile", "payment");
        productResponseDTO.setTags(tags);

        // Assertions
        assertThat(productResponseDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(productResponseDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(productResponseDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(productResponseDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(productResponseDTO.getDescription()).isEqualTo("Mobile POS solution with tablet and wireless payment processing");
        assertThat(productResponseDTO.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(productResponseDTO.getCategory()).isEqualTo("retail");
        assertThat(productResponseDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(productResponseDTO.getProductArea()).isEqualTo("pos_systems");
        assertThat(productResponseDTO.getProductAreaName()).isEqualTo("Retail POS Systems");
        assertThat(productResponseDTO.getStockQuantity()).isEqualTo(50);
        assertThat(productResponseDTO.getSku()).isEqualTo("RET_POS_004");
        assertThat(productResponseDTO.getStatus()).isEqualTo("ACTIVE");
        assertThat(productResponseDTO.getFeatured()).isTrue();
        assertThat(productResponseDTO.getWeight()).isEqualTo(1.2);
        assertThat(productResponseDTO.getDimensions()).isEqualTo("10.1 x 7.5 x 0.5 inches");
        assertThat(productResponseDTO.getWarranty()).isEqualTo("2 years");
        assertThat(productResponseDTO.getManufacturer()).isEqualTo("TechTwist Manufacturing");
        assertThat(productResponseDTO.getCreatedAt()).isEqualTo("2024-01-15T10:30:00Z");
        assertThat(productResponseDTO.getUpdatedAt()).isEqualTo("2024-01-15T10:30:00Z");
        assertThat(productResponseDTO.getFeatures()).hasSize(4).contains("Wireless operation", "Mobile payments");
        assertThat(productResponseDTO.getSpecifications()).hasSize(4).containsKey("screen").containsValue("10.1 inch tablet");
        assertThat(productResponseDTO.getTags()).hasSize(4).contains("pos", "retail");
    }

    @Test
    void testComputedFields_InStock() {
        // Test in stock scenarios
        productResponseDTO.setStockQuantity(10);
        assertThat(productResponseDTO.isInStock()).isTrue();

        productResponseDTO.setStockQuantity(1);
        assertThat(productResponseDTO.isInStock()).isTrue();

        // Test out of stock scenarios
        productResponseDTO.setStockQuantity(0);
        assertThat(productResponseDTO.isInStock()).isFalse();

        productResponseDTO.setStockQuantity(null);
        assertThat(productResponseDTO.isInStock()).isFalse();
    }

    @Test
    void testComputedFields_StockStatus() {
        // Test IN_STOCK status
        productResponseDTO.setStockQuantity(10);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");

        productResponseDTO.setStockQuantity(100);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");

        // Test LOW_STOCK status
        productResponseDTO.setStockQuantity(4);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("LOW_STOCK");

        productResponseDTO.setStockQuantity(1);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("LOW_STOCK");

        // Test OUT_OF_STOCK status
        productResponseDTO.setStockQuantity(0);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("OUT_OF_STOCK");

        productResponseDTO.setStockQuantity(null);
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("OUT_OF_STOCK");
    }

    @Test
    void testJsonSerialization() throws Exception {
        // Arrange
        productResponseDTO.setId("ret_pos_004");
        productResponseDTO.setName("FlexiRetail Mobile POS");
        productResponseDTO.setBrand("TechTwist");
        productResponseDTO.setPrice(new BigDecimal("399.99"));
        productResponseDTO.setCategoryName("Retail");
        productResponseDTO.setProductArea("pos_systems");
        productResponseDTO.setStockQuantity(10);

        // Act
        String json = objectMapper.writeValueAsString(productResponseDTO);

        // Assert
        assertThat(json).contains("\"id\":\"ret_pos_004\"");
        assertThat(json).contains("\"name\":\"FlexiRetail Mobile POS\"");
        assertThat(json).contains("\"brand\":\"TechTwist\"");
        assertThat(json).contains("\"price\":399.99");
        assertThat(json).contains("\"categoryName\":\"Retail\"");
        assertThat(json).contains("\"productArea\":\"pos_systems\"");
        assertThat(json).contains("\"inStock\":true");
        assertThat(json).contains("\"stockStatus\":\"IN_STOCK\"");
    }

    @Test
    void testJsonDeserialization() throws Exception {
        // Arrange
        String json = "{" +
                "\"id\":\"ret_pos_004\"," +
                "\"name\":\"FlexiRetail Mobile POS\"," +
                "\"brand\":\"TechTwist\"," +
                "\"price\":399.99," +
                "\"categoryName\":\"Retail\"," +
                "\"productArea\":\"pos_systems\"," +
                "\"stockQuantity\":10," +
                "\"features\":[\"Wireless operation\",\"Mobile payments\"]," +
                "\"specifications\":{\"screen\":\"10.1 inch tablet\"}," +
                "\"createdAt\":\"2024-01-15T10:30:00Z\"" +
                "}";

        // Act
        ProductResponseDTO dto = objectMapper.readValue(json, ProductResponseDTO.class);

        // Assert
        assertThat(dto.getId()).isEqualTo("ret_pos_004");
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isEqualTo("TechTwist");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getProductArea()).isEqualTo("pos_systems");
        assertThat(dto.getStockQuantity()).isEqualTo(10);
        assertThat(dto.getFeatures()).hasSize(2).contains("Wireless operation", "Mobile payments");
        assertThat(dto.getSpecifications()).containsKey("screen").containsValue("10.1 inch tablet");
        assertThat(dto.getCreatedAt()).isEqualTo("2024-01-15T10:30:00Z");
        assertThat(dto.isInStock()).isTrue();
        assertThat(dto.getStockStatus()).isEqualTo("IN_STOCK");
    }

    @Test
    void testToString() {
        // Arrange
        productResponseDTO.setId("ret_pos_004");
        productResponseDTO.setName("FlexiRetail Mobile POS");
        productResponseDTO.setBrand("TechTwist");
        productResponseDTO.setPrice(new BigDecimal("399.99"));

        // Act
        String toString = productResponseDTO.toString();

        // Assert
        assertThat(toString).isNotNull();
        assertThat(toString).contains("ProductResponseDTO");
        assertThat(toString).contains("ret_pos_004");
        assertThat(toString).contains("FlexiRetail Mobile POS");
        assertThat(toString).contains("TechTwist");
    }

    @Test
    void testCompleteProductScenario() {
        // Arrange - Create a complete product response
        productResponseDTO.setId("ret_pos_004");
        productResponseDTO.setName("FlexiRetail Mobile POS");
        productResponseDTO.setBrand("TechTwist");
        productResponseDTO.setPrice(new BigDecimal("399.99"));
        productResponseDTO.setDescription("Mobile POS solution with tablet and wireless payment processing for flexible retail operations.");
        productResponseDTO.setImageUrl("ttp4.png");
        productResponseDTO.setCategory("retail");
        productResponseDTO.setCategoryName("Retail");
        productResponseDTO.setProductArea("pos_systems");
        productResponseDTO.setProductAreaName("Retail POS Systems");
        productResponseDTO.setStockQuantity(25);
        productResponseDTO.setSku("RET_POS_004");
        productResponseDTO.setStatus("ACTIVE");
        productResponseDTO.setFeatured(true);
        productResponseDTO.setWeight(1.2);
        productResponseDTO.setDimensions("10.1 x 7.5 x 0.5 inches");
        productResponseDTO.setWarranty("2 years");
        productResponseDTO.setManufacturer("TechTwist Manufacturing");

        List<String> features = Arrays.asList("Wireless operation", "Tablet-based", "Mobile payments", "Cloud sync");
        productResponseDTO.setFeatures(features);

        Map<String, String> specifications = new HashMap<>();
        specifications.put("screen", "10.1 inch tablet");
        specifications.put("connectivity", "WiFi, 4G LTE");
        specifications.put("battery", "8 hours");
        specifications.put("weight", "1.2 lbs");
        productResponseDTO.setSpecifications(specifications);

        List<String> tags = Arrays.asList("pos", "retail", "mobile", "payment");
        productResponseDTO.setTags(tags);

        productResponseDTO.setCreatedAt("2024-01-15T10:30:00Z");
        productResponseDTO.setUpdatedAt("2024-01-15T12:45:00Z");

        // Act & Assert - Verify all fields are set correctly
        assertThat(productResponseDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(productResponseDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(productResponseDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(productResponseDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(productResponseDTO.isInStock()).isTrue();
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");
        assertThat(productResponseDTO.getFeatured()).isTrue();
        assertThat(productResponseDTO.getStatus()).isEqualTo("ACTIVE");

        // Verify computed fields work correctly
        assertThat(productResponseDTO.isInStock()).isTrue();
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");
    }

    @Test
    void testEdgeCasesAndBoundaryValues() {
        // Test boundary values for stock status
        productResponseDTO.setStockQuantity(5); // Exactly at the boundary
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");

        productResponseDTO.setStockQuantity(4); // Just below boundary
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("LOW_STOCK");

        // Test with negative stock (edge case)
        productResponseDTO.setStockQuantity(-1);
        assertThat(productResponseDTO.isInStock()).isFalse();
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("OUT_OF_STOCK");

        // Test with large stock numbers
        productResponseDTO.setStockQuantity(Integer.MAX_VALUE);
        assertThat(productResponseDTO.isInStock()).isTrue();
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("IN_STOCK");
    }

    @Test
    void testNullSafetyInComputedFields() {
        // Test that computed fields handle null stockQuantity gracefully
        productResponseDTO.setStockQuantity(null);
        
        assertThat(productResponseDTO.isInStock()).isFalse();
        assertThat(productResponseDTO.getStockStatus()).isEqualTo("OUT_OF_STOCK");
        
        // Verify no exceptions are thrown
        assertThat(productResponseDTO.isInStock()).isFalse();
        assertThat(productResponseDTO.getStockStatus()).isNotNull();
    }
}
