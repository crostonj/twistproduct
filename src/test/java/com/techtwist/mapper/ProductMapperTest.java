package com.techtwist.mapper;

import com.techtwist.dto.*;
import com.techtwist.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ProductMapper
 * Tests conversion between Product entity and various DTOs
 */
public class ProductMapperTest {

    private ProductMapper productMapper;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        sampleProduct = createSampleProduct();
    }

    @Test
    void testToDTO() {
        // Test conversion from Product to ProductDTO
        ProductDTO dto = productMapper.toDTO(sampleProduct);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo("ret_pos_004");
        assertThat(dto.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(dto.getBrand()).isEqualTo("TechTwist");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(dto.getDescription()).isEqualTo("Mobile POS solution with tablet and wireless payment processing.");
        assertThat(dto.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(dto.getCategory()).isEqualTo("retail");
        assertThat(dto.getCategoryName()).isEqualTo("Retail");
        assertThat(dto.getProductArea()).isEqualTo("point-of-sale");
        assertThat(dto.getProductAreaName()).isEqualTo("Point of Sale");
        assertThat(dto.getStockQuantity()).isEqualTo(50);
        assertThat(dto.getSku()).isEqualTo("TT-RET-POS-004");
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
        assertThat(dto.getFeatured()).isTrue();
        assertThat(dto.getWeight()).isEqualTo(2.5);
        assertThat(dto.getDimensions()).isEqualTo("25x15x5 cm");
        assertThat(dto.getWarranty()).isEqualTo("2 years");
        assertThat(dto.getManufacturer()).isEqualTo("TechTwist Industries");
        assertThat(dto.getFeatures()).containsExactlyInAnyOrder("Touch Screen", "Wireless Connectivity", "Card Reader");
        assertThat(dto.getSpecifications()).containsEntry("Screen Size", "10 inches");
        assertThat(dto.getTags()).containsExactlyInAnyOrder("pos", "mobile", "retail");
    }

    @Test
    void testToDTOWithNullProduct() {
        // Test handling of null Product
        ProductDTO dto = productMapper.toDTO(null);
        assertThat(dto).isNull();
    }

    @Test
    void testToResponseDTO() {
        // Test conversion from Product to ProductResponseDTO
        ProductResponseDTO responseDTO = productMapper.toResponseDTO(sampleProduct);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(responseDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(responseDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(responseDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(responseDTO.getDescription()).isEqualTo("Mobile POS solution with tablet and wireless payment processing.");
        assertThat(responseDTO.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(responseDTO.getCategory()).isEqualTo("retail");
        assertThat(responseDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(responseDTO.getProductArea()).isEqualTo("point-of-sale");
        assertThat(responseDTO.getProductAreaName()).isEqualTo("Point of Sale");
        assertThat(responseDTO.getStockQuantity()).isEqualTo(50);
        assertThat(responseDTO.getSku()).isEqualTo("TT-RET-POS-004");
        assertThat(responseDTO.getStatus()).isEqualTo("ACTIVE");
        assertThat(responseDTO.getFeatured()).isTrue();
        assertThat(responseDTO.getWeight()).isEqualTo(2.5);
        assertThat(responseDTO.getDimensions()).isEqualTo("25x15x5 cm");
        assertThat(responseDTO.getWarranty()).isEqualTo("2 years");
        assertThat(responseDTO.getManufacturer()).isEqualTo("TechTwist Industries");
        assertThat(responseDTO.getFeatures()).containsExactlyInAnyOrder("Touch Screen", "Wireless Connectivity", "Card Reader");
        assertThat(responseDTO.getSpecifications()).containsEntry("Screen Size", "10 inches");
        assertThat(responseDTO.getTags()).containsExactlyInAnyOrder("pos", "mobile", "retail");
        
        // Test datetime fields
        assertThat(responseDTO.getCreatedAt()).isNotNull();
        assertThat(responseDTO.getUpdatedAt()).isNotNull();
        
        // Test computed fields
        assertThat(responseDTO.isInStock()).isTrue();
        assertThat(responseDTO.getStockStatus()).isEqualTo("IN_STOCK");
    }

    @Test
    void testToResponseDTOWithNullDates() {
        // Test conversion with null datetime fields
        Product productWithNullDates = createSampleProduct();
        productWithNullDates.setCreatedAt(null);
        productWithNullDates.setUpdatedAt(null);

        ProductResponseDTO responseDTO = productMapper.toResponseDTO(productWithNullDates);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getCreatedAt()).isNull();
        assertThat(responseDTO.getUpdatedAt()).isNull();
    }

    @Test
    void testToResponseDTOWithNullProduct() {
        // Test handling of null Product
        ProductResponseDTO responseDTO = productMapper.toResponseDTO(null);
        assertThat(responseDTO).isNull();
    }

    @Test
    void testToSummaryDTO() {
        // Test conversion from Product to ProductSummaryDTO
        ProductSummaryDTO summaryDTO = productMapper.toSummaryDTO(sampleProduct);

        assertThat(summaryDTO).isNotNull();
        assertThat(summaryDTO.getId()).isEqualTo("ret_pos_004");
        assertThat(summaryDTO.getName()).isEqualTo("FlexiRetail Mobile POS");
        assertThat(summaryDTO.getBrand()).isEqualTo("TechTwist");
        assertThat(summaryDTO.getPrice()).isEqualTo(new BigDecimal("399.99"));
        assertThat(summaryDTO.getImageUrl()).isEqualTo("ttp4.png");
        assertThat(summaryDTO.getCategory()).isEqualTo("retail");
        assertThat(summaryDTO.getCategoryName()).isEqualTo("Retail");
        assertThat(summaryDTO.getStockQuantity()).isEqualTo(50);
        assertThat(summaryDTO.getFeatured()).isTrue();
        assertThat(summaryDTO.getStatus()).isEqualTo("ACTIVE");
        
        // Test computed field
        assertThat(summaryDTO.isInStock()).isTrue();
    }

    @Test
    void testToSummaryDTOWithNullProduct() {
        // Test handling of null Product
        ProductSummaryDTO summaryDTO = productMapper.toSummaryDTO(null);
        assertThat(summaryDTO).isNull();
    }

    @Test
    void testToEntityFromCreateDTO() {
        // Create a ProductCreateDTO
        ProductCreateDTO createDTO = new ProductCreateDTO();
        createDTO.setName("New Product");
        createDTO.setBrand("NewBrand");
        createDTO.setPrice(new BigDecimal("199.99"));
        createDTO.setDescription("A new product description");
        createDTO.setImageUrl("new.png");
        createDTO.setCategory("new-category");
        createDTO.setCategoryName("New Category");
        createDTO.setProductArea("new-area");
        createDTO.setProductAreaName("New Area");
        createDTO.setStockQuantity(25);
        createDTO.setSku("NEW-001");
        createDTO.setFeatured(true);
        createDTO.setWeight(1.5);
        createDTO.setDimensions("20x10x5 cm");
        createDTO.setWarranty("1 year");
        createDTO.setManufacturer("NewBrand Corp");
        createDTO.setFeatures(Arrays.asList("Feature 1", "Feature 2"));
        
        Map<String, String> specs = new HashMap<>();
        specs.put("Type", "Test");
        createDTO.setSpecifications(specs);
        createDTO.setTags(Arrays.asList("new", "test"));

        // Test conversion
        Product product = productMapper.toEntity(createDTO);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNull(); // ID should not be set from CreateDTO
        assertThat(product.getName()).isEqualTo("New Product");
        assertThat(product.getBrand()).isEqualTo("NewBrand");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("199.99"));
        assertThat(product.getDescription()).isEqualTo("A new product description");
        assertThat(product.getImageUrl()).isEqualTo("new.png");
        assertThat(product.getCategory()).isEqualTo("new-category");
        assertThat(product.getCategoryName()).isEqualTo("New Category");
        assertThat(product.getProductArea()).isEqualTo("new-area");
        assertThat(product.getProductAreaName()).isEqualTo("New Area");
        assertThat(product.getStockQuantity()).isEqualTo(25);
        assertThat(product.getSku()).isEqualTo("NEW-001");
        assertThat(product.getFeatured()).isTrue();
        assertThat(product.getWeight()).isEqualTo(1.5);
        assertThat(product.getDimensions()).isEqualTo("20x10x5 cm");
        assertThat(product.getWarranty()).isEqualTo("1 year");
        assertThat(product.getManufacturer()).isEqualTo("NewBrand Corp");
        assertThat(product.getFeatures()).containsExactlyInAnyOrder("Feature 1", "Feature 2");
        assertThat(product.getSpecifications()).containsEntry("Type", "Test");
        assertThat(product.getTags()).containsExactlyInAnyOrder("new", "test");
        assertThat(product.getStatus()).isEqualTo("ACTIVE"); // Status is set by Product constructor
    }

    @Test
    void testToEntityFromCreateDTOWithNull() {
        // Test handling of null ProductCreateDTO
        Product product = productMapper.toEntity((ProductCreateDTO) null);
        assertThat(product).isNull();
    }

    @Test
    void testToEntityFromProductDTO() {
        // Create a ProductDTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("test_001");
        productDTO.setName("Test Product");
        productDTO.setBrand("TestBrand");
        productDTO.setPrice(new BigDecimal("299.99"));
        productDTO.setDescription("Test product description");
        productDTO.setImageUrl("test.png");
        productDTO.setCategory("test");
        productDTO.setCategoryName("Test");
        productDTO.setProductArea("test-area");
        productDTO.setProductAreaName("Test Area");
        productDTO.setStockQuantity(10);
        productDTO.setSku("TEST-001");
        productDTO.setStatus("ACTIVE");
        productDTO.setFeatured(false);
        productDTO.setWeight(0.5);
        productDTO.setDimensions("10x5x2 cm");
        productDTO.setWarranty("6 months");
        productDTO.setManufacturer("TestBrand Inc");
        productDTO.setFeatures(Arrays.asList("Test Feature"));
        
        Map<String, String> specs = new HashMap<>();
        specs.put("Version", "1.0");
        productDTO.setSpecifications(specs);
        productDTO.setTags(Arrays.asList("test"));

        // Test conversion
        Product product = productMapper.toEntity(productDTO);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo("test_001");
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getBrand()).isEqualTo("TestBrand");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("299.99"));
        assertThat(product.getDescription()).isEqualTo("Test product description");
        assertThat(product.getImageUrl()).isEqualTo("test.png");
        assertThat(product.getCategory()).isEqualTo("test");
        assertThat(product.getCategoryName()).isEqualTo("Test");
        assertThat(product.getProductArea()).isEqualTo("test-area");
        assertThat(product.getProductAreaName()).isEqualTo("Test Area");
        assertThat(product.getStockQuantity()).isEqualTo(10);
        assertThat(product.getSku()).isEqualTo("TEST-001");
        assertThat(product.getStatus()).isEqualTo("ACTIVE");
        assertThat(product.getFeatured()).isFalse();
        assertThat(product.getWeight()).isEqualTo(0.5);
        assertThat(product.getDimensions()).isEqualTo("10x5x2 cm");
        assertThat(product.getWarranty()).isEqualTo("6 months");
        assertThat(product.getManufacturer()).isEqualTo("TestBrand Inc");
        assertThat(product.getFeatures()).containsExactly("Test Feature");
        assertThat(product.getSpecifications()).containsEntry("Version", "1.0");
        assertThat(product.getTags()).containsExactly("test");
    }

    @Test
    void testToEntityFromProductDTOWithNull() {
        // Test handling of null ProductDTO
        Product product = productMapper.toEntity((ProductDTO) null);
        assertThat(product).isNull();
    }

    @Test
    void testUpdateEntityFromUpdateDTO() {
        // Create an existing product
        Product existingProduct = createSampleProduct();
        
        // Create an update DTO with partial data
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("Updated FlexiRetail Mobile POS");
        updateDTO.setPrice(new BigDecimal("449.99"));
        updateDTO.setDescription("Updated mobile POS solution with enhanced features.");
        updateDTO.setStockQuantity(75);
        updateDTO.setFeatured(false);
        updateDTO.setFeatures(Arrays.asList("Enhanced Touch Screen", "Wireless Connectivity", "Card Reader", "Receipt Printer"));
        
        Map<String, String> updatedSpecs = new HashMap<>();
        updatedSpecs.put("Screen Size", "12 inches");
        updatedSpecs.put("RAM", "8GB");
        updateDTO.setSpecifications(updatedSpecs);
        
        updateDTO.setTags(Arrays.asList("pos", "mobile", "retail", "enhanced"));

        // Test update
        Product updatedProduct = productMapper.updateEntity(existingProduct, updateDTO);

        assertThat(updatedProduct).isSameAs(existingProduct); // Should be the same instance
        assertThat(updatedProduct.getName()).isEqualTo("Updated FlexiRetail Mobile POS");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal("449.99"));
        assertThat(updatedProduct.getDescription()).isEqualTo("Updated mobile POS solution with enhanced features.");
        assertThat(updatedProduct.getStockQuantity()).isEqualTo(75);
        assertThat(updatedProduct.getFeatured()).isFalse();
        assertThat(updatedProduct.getFeatures()).containsExactlyInAnyOrder(
            "Enhanced Touch Screen", "Wireless Connectivity", "Card Reader", "Receipt Printer");
        assertThat(updatedProduct.getSpecifications()).containsEntry("Screen Size", "12 inches");
        assertThat(updatedProduct.getSpecifications()).containsEntry("RAM", "8GB");
        assertThat(updatedProduct.getTags()).containsExactlyInAnyOrder("pos", "mobile", "retail", "enhanced");
        
        // Verify unchanged fields
        assertThat(updatedProduct.getId()).isEqualTo("ret_pos_004");
        assertThat(updatedProduct.getBrand()).isEqualTo("TechTwist"); // Not updated
        assertThat(updatedProduct.getImageUrl()).isEqualTo("ttp4.png"); // Not updated
        assertThat(updatedProduct.getCategory()).isEqualTo("retail"); // Not updated
        assertThat(updatedProduct.getSku()).isEqualTo("TT-RET-POS-004"); // Not updated
    }

    @Test
    void testUpdateEntityFromUpdateDTOWithNullValues() {
        // Create an existing product
        Product existingProduct = createSampleProduct();
        String originalName = existingProduct.getName();
        BigDecimal originalPrice = existingProduct.getPrice();
        
        // Create an update DTO with null values (should not update these fields)
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName(null);
        updateDTO.setPrice(null);
        updateDTO.setDescription("Only description updated");

        // Test update
        Product updatedProduct = productMapper.updateEntity(existingProduct, updateDTO);

        assertThat(updatedProduct).isSameAs(existingProduct);
        assertThat(updatedProduct.getName()).isEqualTo(originalName); // Should remain unchanged
        assertThat(updatedProduct.getPrice()).isEqualTo(originalPrice); // Should remain unchanged
        assertThat(updatedProduct.getDescription()).isEqualTo("Only description updated"); // Should be updated
    }

    @Test
    void testUpdateEntityWithNullProduct() {
        // Test handling of null product
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setName("Test");
        
        Product result = productMapper.updateEntity(null, updateDTO);
        assertThat(result).isNull();
    }

    @Test
    void testUpdateEntityWithNullUpdateDTO() {
        // Test handling of null update DTO
        Product existingProduct = createSampleProduct();
        String originalName = existingProduct.getName();
        
        Product result = productMapper.updateEntity(existingProduct, (ProductUpdateDTO) null);
        assertThat(result).isSameAs(existingProduct);
        assertThat(result.getName()).isEqualTo(originalName); // Should remain unchanged
    }

    @Test
    void testUpdateEntityFromProductDTO() {
        // Create an existing product
        Product existingProduct = createSampleProduct();
        
        // Create a ProductDTO for update
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setId("ret_pos_004"); // Same ID
        updateDTO.setName("Updated via ProductDTO");
        updateDTO.setBrand("UpdatedBrand");
        updateDTO.setPrice(new BigDecimal("499.99"));
        updateDTO.setStatus("INACTIVE");

        // Test update
        Product updatedProduct = productMapper.updateEntity(existingProduct, updateDTO);

        assertThat(updatedProduct).isSameAs(existingProduct);
        assertThat(updatedProduct.getName()).isEqualTo("Updated via ProductDTO");
        assertThat(updatedProduct.getBrand()).isEqualTo("UpdatedBrand");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal("499.99"));
        assertThat(updatedProduct.getStatus()).isEqualTo("INACTIVE");
        
        // Verify fields not in DTO remain unchanged
        assertThat(updatedProduct.getId()).isEqualTo("ret_pos_004");
        assertThat(updatedProduct.getDescription()).isEqualTo("Mobile POS solution with tablet and wireless payment processing.");
    }

    @Test
    void testUpdateEntityFromProductDTOWithNulls() {
        // Test ProductDTO update with null values
        Product existingProduct = createSampleProduct();
        String originalName = existingProduct.getName();
        
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setName(null);
        updateDTO.setBrand("OnlyBrandUpdated");
        
        Product updatedProduct = productMapper.updateEntity(existingProduct, updateDTO);
        
        assertThat(updatedProduct.getName()).isEqualTo(originalName); // Should remain unchanged
        assertThat(updatedProduct.getBrand()).isEqualTo("OnlyBrandUpdated"); // Should be updated
    }

    @Test
    void testMappingWithComplexCollections() {
        // Test mapping with complex collections (features, specifications, tags)
        Product productWithCollections = new Product();
        productWithCollections.setId("complex_001");
        productWithCollections.setName("Complex Product");
        productWithCollections.setBrand("ComplexBrand");
        productWithCollections.setPrice(new BigDecimal("999.99"));
        productWithCollections.setDescription("Product with complex collections");
        productWithCollections.setImageUrl("complex.png");
        
        // Complex features list
        List<String> features = Arrays.asList(
            "Advanced Feature 1",
            "Advanced Feature 2", 
            "Premium Feature",
            "Enterprise Feature"
        );
        productWithCollections.setFeatures(features);
        
        // Complex specifications map
        Map<String, String> specifications = new HashMap<>();
        specifications.put("CPU", "Intel i9");
        specifications.put("GPU", "NVIDIA RTX 4090");
        specifications.put("RAM", "64GB DDR5");
        specifications.put("Storage", "2TB NVMe SSD");
        specifications.put("Connectivity", "WiFi 6E, Bluetooth 5.3");
        productWithCollections.setSpecifications(specifications);
        
        // Complex tags list
        List<String> tags = Arrays.asList("premium", "enterprise", "high-performance", "professional");
        productWithCollections.setTags(tags);

        // Test conversion to ResponseDTO
        ProductResponseDTO responseDTO = productMapper.toResponseDTO(productWithCollections);
        
        assertThat(responseDTO.getFeatures()).hasSize(4);
        assertThat(responseDTO.getFeatures()).containsExactlyInAnyOrder(
            "Advanced Feature 1", "Advanced Feature 2", "Premium Feature", "Enterprise Feature");
        
        assertThat(responseDTO.getSpecifications()).hasSize(5);
        assertThat(responseDTO.getSpecifications()).containsEntry("CPU", "Intel i9");
        assertThat(responseDTO.getSpecifications()).containsEntry("GPU", "NVIDIA RTX 4090");
        assertThat(responseDTO.getSpecifications()).containsEntry("RAM", "64GB DDR5");
        
        assertThat(responseDTO.getTags()).hasSize(4);
        assertThat(responseDTO.getTags()).containsExactlyInAnyOrder(
            "premium", "enterprise", "high-performance", "professional");
    }

    @Test
    void testMappingPreservesDateFormatting() {
        // Test that date formatting is preserved correctly
        LocalDateTime testDateTime = LocalDateTime.of(2024, 12, 15, 14, 30, 45);
        Product product = createSampleProduct();
        product.setCreatedAt(testDateTime);
        product.setUpdatedAt(testDateTime.plusDays(1));

        ProductResponseDTO responseDTO = productMapper.toResponseDTO(product);

        assertThat(responseDTO.getCreatedAt()).isEqualTo("2024-12-15T14:30:45");
        assertThat(responseDTO.getUpdatedAt()).isEqualTo("2024-12-16T14:30:45");
    }

    // Helper method to create a sample product for testing
    private Product createSampleProduct() {
        Product product = new Product();
        product.setId("ret_pos_004");
        product.setName("FlexiRetail Mobile POS");
        product.setBrand("TechTwist");
        product.setPrice(new BigDecimal("399.99"));
        product.setDescription("Mobile POS solution with tablet and wireless payment processing.");
        product.setImageUrl("ttp4.png");
        product.setCategory("retail");
        product.setCategoryName("Retail");
        product.setProductArea("point-of-sale");
        product.setProductAreaName("Point of Sale");
        product.setStockQuantity(50);
        product.setSku("TT-RET-POS-004");
        product.setStatus("ACTIVE");
        product.setFeatured(true);
        product.setWeight(2.5);
        product.setDimensions("25x15x5 cm");
        product.setWarranty("2 years");
        product.setManufacturer("TechTwist Industries");
        product.setFeatures(Arrays.asList("Touch Screen", "Wireless Connectivity", "Card Reader"));
        
        Map<String, String> specifications = new HashMap<>();
        specifications.put("Screen Size", "10 inches");
        specifications.put("Processor", "ARM Cortex-A72");
        specifications.put("RAM", "4GB");
        product.setSpecifications(specifications);
        
        product.setTags(Arrays.asList("pos", "mobile", "retail"));
        product.setCreatedAt(LocalDateTime.now().minusDays(30));
        product.setUpdatedAt(LocalDateTime.now().minusDays(1));
        
        return product;
    }
}
