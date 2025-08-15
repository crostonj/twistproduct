package com.techtwist.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ProductSummaryDTO
 */
public class ProductSummaryDTOTest {

    private ObjectMapper objectMapper;
    private ProductSummaryDTO productSummaryDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        productSummaryDTO = new ProductSummaryDTO();
    }

    @Test
    void testDefaultConstructor() {
        ProductSummaryDTO dto = new ProductSummaryDTO();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isNull();
        assertThat(dto.getStockQuantity()).isNull();
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String id = "ret_pos_004";
        String name = "FlexiRetail Mobile POS";
        String brand = "TechTwist";
        BigDecimal price = new BigDecimal("399.99");
        String imageUrl = "ttp4.png";
        String category = "retail";
        String categoryName = "Retail";
        Integer stockQuantity = 50;
        Boolean featured = true;
        String status = "ACTIVE";

        // Act
        ProductSummaryDTO dto = new ProductSummaryDTO(id, name, brand, price, imageUrl, 
                                                     category, categoryName, stockQuantity, 
                                                     featured, status);

        // Assert
        assertThat(dto.getId()).isEqualTo("ret_pos_004");
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isEqualTo("TechTwist");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(dto.getCategory()).isEqualTo("retail");
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getStockQuantity()).isEqualTo(50);
        assertThat(dto.getFeatured()).isTrue();
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void testSettersAndGetters() {
        // Act
        productSummaryDTO.setId("ret_pos_004");
        productSummaryDTO.setName("FlexiRetail Mobile POS");
        productSummaryDTO.setBrand("TechTwist");
        productSummaryDTO.setPrice(new BigDecimal("399.99"));
        productSummaryDTO.setImageUrl("ttp4.png");
        productSummaryDTO.setCategory("retail");
        productSummaryDTO.setCategoryName("Retail");
        productSummaryDTO.setStockQuantity(50);
        productSummaryDTO.setFeatured(true);
        productSummaryDTO.setStatus("ACTIVE");

        // Assert
        assertThat(productSummaryDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(productSummaryDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(productSummaryDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(productSummaryDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(productSummaryDTO.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(productSummaryDTO.getCategory()).isEqualTo("retail");
        assertThat(productSummaryDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(productSummaryDTO.getStockQuantity()).isEqualTo(50);
        assertThat(productSummaryDTO.getFeatured()).isTrue();
        assertThat(productSummaryDTO.getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void testComputedField_InStock() {
        // Test in stock scenarios
        productSummaryDTO.setStockQuantity(10);
        assertThat(productSummaryDTO.isInStock()).isTrue();

        productSummaryDTO.setStockQuantity(1);
        assertThat(productSummaryDTO.isInStock()).isTrue();

        // Test out of stock scenarios
        productSummaryDTO.setStockQuantity(0);
        assertThat(productSummaryDTO.isInStock()).isFalse();

        productSummaryDTO.setStockQuantity(null);
        assertThat(productSummaryDTO.isInStock()).isFalse();

        // Test negative stock (edge case)
        productSummaryDTO.setStockQuantity(-1);
        assertThat(productSummaryDTO.isInStock()).isFalse();
    }

    @Test
    void testJsonSerialization() throws Exception {
        // Arrange
        productSummaryDTO.setId("ret_pos_004");
        productSummaryDTO.setName("FlexiRetail Mobile POS");
        productSummaryDTO.setBrand("TechTwist");
        productSummaryDTO.setPrice(new BigDecimal("399.99"));
        productSummaryDTO.setImageUrl("ttp4.png");
        productSummaryDTO.setCategoryName("Retail");
        productSummaryDTO.setStockQuantity(10);
        productSummaryDTO.setFeatured(true);
        productSummaryDTO.setStatus("ACTIVE");

        // Act
        String json = objectMapper.writeValueAsString(productSummaryDTO);

        // Assert
        assertThat(json).contains("\"id\":\"ret_pos_004\"");
        assertThat(json).contains("\"name\":\"FlexiRetail Mobile POS\"");
        assertThat(json).contains("\"brand\":\"TechTwist\"");
        assertThat(json).contains("\"price\":399.99");
        assertThat(json).contains("\"imageUrl\":\"ttp4.png\"");
        assertThat(json).contains("\"categoryName\":\"Retail\"");
        assertThat(json).contains("\"stockQuantity\":10");
        assertThat(json).contains("\"featured\":true");
        assertThat(json).contains("\"status\":\"ACTIVE\"");
        assertThat(json).contains("\"inStock\":true");
    }

    @Test
    void testJsonDeserialization() throws Exception {
        // Arrange
        String json = "{" +
                "\"id\":\"ret_pos_004\"," +
                "\"name\":\"FlexiRetail Mobile POS\"," +
                "\"brand\":\"TechTwist\"," +
                "\"price\":399.99," +
                "\"imageUrl\":\"ttp4.png\"," +
                "\"category\":\"retail\"," +
                "\"categoryName\":\"Retail\"," +
                "\"stockQuantity\":10," +
                "\"featured\":true," +
                "\"status\":\"ACTIVE\"" +
                "}";

        // Act
        ProductSummaryDTO dto = objectMapper.readValue(json, ProductSummaryDTO.class);

        // Assert
        assertThat(dto.getId()).isEqualTo("ret_pos_004");
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isEqualTo("TechTwist");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(dto.getCategory()).isEqualTo("retail");
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getStockQuantity()).isEqualTo(10);
        assertThat(dto.getFeatured()).isTrue();
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
        assertThat(dto.isInStock()).isTrue();
    }

    @Test
    void testToString() {
        // Arrange
        productSummaryDTO.setId("ret_pos_004");
        productSummaryDTO.setName("FlexiRetail Mobile POS");
        productSummaryDTO.setBrand("TechTwist");
        productSummaryDTO.setPrice(new BigDecimal("399.99"));

        // Act
        String toString = productSummaryDTO.toString();

        // Assert
        assertThat(toString).isNotNull();
        assertThat(toString).contains("ProductSummaryDTO");
        assertThat(toString).contains("ret_pos_004");
        assertThat(toString).contains("FlexiRetail Mobile POS");
        assertThat(toString).contains("TechTwist");
    }

    @Test
    void testSummaryListScenarios() {
        // Test multiple products in a summary list format
        
        // Product 1 - In stock, featured
        ProductSummaryDTO product1 = new ProductSummaryDTO();
        product1.setId("ret_pos_004");
        product1.setName("FlexiRetail Mobile POS");
        product1.setBrand("TechTwist");
        product1.setPrice(new BigDecimal("399.99"));
        product1.setStockQuantity(25);
        product1.setFeatured(true);
        product1.setStatus("ACTIVE");

        // Product 2 - Out of stock, not featured
        ProductSummaryDTO product2 = new ProductSummaryDTO();
        product2.setId("ret_pos_005");
        product2.setName("FlexiRetail Desktop POS");
        product2.setBrand("TechTwist");
        product2.setPrice(new BigDecimal("599.99"));
        product2.setStockQuantity(0);
        product2.setFeatured(false);
        product2.setStatus("ACTIVE");

        // Product 3 - Low stock, featured
        ProductSummaryDTO product3 = new ProductSummaryDTO();
        product3.setId("ret_pos_006");
        product3.setName("FlexiRetail Compact POS");
        product3.setBrand("TechTwist");
        product3.setPrice(new BigDecimal("299.99"));
        product3.setStockQuantity(3);
        product3.setFeatured(true);
        product3.setStatus("ACTIVE");

        // Verify different stock statuses
        assertThat(product1.isInStock()).isTrue();
        assertThat(product2.isInStock()).isFalse();
        assertThat(product3.isInStock()).isTrue();

        // Verify featured flags
        assertThat(product1.getFeatured()).isTrue();
        assertThat(product2.getFeatured()).isFalse();
        assertThat(product3.getFeatured()).isTrue();

        // Verify all are active
        assertThat(product1.getStatus()).isEqualTo("ACTIVE");
        assertThat(product2.getStatus()).isEqualTo("ACTIVE");
        assertThat(product3.getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void testBoundaryValues() {
        // Test with minimum values
        productSummaryDTO.setStockQuantity(0);
        assertThat(productSummaryDTO.isInStock()).isFalse();

        productSummaryDTO.setStockQuantity(1);
        assertThat(productSummaryDTO.isInStock()).isTrue();

        // Test with large values
        productSummaryDTO.setStockQuantity(Integer.MAX_VALUE);
        assertThat(productSummaryDTO.isInStock()).isTrue();

        // Test with minimum price
        productSummaryDTO.setPrice(BigDecimal.ZERO);
        assertThat(productSummaryDTO.getPrice()).isEqualTo(BigDecimal.ZERO);

        // Test with very precise price
        BigDecimal precisePrice = new BigDecimal("399.999");
        productSummaryDTO.setPrice(precisePrice);
        assertThat(productSummaryDTO.getPrice()).isEqualTo(precisePrice);
    }

    @Test
    void testNullHandling() {
        // Test all null fields
        productSummaryDTO.setId(null);
        productSummaryDTO.setName(null);
        productSummaryDTO.setBrand(null);
        productSummaryDTO.setPrice(null);
        productSummaryDTO.setImageUrl(null);
        productSummaryDTO.setCategory(null);
        productSummaryDTO.setCategoryName(null);
        productSummaryDTO.setStockQuantity(null);
        productSummaryDTO.setFeatured(null);
        productSummaryDTO.setStatus(null);

        assertThat(productSummaryDTO.getId()).isNull();
        assertThat(productSummaryDTO.getName()).isNull();
        assertThat(productSummaryDTO.getBrand()).isNull();
        assertThat(productSummaryDTO.getPrice()).isNull();
        assertThat(productSummaryDTO.getImageUrl()).isNull();
        assertThat(productSummaryDTO.getCategory()).isNull();
        assertThat(productSummaryDTO.getCategoryName()).isNull();
        assertThat(productSummaryDTO.getStockQuantity()).isNull();
        assertThat(productSummaryDTO.getFeatured()).isNull();
        assertThat(productSummaryDTO.getStatus()).isNull();
        
        // Computed field should handle null stockQuantity
        assertThat(productSummaryDTO.isInStock()).isFalse();
    }

    @Test
    void testMinimalSummary() {
        // Test with only essential fields (common scenario for lightweight summaries)
        productSummaryDTO.setId("ret_pos_004");
        productSummaryDTO.setName("FlexiRetail Mobile POS");
        productSummaryDTO.setPrice(new BigDecimal("399.99"));
        productSummaryDTO.setStockQuantity(25);

        assertThat(productSummaryDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(productSummaryDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(productSummaryDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(productSummaryDTO.getStockQuantity()).isEqualTo(25);
        assertThat(productSummaryDTO.isInStock()).isTrue();
        
        // Optional fields should be null
        assertThat(productSummaryDTO.getBrand()).isNull();
        assertThat(productSummaryDTO.getImageUrl()).isNull();
        assertThat(productSummaryDTO.getFeatured()).isNull();
    }

    @Test
    void testProductStatusVariations() {
        // Test different product statuses
        String[] statuses = {"ACTIVE", "INACTIVE", "DISCONTINUED", "COMING_SOON", "SOLD_OUT"};
        
        for (String status : statuses) {
            productSummaryDTO.setStatus(status);
            assertThat(productSummaryDTO.getStatus()).isEqualTo(status);
        }
    }

    @Test
    void testConstructorWithNullValues() {
        // Test constructor with some null values
        ProductSummaryDTO dto = new ProductSummaryDTO(
            "ret_pos_004", 
            "FlexiRetail Mobile POS", 
            null, // brand is null
            new BigDecimal("399.99"), 
            null, // imageUrl is null
            "retail", 
            "Retail", 
            25, 
            null, // featured is null
            "ACTIVE"
        );

        assertThat(dto.getId()).isEqualTo("ret_pos_004");
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isNull();
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getImageUrl()).isNull();
        assertThat(dto.getCategory()).isEqualTo("retail");
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getStockQuantity()).isEqualTo(25);
        assertThat(dto.getFeatured()).isNull();
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
        assertThat(dto.isInStock()).isTrue();
    }
}
