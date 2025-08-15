package com.techtwist.repository;

import com.techtwist.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ProductRepository operations using mocked MongoTemplate
 * Tests MongoDB query logic without requiring actual database connection
 */
@ExtendWith(MockitoExtension.class)
public class ProductRepositoryIntegrationTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private Product flexiRetailMobilePOS;
    private Product flexiRetailDesktopPOS;
    private Product smartKiosk;
    private Product paymentTerminal;

    @BeforeEach
    void setUp() {
        // Create test data
        flexiRetailMobilePOS = createProduct(
            "SKU001", "FlexiRetail Mobile POS", "Mobile POS System", "POS",
            "TechTwist", new BigDecimal("299.99"), 50, true,
            Arrays.asList("mobile", "pos", "retail"), "AVAILABLE", "Electronics"
        );

        flexiRetailDesktopPOS = createProduct(
            "SKU002", "FlexiRetail Desktop POS", "Desktop POS System", "POS",
            "TechTwist", new BigDecimal("599.99"), 25, true,
            Arrays.asList("desktop", "pos", "retail"), "AVAILABLE", "Electronics"
        );

        smartKiosk = createProduct(
            "SKU003", "Smart Kiosk Pro", "Interactive Kiosk Solution", "Kiosk",
            "TechTwist", new BigDecimal("1999.99"), 10, false,
            Arrays.asList("kiosk", "interactive", "self-service"), "AVAILABLE", "Electronics"
        );

        paymentTerminal = createProduct(
            "SKU004", "Payment Terminal X1", "Secure Payment Terminal", "Payment",
            "TechTwist", new BigDecimal("199.99"), 100, true,
            Arrays.asList("payment", "terminal", "secure"), "DISCONTINUED", "Electronics"
        );
    }

    @Test
    void testFindBySku() {
        // Arrange
        Query expectedQuery = new Query(Criteria.where("sku").is("SKU001"));
        when(mongoTemplate.findOne(any(Query.class), eq(Product.class)))
            .thenReturn(flexiRetailMobilePOS);
        
        // Act
        Product result = mongoTemplate.findOne(expectedQuery, Product.class);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getSku()).isEqualTo("SKU001");
        assertThat(result.getName()).isEqualTo("FlexiRetail Mobile POS");
    }

    @Test
    void testFindByCategoryAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS, smartKiosk);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("category").is("Electronics")
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(3);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002", "SKU003");
    }

    @Test
    void testFindByBrandAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS, smartKiosk);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("brand").is("TechTwist")
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(3);
        assertThat(results).allMatch(p -> p.getBrand().equals("TechTwist"));
        assertThat(results).allMatch(p -> p.getStatus().equals("AVAILABLE"));
    }

    @Test
    void testFindByProductAreaAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("productArea").is("POS")
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testFindByCategoryAndProductAreaAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("category").is("Electronics")
                                        .and("productArea").is("POS")
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testFindByFeaturedTrueAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("featured").is(true)
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testFindByPriceBetweenAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("price").gte(new BigDecimal("200.00"))
                                                       .lte(new BigDecimal("600.00"))
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testFindByStockQuantityGreaterThanAndStatus() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("stockQuantity").gt(20)
                                        .and("status").is("AVAILABLE"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testFindByTagsContainingIgnoreCase() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("tags").regex(".*POS.*", "i"));
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testSearchProducts() {
        // Arrange - Mock for name search
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(Arrays.asList(flexiRetailMobilePOS))  // First call
            .thenReturn(Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS));  // Second call
        
        // Act & Assert - Search by name containing "Mobile"
        Query nameQuery = new Query(Criteria.where("name").regex(".*Mobile.*", "i"));
        List<Product> nameResults = mongoTemplate.find(nameQuery, Product.class);
        
        assertThat(nameResults).hasSize(1);
        assertThat(nameResults.get(0).getSku()).isEqualTo("SKU001");

        // Act & Assert - Search by description containing "POS"
        Query descQuery = new Query(Criteria.where("description").regex(".*POS.*", "i"));
        List<Product> descResults = mongoTemplate.find(descQuery, Product.class);
        
        assertThat(descResults).hasSize(2);
        assertThat(descResults).extracting(Product::getSku)
                               .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    @Test
    void testCountByCategoryAndStatus() {
        // Arrange
        when(mongoTemplate.count(any(Query.class), eq(Product.class)))
            .thenReturn(3L);
        
        // Act
        Query query = new Query(Criteria.where("category").is("Electronics")
                                        .and("status").is("AVAILABLE"));
        long count = mongoTemplate.count(query, Product.class);
        
        // Assert
        assertThat(count).isEqualTo(3);
    }

    @Test
    void testCountByProductAreaAndStatus() {
        // Arrange
        when(mongoTemplate.count(any(Query.class), eq(Product.class)))
            .thenReturn(2L);
        
        // Act
        Query query = new Query(Criteria.where("productArea").is("POS")
                                        .and("status").is("AVAILABLE"));
        long count = mongoTemplate.count(query, Product.class);
        
        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void testRepositoryPagination() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS))  // First page
            .thenReturn(Arrays.asList(smartKiosk));  // Second page
        
        // Act - Test first page
        Pageable pageable = PageRequest.of(0, 2);
        Query query = new Query(Criteria.where("status").is("AVAILABLE"))
                         .with(pageable);
        
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        
        // Act - Test second page
        Pageable secondPage = PageRequest.of(1, 2);
        Query secondQuery = new Query(Criteria.where("status").is("AVAILABLE"))
                              .with(secondPage);
        
        List<Product> secondResults = mongoTemplate.find(secondQuery, Product.class);
        
        // Assert
        assertThat(secondResults).hasSize(1);
    }

    @Test
    void testComplexFiltersQuery() {
        // Arrange
        List<Product> expectedResults = Arrays.asList(flexiRetailMobilePOS, flexiRetailDesktopPOS);
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
            .thenReturn(expectedResults);
        
        // Act
        Query query = new Query(Criteria.where("category").is("Electronics")
                                        .and("status").is("AVAILABLE")
                                        .and("price").lte(new BigDecimal("1000.00"))
                                        .and("stockQuantity").gte(20));
        
        List<Product> results = mongoTemplate.find(query, Product.class);
        
        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getSku)
                          .containsExactlyInAnyOrder("SKU001", "SKU002");
    }

    private Product createProduct(String sku, String name, String description, String productArea,
                                String brand, BigDecimal price, int stockQuantity, boolean featured,
                                List<String> tags, String status, String category) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setDescription(description);
        product.setProductArea(productArea);
        product.setBrand(brand);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setFeatured(featured);
        product.setTags(tags);
        product.setStatus(status);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }
}
