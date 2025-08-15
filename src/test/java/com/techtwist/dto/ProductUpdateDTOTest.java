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
 * Unit tests for ProductUpdateDTO
 */
public class ProductUpdateDTOTest {

    private ObjectMapper objectMapper;
    private ProductUpdateDTO productUpdateDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        productUpdateDTO = new ProductUpdateDTO();
    }

    @Test
    void testDefaultConstructor() {
        ProductUpdateDTO dto = new ProductUpdateDTO();
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        // Basic fields
        productUpdateDTO.setName("FlexiRetail Mobile POS v2");
        productUpdateDTO.setBrand("TechTwist");
        productUpdateDTO.setPrice(new BigDecimal("449.99"));
        productUpdateDTO.setDescription("Enhanced mobile POS solution with improved features");
        productUpdateDTO.setImageUrl("ttp4_v2.png");
        productUpdateDTO.setCategory("retail");
        productUpdateDTO.setCategoryName("Retail");
        productUpdateDTO.setProductArea("pos_systems");
        productUpdateDTO.setProductAreaName("Retail POS Systems");

        // Advanced fields
        productUpdateDTO.setStockQuantity(75);
        productUpdateDTO.setSku("RET_POS_004_V2");
        productUpdateDTO.setStatus("ACTIVE");
        productUpdateDTO.setFeatured(true);
        productUpdateDTO.setWeight(1.1);
        productUpdateDTO.setDimensions("10.1 x 7.5 x 0.4 inches");
        productUpdateDTO.setWarranty("3 years");
        productUpdateDTO.setManufacturer("TechTwist Manufacturing");

        // Collections
        List<String> features = Arrays.asList("Wireless operation", "Tablet-based", "Mobile payments", "Cloud sync", "Enhanced security");
        productUpdateDTO.setFeatures(features);

        Map<String, String> specifications = new HashMap<>();
        specifications.put("screen", "10.1 inch tablet");
        specifications.put("connectivity", "WiFi, 4G LTE, 5G");
        specifications.put("battery", "10 hours");
        specifications.put("weight", "1.1 lbs");
        productUpdateDTO.setSpecifications(specifications);

        List<String> tags = Arrays.asList("pos", "retail", "mobile", "payment", "v2");
        productUpdateDTO.setTags(tags);

        // Assertions
        assertThat(productUpdateDTO.getName()).isEqualTo("FlexiRetail Mobile POS v2");
        assertThat(productUpdateDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(productUpdateDTO.getPrice()).isEqualTo(new BigDecimal("449.99"));
        assertThat(productUpdateDTO.getDescription()).isEqualTo("Enhanced mobile POS solution with improved features");
        assertThat(productUpdateDTO.getImageUrl()).isEqualTo("ttp4_v2.png");
        assertThat(productUpdateDTO.getCategory()).isEqualTo("retail");
        assertThat(productUpdateDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(productUpdateDTO.getProductArea()).isEqualTo("pos_systems");
        assertThat(productUpdateDTO.getProductAreaName()).isEqualTo("Retail POS Systems");
        assertThat(productUpdateDTO.getStockQuantity()).isEqualTo(75);
        assertThat(productUpdateDTO.getSku()).isEqualTo("RET_POS_004_V2");
        assertThat(productUpdateDTO.getStatus()).isEqualTo("ACTIVE");
        assertThat(productUpdateDTO.getFeatured()).isTrue();
        assertThat(productUpdateDTO.getWeight()).isEqualTo(1.1);
        assertThat(productUpdateDTO.getDimensions()).isEqualTo("10.1 x 7.5 x 0.4 inches");
        assertThat(productUpdateDTO.getWarranty()).isEqualTo("3 years");
        assertThat(productUpdateDTO.getManufacturer()).isEqualTo("TechTwist Manufacturing");
        assertThat(productUpdateDTO.getFeatures()).hasSize(5).contains("Enhanced security");
        assertThat(productUpdateDTO.getSpecifications()).hasSize(4).containsValue("WiFi, 4G LTE, 5G");
        assertThat(productUpdateDTO.getTags()).hasSize(5).contains("v2");
    }

    @Test
    void testPartialUpdate_OnlyName() {
        // Test updating only the name field (common scenario)
        productUpdateDTO.setName("Updated Product Name");
        
        assertThat(productUpdateDTO.getName()).isEqualTo("Updated Product Name");
        assertThat(productUpdateDTO.getBrand()).isNull();
        assertThat(productUpdateDTO.getPrice()).isNull();
        assertThat(productUpdateDTO.getDescription()).isNull();
        assertThat(productUpdateDTO.getStockQuantity()).isNull();
    }

    @Test
    void testPartialUpdate_PriceAndStock() {
        // Test updating price and stock quantity only
        productUpdateDTO.setPrice(new BigDecimal("299.99"));
        productUpdateDTO.setStockQuantity(100);
        
        assertThat(productUpdateDTO.getPrice()).isEqualTo(new BigDecimal("299.99"));
        assertThat(productUpdateDTO.getStockQuantity()).isEqualTo(100);
        assertThat(productUpdateDTO.getName()).isNull();
        assertThat(productUpdateDTO.getBrand()).isNull();
        assertThat(productUpdateDTO.getDescription()).isNull();
    }

    @Test
    void testPartialUpdate_StatusAndFeatured() {
        // Test updating status and featured flag
        productUpdateDTO.setStatus("INACTIVE");
        productUpdateDTO.setFeatured(false);
        
        assertThat(productUpdateDTO.getStatus()).isEqualTo("INACTIVE");
        assertThat(productUpdateDTO.getFeatured()).isFalse();
        assertThat(productUpdateDTO.getName()).isNull();
        assertThat(productUpdateDTO.getPrice()).isNull();
    }

    @Test
    void testJsonSerialization() throws Exception {
        // Arrange
        productUpdateDTO.setName("FlexiRetail Mobile POS v2");
        productUpdateDTO.setPrice(new BigDecimal("449.99"));
        productUpdateDTO.setCategoryName("Retail");
        productUpdateDTO.setStockQuantity(75);

        // Act
        String json = objectMapper.writeValueAsString(productUpdateDTO);

        // Assert
        assertThat(json).contains("\"name\":\"FlexiRetail Mobile POS v2\"");
        assertThat(json).contains("\"price\":449.99");
        assertThat(json).contains("\"categoryName\":\"Retail\"");
        assertThat(json).contains("\"stockQuantity\":75");
        
        // Should not contain null fields
        assertThat(json).doesNotContain("\"brand\":null");
        assertThat(json).doesNotContain("\"description\":null");
    }

    @Test
    void testJsonDeserialization() throws Exception {
        // Arrange - Partial update JSON (common scenario)
        String json = "{" +
                "\"name\":\"FlexiRetail Mobile POS v2\"," +
                "\"price\":449.99," +
                "\"stockQuantity\":75," +
                "\"features\":[\"Wireless operation\",\"Enhanced security\"]," +
                "\"specifications\":{\"battery\":\"10 hours\"}" +
                "}";

        // Act
        ProductUpdateDTO dto = objectMapper.readValue(json, ProductUpdateDTO.class);

        // Assert
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS v2");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("449.99"));
        assertThat(dto.getStockQuantity()).isEqualTo(75);
        assertThat(dto.getFeatures()).hasSize(2).contains("Enhanced security");
        assertThat(dto.getSpecifications()).containsKey("battery").containsValue("10 hours");
        
        // Fields not in JSON should be null
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getDescription()).isNull();
        assertThat(dto.getCategoryName()).isNull();
    }

    @Test
    void testJsonDeserializationEmptyJson() throws Exception {
        // Arrange - Empty JSON object
        String json = "{}";

        // Act
        ProductUpdateDTO dto = objectMapper.readValue(json, ProductUpdateDTO.class);

        // Assert - All fields should be null for partial update
        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
        assertThat(dto.getDescription()).isNull();
        assertThat(dto.getStockQuantity()).isNull();
        assertThat(dto.getFeatures()).isNull();
        assertThat(dto.getSpecifications()).isNull();
    }

    @Test
    void testToString() {
        // Arrange
        productUpdateDTO.setName("FlexiRetail Mobile POS v2");
        productUpdateDTO.setPrice(new BigDecimal("449.99"));
        productUpdateDTO.setStockQuantity(75);

        // Act
        String toString = productUpdateDTO.toString();

        // Assert
        assertThat(toString).isNotNull();
        assertThat(toString).contains("ProductUpdateDTO");
        assertThat(toString).contains("FlexiRetail Mobile POS v2");
        assertThat(toString).contains("449.99");
    }

    @Test
    void testUpdateScenarios() {
        // Scenario 1: Price update only
        ProductUpdateDTO priceUpdate = new ProductUpdateDTO();
        priceUpdate.setPrice(new BigDecimal("349.99"));
        
        assertThat(priceUpdate.getPrice()).isEqualTo(new BigDecimal("349.99"));
        assertThat(priceUpdate.getName()).isNull();

        // Scenario 2: Stock replenishment
        ProductUpdateDTO stockUpdate = new ProductUpdateDTO();
        stockUpdate.setStockQuantity(200);
        
        assertThat(stockUpdate.getStockQuantity()).isEqualTo(200);
        assertThat(stockUpdate.getPrice()).isNull();

        // Scenario 3: Feature enhancement
        ProductUpdateDTO featureUpdate = new ProductUpdateDTO();
        List<String> newFeatures = Arrays.asList("New feature 1", "New feature 2");
        featureUpdate.setFeatures(newFeatures);
        
        assertThat(featureUpdate.getFeatures()).hasSize(2).contains("New feature 1");
        assertThat(featureUpdate.getName()).isNull();

        // Scenario 4: Status change
        ProductUpdateDTO statusUpdate = new ProductUpdateDTO();
        statusUpdate.setStatus("DISCONTINUED");
        statusUpdate.setFeatured(false);
        
        assertThat(statusUpdate.getStatus()).isEqualTo("DISCONTINUED");
        assertThat(statusUpdate.getFeatured()).isFalse();
        assertThat(statusUpdate.getPrice()).isNull();
    }

    @Test
    void testCollectionUpdates() {
        // Test updating collections independently
        
        // Features update
        List<String> newFeatures = Arrays.asList("Feature A", "Feature B", "Feature C");
        productUpdateDTO.setFeatures(newFeatures);
        assertThat(productUpdateDTO.getFeatures()).hasSize(3).contains("Feature A", "Feature B", "Feature C");

        // Specifications update
        Map<String, String> newSpecs = new HashMap<>();
        newSpecs.put("newSpec1", "value1");
        newSpecs.put("newSpec2", "value2");
        productUpdateDTO.setSpecifications(newSpecs);
        assertThat(productUpdateDTO.getSpecifications()).hasSize(2).containsKey("newSpec1");

        // Tags update
        List<String> newTags = Arrays.asList("tag1", "tag2");
        productUpdateDTO.setTags(newTags);
        assertThat(productUpdateDTO.getTags()).hasSize(2).contains("tag1", "tag2");
    }

    @Test
    void testNullHandling() {
        // Test that DTO can handle null values for optional updates
        productUpdateDTO.setName(null);
        productUpdateDTO.setBrand(null);
        productUpdateDTO.setPrice(null);
        productUpdateDTO.setFeatures(null);
        productUpdateDTO.setSpecifications(null);
        productUpdateDTO.setStockQuantity(null);

        assertThat(productUpdateDTO.getName()).isNull();
        assertThat(productUpdateDTO.getBrand()).isNull();
        assertThat(productUpdateDTO.getPrice()).isNull();
        assertThat(productUpdateDTO.getFeatures()).isNull();
        assertThat(productUpdateDTO.getSpecifications()).isNull();
        assertThat(productUpdateDTO.getStockQuantity()).isNull();
    }

    @Test
    void testBooleanHandling() {
        // Test boolean field updates
        productUpdateDTO.setFeatured(true);
        assertThat(productUpdateDTO.getFeatured()).isTrue();

        productUpdateDTO.setFeatured(false);
        assertThat(productUpdateDTO.getFeatured()).isFalse();

        productUpdateDTO.setFeatured(null);
        assertThat(productUpdateDTO.getFeatured()).isNull();
    }

    @Test
    void testNumericFieldUpdates() {
        // Test different numeric field types
        productUpdateDTO.setPrice(new BigDecimal("999.99"));
        productUpdateDTO.setStockQuantity(500);
        productUpdateDTO.setWeight(2.5);

        assertThat(productUpdateDTO.getPrice()).isEqualTo(new BigDecimal("999.99"));
        assertThat(productUpdateDTO.getStockQuantity()).isEqualTo(500);
        assertThat(productUpdateDTO.getWeight()).isEqualTo(2.5);

        // Test zero values
        productUpdateDTO.setPrice(BigDecimal.ZERO);
        productUpdateDTO.setStockQuantity(0);
        productUpdateDTO.setWeight(0.0);

        assertThat(productUpdateDTO.getPrice()).isEqualTo(BigDecimal.ZERO);
        assertThat(productUpdateDTO.getStockQuantity()).isEqualTo(0);
        assertThat(productUpdateDTO.getWeight()).isEqualTo(0.0);
    }
}
