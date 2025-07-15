package com.techtwist.mapper;

import com.techtwist.dto.ProductDTO;
import com.techtwist.dto.ProductCreateDTO;
import com.techtwist.dto.ProductUpdateDTO;
import com.techtwist.dto.ProductResponseDTO;
import com.techtwist.dto.ProductSummaryDTO;
import com.techtwist.models.Product;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Mapper utility class to convert between Product entity and various DTOs
 */
@Component
public class ProductMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Convert Product entity to ProductDTO
     */
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        dto.setCategoryName(product.getCategoryName());
        dto.setProductArea(product.getProductArea());
        dto.setProductAreaName(product.getProductAreaName());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSku(product.getSku());
        dto.setStatus(product.getStatus());
        dto.setFeatured(product.getFeatured());
        dto.setWeight(product.getWeight());
        dto.setDimensions(product.getDimensions());
        dto.setWarranty(product.getWarranty());
        dto.setManufacturer(product.getManufacturer());
        dto.setFeatures(product.getFeatures());
        dto.setSpecifications(product.getSpecifications());
        dto.setTags(product.getTags());

        return dto;
    }

    /**
     * Convert Product entity to ProductResponseDTO (for API responses)
     */
    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        dto.setCategoryName(product.getCategoryName());
        dto.setProductArea(product.getProductArea());
        dto.setProductAreaName(product.getProductAreaName());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSku(product.getSku());
        dto.setStatus(product.getStatus());
        dto.setFeatured(product.getFeatured());
        dto.setWeight(product.getWeight());
        dto.setDimensions(product.getDimensions());
        dto.setWarranty(product.getWarranty());
        dto.setManufacturer(product.getManufacturer());
        dto.setFeatures(product.getFeatures());
        dto.setSpecifications(product.getSpecifications());
        dto.setTags(product.getTags());
        dto.setCreatedAt(product.getCreatedAt() != null ? product.getCreatedAt().format(formatter) : null);
        dto.setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().format(formatter) : null);

        return dto;
    }

    /**
     * Convert Product entity to ProductSummaryDTO (for lists)
     */
    public ProductSummaryDTO toSummaryDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductSummaryDTO(
            product.getId(),
            product.getName(),
            product.getBrand(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory(),
            product.getCategoryName(),
            product.getStockQuantity(),
            product.getFeatured(),
            product.getStatus()
        );
    }

    /**
     * Convert ProductCreateDTO to Product entity
     */
    public Product toEntity(ProductCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(dto.getCategory());
        product.setCategoryName(dto.getCategoryName());
        product.setProductArea(dto.getProductArea());
        product.setProductAreaName(dto.getProductAreaName());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setFeatured(dto.getFeatured());
        product.setWeight(dto.getWeight());
        product.setDimensions(dto.getDimensions());
        product.setWarranty(dto.getWarranty());
        product.setManufacturer(dto.getManufacturer());
        product.setFeatures(dto.getFeatures());
        product.setSpecifications(dto.getSpecifications());
        product.setTags(dto.getTags());

        return product;
    }

    /**
     * Convert ProductDTO to Product entity (backward compatibility)
     */
    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(dto.getCategory());
        product.setCategoryName(dto.getCategoryName());
        product.setProductArea(dto.getProductArea());
        product.setProductAreaName(dto.getProductAreaName());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setStatus(dto.getStatus());
        product.setFeatured(dto.getFeatured());
        product.setWeight(dto.getWeight());
        product.setDimensions(dto.getDimensions());
        product.setWarranty(dto.getWarranty());
        product.setManufacturer(dto.getManufacturer());
        product.setFeatures(dto.getFeatures());
        product.setSpecifications(dto.getSpecifications());
        product.setTags(dto.getTags());

        return product;
    }

    /**
     * Update existing Product entity with ProductUpdateDTO
     */
    public Product updateEntity(Product product, ProductUpdateDTO dto) {
        if (product == null || dto == null) {
            return product;
        }

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getBrand() != null) product.setBrand(dto.getBrand());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getCategoryName() != null) product.setCategoryName(dto.getCategoryName());
        if (dto.getProductArea() != null) product.setProductArea(dto.getProductArea());
        if (dto.getProductAreaName() != null) product.setProductAreaName(dto.getProductAreaName());
        if (dto.getStockQuantity() != null) product.setStockQuantity(dto.getStockQuantity());
        if (dto.getSku() != null) product.setSku(dto.getSku());
        if (dto.getStatus() != null) product.setStatus(dto.getStatus());
        if (dto.getFeatured() != null) product.setFeatured(dto.getFeatured());
        if (dto.getWeight() != null) product.setWeight(dto.getWeight());
        if (dto.getDimensions() != null) product.setDimensions(dto.getDimensions());
        if (dto.getWarranty() != null) product.setWarranty(dto.getWarranty());
        if (dto.getManufacturer() != null) product.setManufacturer(dto.getManufacturer());
        if (dto.getFeatures() != null) product.setFeatures(dto.getFeatures());
        if (dto.getSpecifications() != null) product.setSpecifications(dto.getSpecifications());
        if (dto.getTags() != null) product.setTags(dto.getTags());

        return product;
    }

    /**
     * Update existing Product entity with ProductDTO (backward compatibility)
     */
    public Product updateEntity(Product product, ProductDTO dto) {
        if (product == null || dto == null) {
            return product;
        }

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getBrand() != null) product.setBrand(dto.getBrand());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getCategoryName() != null) product.setCategoryName(dto.getCategoryName());
        if (dto.getProductArea() != null) product.setProductArea(dto.getProductArea());
        if (dto.getProductAreaName() != null) product.setProductAreaName(dto.getProductAreaName());
        if (dto.getStockQuantity() != null) product.setStockQuantity(dto.getStockQuantity());
        if (dto.getSku() != null) product.setSku(dto.getSku());
        if (dto.getStatus() != null) product.setStatus(dto.getStatus());
        if (dto.getFeatured() != null) product.setFeatured(dto.getFeatured());
        if (dto.getWeight() != null) product.setWeight(dto.getWeight());
        if (dto.getDimensions() != null) product.setDimensions(dto.getDimensions());
        if (dto.getWarranty() != null) product.setWarranty(dto.getWarranty());
        if (dto.getManufacturer() != null) product.setManufacturer(dto.getManufacturer());
        if (dto.getFeatures() != null) product.setFeatures(dto.getFeatures());
        if (dto.getSpecifications() != null) product.setSpecifications(dto.getSpecifications());
        if (dto.getTags() != null) product.setTags(dto.getTags());

        return product;
    }
}
