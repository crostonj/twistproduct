package com.techtwist.services;

import com.techtwist.services.interfaces.IProductService;
import com.techtwist.models.Product;
import com.techtwist.dto.*;
import com.techtwist.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("InMemoryProductService")
@Profile("local")
public class InMemoryProductService implements IProductService {

    private final Map<String, Product> productStore = new HashMap<>();

    @Autowired
    private ProductMapper productMapper;

    public InMemoryProductService() {
        // Sample products will be created after ProductMapper is injected
    }

    @Autowired
    public void initializeSampleProducts() {
        createSampleProducts();
    }

    // New DTO-based methods
    @Override
    public ProductResponseDTO create(ProductCreateDTO createDTO) {
        Product product = productMapper.toEntity(createDTO);
        product.setId(UUID.randomUUID().toString());
        productStore.put(product.getId(), product);
        return productMapper.toResponseDTO(product);
    }

    @Override
    public Optional<ProductResponseDTO> findById(String id) {
        Product product = productStore.get(id);
        if (product != null && "ACTIVE".equals(product.getStatus())) {
            return Optional.of(productMapper.toResponseDTO(product));
        }
        return Optional.empty();
    }

    @Override
    public ProductResponseDTO findByName(String name) {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()) && name.equals(p.getName()))
                .findFirst()
                .map(productMapper::toResponseDTO)
                .orElse(null);
    }

    @Override
    public ProductResponseDTO update(String id, ProductUpdateDTO updateDTO) {
        Product existingProduct = productStore.get(id);
        if (existingProduct != null) {
            productMapper.updateEntity(existingProduct, updateDTO);
            productStore.put(id, existingProduct);
            return productMapper.toResponseDTO(existingProduct);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productStore.remove(id);
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductSummaryDTO> findAllSummary() {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()))
                .map(productMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByCategory(String category) {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()) && category.equals(p.getCategory()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByBrand(String brand) {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()) && brand.equals(p.getBrand()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByProductArea(String productArea) {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()) && productArea.equals(p.getProductArea()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findFeaturedProducts() {
        return productStore.values().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()) && Boolean.TRUE.equals(p.getFeatured()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Legacy methods for backward compatibility
    @Override
    public ProductDTO createLegacy(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setId(UUID.randomUUID().toString());
        productStore.put(product.getId(), product);
        return productMapper.toDTO(product);
    }

    @Override
    public Optional<ProductDTO> findByIdLegacy(String id) {
        Product product = productStore.get(id);
        if (product != null && "ACTIVE".equals(product.getStatus())) {
            return Optional.of(productMapper.toDTO(product));
        }
        return Optional.empty();
    }

    @Override
    public ProductDTO updateLegacy(String id, ProductDTO productDTO) {
        Product existingProduct = productStore.get(id);
        if (existingProduct != null) {
            Product updatedProduct = productMapper.toEntity(productDTO);
            updatedProduct.setId(id);
            productStore.put(id, updatedProduct);
            return productMapper.toDTO(updatedProduct);
        }
        return null;
    }

    // Initialize sample data
    private void createSampleProducts() {
        if (productMapper == null) {
            return; // Will be called again after injection
        }

        // Sample Product 1
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Premium Laptop");
        product1.setBrand("TechCorp");
        product1.setPrice(new BigDecimal("1299.99"));
        product1.setDescription("High-performance laptop for professionals");
        product1.setImageUrl("https://example.com/laptop.jpg");
        product1.setCategory("electronics");
        product1.setCategoryName("Electronics");
        product1.setProductArea("computers");
        product1.setProductAreaName("Computers");
        product1.setStockQuantity(50);
        product1.setSku("LAPTOP-001");
        product1.setStatus("ACTIVE");
        product1.setFeatured(true);
        product1.setWeight(2.5);
        product1.setDimensions("35x25x2 cm");
        product1.setWarranty("2 years");
        product1.setManufacturer("TechCorp Inc.");
        product1.setTags(Arrays.asList("laptop", "computer", "electronics", "premium"));

        // Sample Product 2
        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Wireless Headphones");
        product2.setBrand("AudioMax");
        product2.setPrice(new BigDecimal("199.99"));
        product2.setDescription("High-quality wireless headphones with noise cancellation");
        product2.setImageUrl("https://example.com/headphones.jpg");
        product2.setCategory("electronics");
        product2.setCategoryName("Electronics");
        product2.setProductArea("audio");
        product2.setProductAreaName("Audio");
        product2.setStockQuantity(100);
        product2.setSku("HEADPHONE-001");
        product2.setStatus("ACTIVE");
        product2.setFeatured(false);
        product2.setWeight(0.3);
        product2.setDimensions("20x18x8 cm");
        product2.setWarranty("1 year");
        product2.setManufacturer("AudioMax Ltd.");
        product2.setTags(Arrays.asList("headphones", "wireless", "audio", "electronics"));

        productStore.put(product1.getId(), product1);
        productStore.put(product2.getId(), product2);
    }
}
