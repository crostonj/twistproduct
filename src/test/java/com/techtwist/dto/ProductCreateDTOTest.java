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
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductCreateDTO
 */
public class ProductCreateDTOTest {

    private ObjectMapper objectMapper;
    private ProductCreateDTO productCreateDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        productCreateDTO = new ProductCreateDTO();
    }

    @Test
    void testDefaultConstructor() {
        ProductCreateDTO dto = new ProductCreateDTO();
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        // Basic fields
        productCreateDTO.setName("FlexiRetail Mobile POS");
        productCreateDTO.setBrand("TechTwist");
        productCreateDTO.setPrice(new BigDecimal("399.99"));
        productCreateDTO.setDescription("Mobile POS solution with tablet and wireless payment processing");
        productCreateDTO.setImageUrl("ttp4.png");
        productCreateDTO.setCategory("retail");
        productCreateDTO.setCategoryName("Retail");
        productCreateDTO.setProductArea("pos_systems");
        productCreateDTO.setProductAreaName("Retail POS Systems");

        // Advanced fields
        productCreateDTO.setStockQuantity(50);
        productCreateDTO.setSku("RET_POS_004");
        productCreateDTO.setStatus("ACTIVE");
        productCreateDTO.setFeatured(true);
        productCreateDTO.setWeight(1.2);
        productCreateDTO.setDimensions("10.1 x 7.5 x 0.5 inches");
        productCreateDTO.setWarranty("2 years");
        productCreateDTO.setManufacturer("TechTwist Manufacturing");

        // Collections
        List<String> features = Arrays.asList("Wireless operation", "Tablet-based", "Mobile payments", "Cloud sync");
        productCreateDTO.setFeatures(features);

        Map<String, String> specifications = new HashMap<>();
        specifications.put("screen", "10.1 inch tablet");
        specifications.put("connectivity", "WiFi, 4G LTE");
        specifications.put("battery", "8 hours");
        specifications.put("weight", "1.2 lbs");
        productCreateDTO.setSpecifications(specifications);

        List<String> tags = Arrays.asList("pos", "retail", "mobile", "payment");
        productCreateDTO.setTags(tags);

        // Assertions
        assertThat(productCreateDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(productCreateDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(productCreateDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(productCreateDTO.getDescription()).isEqualTo("Mobile POS solution with tablet and wireless payment processing");
        assertThat(productCreateDTO.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(productCreateDTO.getCategory()).isEqualTo("retail");
        assertThat(productCreateDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(productCreateDTO.getProductArea()).isEqualTo("pos_systems");
        assertThat(productCreateDTO.getProductAreaName()).isEqualTo("Retail POS Systems");
        assertThat(productCreateDTO.getStockQuantity()).isEqualTo(50);
        assertThat(productCreateDTO.getSku()).isEqualTo("RET_POS_004");
        assertThat(productCreateDTO.getStatus()).isEqualTo("ACTIVE");
        assertThat(productCreateDTO.getFeatured()).isTrue();
        assertThat(productCreateDTO.getWeight()).isEqualTo(1.2);
        assertThat(productCreateDTO.getDimensions()).isEqualTo("10.1 x 7.5 x 0.5 inches");
        assertThat(productCreateDTO.getWarranty()).isEqualTo("2 years");
        assertThat(productCreateDTO.getManufacturer()).isEqualTo("TechTwist Manufacturing");
        assertThat(productCreateDTO.getFeatures()).hasSize(4).contains("Wireless operation", "Mobile payments");
        assertThat(productCreateDTO.getSpecifications()).hasSize(4).containsKey("screen").containsValue("10.1 inch tablet");
        assertThat(productCreateDTO.getTags()).hasSize(4).contains("pos", "retail");
    }

    @Test
    void testJsonSerialization() throws Exception {
        // Arrange
        productCreateDTO.setName("FlexiRetail Mobile POS");
        productCreateDTO.setBrand("TechTwist");
        productCreateDTO.setPrice(new BigDecimal("399.99"));
        productCreateDTO.setCategoryName("Retail");
        productCreateDTO.setProductArea("pos_systems");

        // Act
        String json = objectMapper.writeValueAsString(productCreateDTO);

        // Assert
        assertThat(json).contains("\"name\":\"FlexiRetail Mobile POS\"");
        assertThat(json).contains("\"brand\":\"TechTwist\"");
        assertThat(json).contains("\"price\":399.99");
        assertThat(json).contains("\"categoryName\":\"Retail\"");
        assertThat(json).contains("\"productArea\":\"pos_systems\"");
    }

    @Test
    void testJsonDeserialization() throws Exception {
        // Arrange
        String json = "{" +
                "\"name\":\"FlexiRetail Mobile POS\"," +
                "\"brand\":\"TechTwist\"," +
                "\"price\":399.99," +
                "\"categoryName\":\"Retail\"," +
                "\"productArea\":\"pos_systems\"," +
                "\"features\":[\"Wireless operation\",\"Mobile payments\"]," +
                "\"specifications\":{\"screen\":\"10.1 inch tablet\"}" +
                "}";

        // Act
        ProductCreateDTO dto = objectMapper.readValue(json, ProductCreateDTO.class);

        // Assert
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isEqualTo("TechTwist");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getProductArea()).isEqualTo("pos_systems");
        assertThat(dto.getFeatures()).hasSize(2).contains("Wireless operation", "Mobile payments");
        assertThat(dto.getSpecifications()).containsKey("screen").containsValue("10.1 inch tablet");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProductCreateDTO dto1 = new ProductCreateDTO();
        dto1.setName("Test Product");
        dto1.setBrand("Test Brand");
        dto1.setPrice(new BigDecimal("100.00"));

        ProductCreateDTO dto2 = new ProductCreateDTO();
        dto2.setName("Test Product");
        dto2.setBrand("Test Brand");
        dto2.setPrice(new BigDecimal("100.00"));

        ProductCreateDTO dto3 = new ProductCreateDTO();
        dto3.setName("Different Product");
        dto3.setBrand("Test Brand");
        dto3.setPrice(new BigDecimal("100.00"));

        // Act & Assert
        assertThat(dto1).isEqualTo(dto1); // reflexive
        assertThat(dto1).isNotEqualTo(dto3); // different name
        assertThat(dto1).isNotEqualTo(null);
        assertThat(dto1).isNotEqualTo("not a DTO");
    }

    @Test
    void testToString() {
        // Arrange
        productCreateDTO.setName("FlexiRetail Mobile POS");
        productCreateDTO.setBrand("TechTwist");
        productCreateDTO.setPrice(new BigDecimal("399.99"));

        // Act
        String toString = productCreateDTO.toString();

        // Assert
        assertThat(toString).isNotNull();
        assertThat(toString).contains("ProductCreateDTO");
        assertThat(toString).contains("FlexiRetail Mobile POS");
        assertThat(toString).contains("TechTwist");
    }

    @Test
    void testValidationScenarios() {
        // Test with minimal required fields
        ProductCreateDTO minimalDto = new ProductCreateDTO();
        minimalDto.setName("Minimal Product");
        minimalDto.setPrice(new BigDecimal("10.00"));

        assertThat(minimalDto.getName()).isEqualTo("Minimal Product");
        assertThat(minimalDto.getPrice()).isEqualTo(new BigDecimal("10.00"));
        assertThat(minimalDto.getBrand()).isNull();
        assertThat(minimalDto.getDescription()).isNull();

        // Test with empty collections
        minimalDto.setFeatures(Arrays.asList());
        minimalDto.setSpecifications(new HashMap<>());
        minimalDto.setTags(Arrays.asList());

        assertThat(minimalDto.getFeatures()).isEmpty();
        assertThat(minimalDto.getSpecifications()).isEmpty();
        assertThat(minimalDto.getTags()).isEmpty();
    }

    @Test
    void testNullHandling() {
        ProductCreateDTO dto = new ProductCreateDTO();
        
        // Set some fields to null explicitly
        dto.setName(null);
        dto.setBrand(null);
        dto.setPrice(null);
        dto.setFeatures(null);
        dto.setSpecifications(null);

        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
        assertThat(dto.getFeatures()).isNull();
        assertThat(dto.getSpecifications()).isNull();
    }

    @Test
    void testBigDecimalPrecision() {
        // Test precise decimal handling
        BigDecimal precisePrice = new BigDecimal("399.999");
        productCreateDTO.setPrice(precisePrice);
        
        assertThat(productCreateDTO.getPrice()).isEqualTo(precisePrice);
        assertThat(productCreateDTO.getPrice().scale()).isEqualTo(3);

        // Test currency precision
        BigDecimal currencyPrice = new BigDecimal("399.99");
        productCreateDTO.setPrice(currencyPrice);
        
        assertThat(productCreateDTO.getPrice()).isEqualTo(currencyPrice);
        assertThat(productCreateDTO.getPrice().scale()).isEqualTo(2);
    }
}
